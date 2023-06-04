package org.bbaemin.user.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.dto.request.ApplyCouponRequest;
import org.bbaemin.dto.request.DeductItemRequest;
import org.bbaemin.dto.response.ItemResponse;
import org.bbaemin.user.cart.controller.response.CartResponse;
import org.bbaemin.user.cart.service.CartItemService;
import org.bbaemin.user.cart.vo.CartItem;
import org.bbaemin.user.order.enums.OrderStatus;
import org.bbaemin.user.order.repository.OrderItemRepository;
import org.bbaemin.user.order.repository.OrderRepository;
import org.bbaemin.user.order.vo.Order;
import org.bbaemin.user.order.vo.OrderItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import static org.bbaemin.enums.ServicePath.CART_SERVICE;
import static org.bbaemin.enums.ServicePath.COUPON_SERVICE;
import static org.bbaemin.enums.ServicePath.ITEM_SERVICE;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {

    @Value("${service.admin}")
    private String admin;
    @Value("${service.user}")
    private String user;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemService cartItemService;
    private final DeliveryFeeService deliveryFeeService;

    private WebClient client = WebClient.create();

    public Flux<Order> getOrderListByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Mono<Order> getOrder(Long userId, Long orderId) {
        return orderRepository.findById(orderId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("orderId : " + orderId)));
    }

    public Flux<OrderItem> getOrderItemListByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public Mono<OrderItem> getOrderItem(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("orderItemId : " + orderItemId)));
    }

    // #4-2. 쿠폰 적용
    Mono<Integer> applyCouponList(int orderAmount, List<Long> discountCouponIdList) {
        return client.post()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder.path(admin)
                            .path(COUPON_SERVICE.getPath())
                            .path("/apply")
                            .build();
                    log.info("uri : {}", uri);
                    return uri;
                })
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ApplyCouponRequest(orderAmount, discountCouponIdList))
                .retrieve()
                .bodyToMono(Integer.class);
    }

    // #1. 장바구니 조회
    Mono<CartResponse> getCart(Long userId) {
        return client.get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder.path(user)
                            .path(CART_SERVICE.getPath())
                            .queryParam("userId", userId)
                            .build();
                    log.info("uri : {}", uri);
                    return uri;
                })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CartResponse.class);
    }

    // #2. 재고 조회 및 재고 차감 처리
    Mono<ItemResponse> deductItem(Long itemId, int orderCount) {
        return client.post()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder.path(admin)
                            .path(ITEM_SERVICE.getPath())
                            .path("/deduct")
                            .build();
                    log.info("uri : {}", uri);
                    return uri;
                })
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DeductItemRequest(itemId, orderCount))
                .retrieve()
                .bodyToMono(ItemResponse.class);
    }

    // 동기 vs 비동기 : 호출한 결과의 완료 여부를 확인을 하는가 안 하는가
    @Transactional
    public Mono<Order> order(Long userId, Order order, List<Long> discountCouponIdList) {

    // #1. 장바구니 조회 (CartService와 분리되어 있다고 가정)
        Flux<CartItem> cartItemFlux = cartItemService.getCartItemListByUserId(userId);
        Flux<OrderItem> orderItemFlux = cartItemFlux
                .map(cartItem -> OrderItem.builder()
                        .itemId(cartItem.getItemId())
                        .itemName(cartItem.getItemName())
                        .itemDescription(cartItem.getItemDescription())
                        .orderPrice(cartItem.getOrderPrice())
                        .orderCount(cartItem.getOrderCount())
                        .build());
        Mono<CartResponse> cart = this.getCart(userId);

    // #2. 재고 조회 및 재고 차감 처리
        // -> 재고 부족 시 주문 불가 처리
//        this.deductItem();


    // #3. 금액 조회
        // #3-1. 주문금액
        Mono<Integer> orderAmount = cartItemFlux
                .map(cartItem -> cartItem.getOrderPrice() * cartItem.getOrderCount())
                .reduce(Integer::sum);

        // #3-2. 배달비 조회
        Mono<Integer> deliveryFee = orderAmount
                .flatMap(deliveryFeeService::getDeliveryFee);

        // #3-3. 쿠폰 적용(할인금액 조회)
        Mono<Integer> totalDiscountAmount = orderAmount
                .flatMap(amount -> this.applyCouponList(amount, discountCouponIdList));

        // #3-4. 총 금액
        Mono<Integer> paymentAmount = orderAmount
                .zipWith(deliveryFee, Integer::sum)
                .zipWith(totalDiscountAmount, (sum, discount) -> sum - discount);


    // #4. 결제 -> 결제 실패 시 주문 불가 처리
        // 결제 정보 생성

    // #5. (결제까지 완료 시) 주문 생성
        Mono<Order> orderMono = Mono.zip(Mono.just(order), orderAmount, deliveryFee, paymentAmount)
                .map(tuple -> {
                    Order o = tuple.getT1();
                    Integer _orderAmount = tuple.getT2();
                    Integer _deliveryFee = tuple.getT3();
                    Integer _paymentAmount = tuple.getT4();
                    o.setOrderAmount(_orderAmount);
                    o.setDeliveryFee(_deliveryFee);
                    o.setPaymentAmount(_paymentAmount);
                    return o;
                })
                .map(o -> {
                    o.setUserId(userId);
                    return o;
                })
                .flatMap(orderRepository::save);
        orderMono
                .flatMapMany(o -> orderItemFlux
                        .map(orderItem -> {
                            orderItem.setOrderId(o.getOrderId());
                            return orderItem;
                        }))
                .doOnNext(orderItemRepository::save)
                .doAfterTerminate(() -> cartItemService.clear(userId));
        return orderMono;
    }

    @Transactional
    public Mono<Void> deleteOrder(Long userId, Long orderId) {
        return orderItemRepository.deleteByOrderId(orderId)
                .thenEmpty(orderRepository.deleteById(orderId));
    }

    @Transactional
    public Mono<Order> cancelOrder(Long userId, Long orderId) {
        return getOrder(userId, orderId)
                .map(order -> {
                    order.setStatus(OrderStatus.CANCEL_ORDER);
                    return order;
                })
                .flatMap(orderRepository::save);
    }
}
