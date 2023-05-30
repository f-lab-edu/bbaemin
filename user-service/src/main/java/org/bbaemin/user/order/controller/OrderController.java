package org.bbaemin.user.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.user.order.controller.request.CreateOrderRequest;
import org.bbaemin.user.order.controller.response.OrderResponse;
import org.bbaemin.user.order.controller.response.OrderSummaryResponse;
import org.bbaemin.user.order.service.OrderService;
import org.bbaemin.user.order.vo.Order;
import org.bbaemin.user.order.vo.OrderItem;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.bbaemin.user.order.enums.OrderStatus.PROCESSING_ORDER;

@Slf4j
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    // 주문 내역 리스트
    @GetMapping
    public Mono<ApiResult<List<OrderSummaryResponse>>> listOrder(@RequestParam Long userId) {
        return orderService.getOrderListByUserId(userId)
                .map(order -> OrderSummaryResponse.builder()
                        .orderId(order.getOrderId())
                        .status(order.getStatus())
                        .paymentAmount(order.getPaymentAmount())
                        .orderDate(order.getOrderDate())
                        .build())
                .collectList()
                .map(ApiResult::ok);
    }

    // 주문 내역 상세보기
    @GetMapping("/{orderId}")
    public Mono<ApiResult<OrderResponse>> getOrder(@RequestParam Long userId, @PathVariable Long orderId) {

        Flux<OrderItem> orderItemFlux = orderService.getOrderItemListByOrderId(orderId);

        Mono<Order> orderMono = orderService.getOrder(userId, orderId);
        Mono<List<OrderItem>> orderItemListMono = orderItemFlux.collectList();
        return Mono.zip(orderMono, orderItemListMono)
                .map(tuple -> {
                    Order order = tuple.getT1();
                    List<OrderItem> orderItemList = tuple.getT2();
                    return OrderResponse.builder()
                            .orderId(order.getOrderId())
                            .status(order.getStatus())
                            .paymentAmount(order.getPaymentAmount())
                            .orderDate(order.getOrderDate())
                            .orderAmount(order.getOrderAmount())
                            .deliveryFee(order.getDeliveryFee())
                            .paymentMethod(order.getPaymentMethod())
                            .deliveryAddress(order.getDeliveryAddress())
                            .phoneNumber(order.getPhoneNumber())
                            .email(order.getEmail())
                            .messageToRider(order.getMessageToRider())
                            .orderItemList(orderItemList)
                            .build();
                })
                .map(ApiResult::ok);
    }

    // 주문
    @PostMapping
    public Mono<ApiResult<OrderResponse>> order(@RequestParam Long userId, @Validated @RequestBody CreateOrderRequest createOrderRequest) {

        Mono<Order> savedMono = orderService.order(
                userId,
                Order.builder()
                        .orderDate(LocalDateTime.now())
                        .status(PROCESSING_ORDER)
                        .deliveryAddress(createOrderRequest.getDeliveryAddress())
                        .phoneNumber(createOrderRequest.getPhoneNumber())
                        .email(createOrderRequest.getEmail())
                        .messageToRider(createOrderRequest.getMessageToRider())
                        .paymentMethod(createOrderRequest.getPaymentMethod())
                        .build(),
                createOrderRequest.getDiscountCouponIdList());

        Mono<List<OrderItem>> orderItemListMono = savedMono
                .flatMap(saved -> {
                    Long orderId = saved.getOrderId();
                    return orderService.getOrderItemListByOrderId(orderId).collectList();
                });

        return Mono.zip(savedMono, orderItemListMono)
                .map(tuple -> {
                    Order saved = tuple.getT1();
                    List<OrderItem> orderItemList = tuple.getT2();
                    return OrderResponse.builder()
                            .orderId(saved.getOrderId())
                            .status(saved.getStatus())
                            .paymentAmount(saved.getPaymentAmount())
                            .orderDate(saved.getOrderDate())
                            .orderAmount(saved.getOrderAmount())
                            .deliveryFee(saved.getDeliveryFee())
                            .paymentMethod(saved.getPaymentMethod())
                            .deliveryAddress(saved.getDeliveryAddress())
                            .phoneNumber(saved.getPhoneNumber())
                            .email(saved.getEmail())
                            .messageToRider(saved.getMessageToRider())
                            .orderItemList(orderItemList)
                            .build();
                })
                .map(ApiResult::ok);
    }

    // 주문 내역 삭제
    @DeleteMapping("/{orderId}")
    public Mono<ApiResult<Void>> deleteOrder(@RequestParam Long userId, @PathVariable Long orderId) {
        return orderService.deleteOrder(userId, orderId)
                .thenReturn(ApiResult.ok());
    }

    // 주문 취소
    @PatchMapping("/{orderId}")
    public Mono<ApiResult<OrderResponse>> cancelOrder(@RequestParam Long userId, @PathVariable Long orderId) {

        Flux<OrderItem> orderItemFlux = orderService.getOrderItemListByOrderId(orderId);

        Mono<List<OrderItem>> orderItemListMono = orderItemFlux.collectList();
        Mono<Order> orderMono = orderService.cancelOrder(userId, orderId);
        return Mono.zip(orderMono, orderItemListMono)
                .map(tuple -> {
                    Order order = tuple.getT1();
                    List<OrderItem> orderItemList = tuple.getT2();
                    return OrderResponse.builder()
                            .orderId(order.getOrderId())
                            .status(order.getStatus())
                            .paymentAmount(order.getPaymentAmount())
                            .orderDate(order.getOrderDate())
                            .orderAmount(order.getOrderAmount())
                            .deliveryFee(order.getDeliveryFee())
                            .paymentMethod(order.getPaymentMethod())
                            .deliveryAddress(order.getDeliveryAddress())
                            .phoneNumber(order.getPhoneNumber())
                            .email(order.getEmail())
                            .messageToRider(order.getMessageToRider())
                            .orderItemList(orderItemList)
                            .build();
                })
                .map(ApiResult::ok);
    }
}
