package org.bbaemin.user.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.dto.request.ApplyCouponRequest;
import org.bbaemin.dto.request.CancelPayRequest;
import org.bbaemin.dto.request.DeductItemRequest;
import org.bbaemin.dto.request.PayRequest;
import org.bbaemin.dto.request.RestoreItemRequest;
import org.bbaemin.dto.request.SendDeliveryInfoRequest;
import org.bbaemin.dto.request.SendEmailRequest;
import org.bbaemin.dto.response.ItemResponse;
import org.bbaemin.dto.response.PaymentResponse;
import org.bbaemin.user.cart.controller.response.CartItemResponse;
import org.bbaemin.user.cart.controller.response.CartResponse;
import org.bbaemin.user.cart.service.CartItemService;
import org.bbaemin.user.order.enums.OrderStatus;
import org.bbaemin.user.order.repository.OrderItemRepository;
import org.bbaemin.user.order.repository.OrderRepository;
import org.bbaemin.user.order.vo.Order;
import org.bbaemin.user.order.vo.OrderItem;
import org.bbaemin.user.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.bbaemin.enums.ServicePath.CART_SERVICE;
import static org.bbaemin.enums.ServicePath.COUPON_SERVICE;
import static org.bbaemin.enums.ServicePath.DELIVERY_SERVICE;
import static org.bbaemin.enums.ServicePath.EMAIL_SERVICE;
import static org.bbaemin.enums.ServicePath.ITEM_SERVICE;
import static org.bbaemin.enums.ServicePath.PAYMENT_SERVICE;
import static org.bbaemin.user.order.enums.OrderStatus.COMPLETE_ORDER;
import static org.bbaemin.user.order.enums.OrderStatus.FAIL_ORDER;

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
    private final UserService userService;

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

    private <T> Mono<T> get(Function<UriBuilder, URI> uriFunction, ParameterizedTypeReference<T> responseType) {
        return client.get()
                .uri(uriFunction)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
                .doOnError(Mono::error);
    }

    private <T> Mono<T> post(Function<UriBuilder, URI> uriFunction, Object bodyValue, ParameterizedTypeReference<T> responseType) {
        return client.post()
                .uri(uriFunction)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bodyValue)
                .retrieve()
                .bodyToMono(responseType)
                .doOnError(Mono::error);
    }

    private <T> Mono<T> patch(Function<UriBuilder, URI> uriFunction, Object bodyValue, ParameterizedTypeReference<T> responseType) {
        return client.patch()
                .uri(uriFunction)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bodyValue)
                .retrieve()
                .bodyToMono(responseType)
                .doOnError(Mono::error);
    }

    // 장바구니 조회
    Mono<ApiResult<CartResponse>> getCart(Long userId) {
        return get(
                (uriBuilder) -> uriBuilder.path(user).path(CART_SERVICE.getPath()).queryParam("userId", userId).build(),
                new ParameterizedTypeReference<>() {});
    }

    // 재고 조회 및 재고 차감 처리
    Mono<ApiResult<ItemResponse>> deductItem(Long itemId, int orderCount) {
        return patch(
                (uriBuilder) -> uriBuilder.path(admin).path(ITEM_SERVICE.getPath()).path("/{itemId}/deduct").build(itemId),
                new DeductItemRequest(itemId, orderCount),
                new ParameterizedTypeReference<>() {});
    }

    // 재고 복구
    Mono<ApiResult<ItemResponse>> restoreItem(Long itemId, int orderCount) {
        return patch(
                (uriBuilder) -> uriBuilder.path(admin).path(ITEM_SERVICE.getPath()).path("/{itemId}/restore").build(itemId),
                new RestoreItemRequest(itemId, orderCount),
                new ParameterizedTypeReference<>() {});
    }

    // 결제
    Mono<ApiResult<PaymentResponse>> pay(Long providerId, Integer amount, String referenceNumber, String account) {
        return post(
                (uriBuilder) -> uriBuilder.path(admin).path(PAYMENT_SERVICE.getPath()).path("/process").build(),
                new PayRequest(providerId, amount, referenceNumber, account),
                new ParameterizedTypeReference<>() {});
    }

    // 결제 취소
    Mono<ApiResult<PaymentResponse>> cancelPay(Long orderId) {
        return post(
                (uriBuilder) -> uriBuilder.path(admin).path(PAYMENT_SERVICE.getPath()).path("/cancel").build(),
                new CancelPayRequest(orderId),
                new ParameterizedTypeReference<>() {});
    }

    // 쿠폰 적용
    Mono<ApiResult<Integer>> applyCouponList(int orderAmount, List<Long> discountCouponIdList) {
        return patch(
                (uriBuilder) -> uriBuilder.path(admin).path(COUPON_SERVICE.getPath()).path("/apply").build(),
                new ApplyCouponRequest(orderAmount, discountCouponIdList),
                new ParameterizedTypeReference<>() {});
    }

    // 이메일 전송
    Mono<ApiResult<Void>> sendEmail(String userEmail, String content) {
        return post(
                (uriBuilder) -> uriBuilder.path(admin).path(EMAIL_SERVICE.getPath()).path("/send").build(),
                new SendEmailRequest(userEmail, content),
                new ParameterizedTypeReference<>() {});
    }

    // 배달 정보 전송
    Mono<ApiResult<Void>> sendDeliveryInfo(String deliveryAddress, Long orderId) {
        return post(
                (uriBuilder) -> uriBuilder.path(admin).path(DELIVERY_SERVICE.getPath()).path("/send").build(),
                new SendDeliveryInfoRequest(deliveryAddress, orderId),
                new ParameterizedTypeReference<>() {});
    }

    @Transactional
    public Mono<Order> order(Long userId, Order order, List<Long> discountCouponIdList) {

    // #1. 장바구니 조회 (CartService와 분리되어 있다고 가정)
        Flux<CartItemResponse> cartItemFlux = this.getCart(userId)
                .map(ApiResult::getResult)
                .flatMap(cart -> Mono.just(cart.getCartItemList()))
                .flatMapMany(Flux::fromIterable);

        return cartItemFlux
                // #2. 재고 조회 및 재고 차감 처리
                // -> 재고 부족 시 주문 불가 처리
                .doOnNext(cartItem -> {
                    this.deductItem(cartItem.getItemId(), cartItem.getOrderCount())
                            .doOnError(error -> {
                                // throw new OrderException(error);
                                this.sendEmail(order.getEmail(), "ORDER FAIL - ITEM FAIL");
                            });
                })
                .doOnComplete(() -> {

                    // #3. 금액 조회
                    // #3-1. 주문금액
                    Mono<Integer> orderAmount = cartItemFlux
                            .map(cartItem -> cartItem.getOrderPrice() * cartItem.getOrderCount())
                            .reduce(Integer::sum);

                    // #3-2. 배달비 조회
                    Mono<Integer> deliveryFee = orderAmount
                            .flatMap(_orderAmount -> deliveryFeeService.getDeliveryFee(_orderAmount).map(ApiResult::getResult));

                    // #3-3. 쿠폰 적용(할인금액 조회)
                    Mono<Integer> totalDiscountAmount = orderAmount
                            .flatMap(amount -> this.applyCouponList(amount, discountCouponIdList).map(ApiResult::getResult));

                    // #3-4. 총 금액
                    orderAmount
                            .zipWith(deliveryFee, Integer::sum)
                            .zipWith(totalDiscountAmount, (sum, discount) -> sum - discount)
                            .doOnSuccess(paymentAmount ->

                                    // #4. 결제 -> 결제 실패 시 주문 불가 처리
                                    // TODO - 결제 데이터
                                    this.pay(1L, paymentAmount, "referenceNumber", "account")
                                            .doOnError(throwable -> {
//                                                throw new OrderException(throwable);

                                                // # 5-1. 주문 실패 메일 전송
                                                this.sendEmail(order.getEmail(), "ORDER FAIL - PAYMENT FAIL");

                                                order.setStatus(FAIL_ORDER);
                                                orderRepository.save(order);
                                            })
                                            .doOnSuccess(result -> {

                                                Long paymentId = result.getResult().getPaymentId();

                                                // #5-1. (결제까지 완료 시) 주문 생성
                                                Mono.zip(Mono.just(order), orderAmount, deliveryFee, Mono.just(paymentAmount))
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
                                                            o.setPaymentId(paymentId);
                                                            o.setStatus(COMPLETE_ORDER);
                                                            return o;
                                                        })
                                                        .flatMap(orderRepository::save)
                                                        .flatMapMany(o -> cartItemFlux.map(cartItem -> OrderItem.builder()
                                                                .orderId(o.getOrderId())
                                                                .itemId(cartItem.getItemId())
                                                                .itemName(cartItem.getItemName())
                                                                .itemDescription(cartItem.getItemDescription())
                                                                .orderPrice(cartItem.getOrderPrice())
                                                                .orderCount(cartItem.getOrderCount())
                                                                .build()))
                                                        .doOnNext(orderItem -> {
                                                            order.addOrderItem(orderItem);
                                                            orderItemRepository.save(orderItem);
                                                        })
                                                        .doOnComplete(() -> {
                                                            cartItemService.clear(userId);
                                                        })
                                                        .then(
                                                            // # 6. 주문 성공 메일 전송
                                                            this.sendEmail(order.getEmail(), "ORDER SUCCESS")
                                                        )
                                                        .then(
                                                            // # 7. 배달 정보 전송
                                                            this.sendDeliveryInfo(order.getDeliveryAddress(), order.getOrderId())
                                                        );
                                            })
                            );
                })
                .then(Mono.just(order));
    }

    @Transactional
    public Mono<Void> deleteOrder(Long userId, Long orderId) {
        return orderItemRepository.deleteByOrderId(orderId)
                .thenEmpty(orderRepository.deleteById(orderId));
    }

    @Transactional
    public Mono<Order> cancelOrder(Long userId, Long orderId) {
        return getOrder(userId, orderId)
                .doOnSuccess(order -> {

                    // #1. 재고 복구
                    List<OrderItem> orderItemList = order.getOrderItemList();
                    Flux.fromIterable(orderItemList)
                            .doOnNext(orderItem -> {
                                this.restoreItem(orderItem.getItemId(), orderItem.getOrderCount());
                            });

                    // #2. 결제 취소
                    this.cancelPay(orderId);

                })
                .map(order -> {
                    order.setStatus(OrderStatus.CANCEL_ORDER);
                    return order;
                })
                // #3. '주문 취소'로 상태 변경
                .flatMap(orderRepository::save)
                .doOnSuccess(order -> {
                    // # 4. 주문 취소 메일 전송
                    this.sendEmail(order.getEmail(), "ORDER CANCEL");
                })
                .doOnSuccess(order -> {
                    // # 5. 배달 취소 정보 전송
                    this.sendDeliveryInfo(order.getDeliveryAddress(), order.getOrderId());
                });
    }
}
