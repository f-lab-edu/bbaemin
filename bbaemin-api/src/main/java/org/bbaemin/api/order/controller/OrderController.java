package org.bbaemin.api.order.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.api.order.controller.request.CreateOrderRequest;
import org.bbaemin.api.order.controller.response.OrderResponse;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.api.order.controller.response.OrderSummaryResponse;
import org.bbaemin.api.order.service.OrderService;
import org.bbaemin.api.order.vo.Order;
import org.bbaemin.api.order.vo.OrderItem;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.bbaemin.api.order.enums.OrderStatus.COMPLETE_ORDER;

@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    // 주문 내역 리스트
    @GetMapping
    public ApiResult<List<OrderSummaryResponse>> listOrder(@RequestParam Long userId) {
        List<OrderSummaryResponse> orderList = orderService.getOrderListByUserId(userId).stream()
                .map(OrderSummaryResponse::new).collect(Collectors.toList());
        return ApiResult.ok(orderList);
    }

    // 주문 내역 상세보기
    @GetMapping("/{orderId}")
    public ApiResult<OrderResponse> getOrder(@RequestParam Long userId, @PathVariable Long orderId) {
        Order order = orderService.getOrder(userId, orderId);
        List<OrderItem> orderItemList = orderService.getOrderItemListByOrder(order);
        return ApiResult.ok(new OrderResponse(order, orderItemList));
    }

    // 주문
    @PostMapping
    public ApiResult<OrderResponse> order(@RequestParam Long userId, @Validated @RequestBody CreateOrderRequest createOrderRequest) {
        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .status(COMPLETE_ORDER)
                .deliveryAddress(createOrderRequest.getDeliveryAddress())
                .phoneNumber(createOrderRequest.getPhoneNumber())
                .email(createOrderRequest.getEmail())
                .messageToRider(createOrderRequest.getMessageToRider())
                .paymentMethod(createOrderRequest.getPaymentMethod())
                .build();
        Order saved = orderService.order(userId, order, createOrderRequest.getDiscountCouponIdList());
        List<OrderItem> orderItemList = orderService.getOrderItemListByOrder(saved);
        return ApiResult.created(new OrderResponse(saved, orderItemList));
    }

    // 주문 내역 삭제
    @DeleteMapping("/{orderId}")
    public ApiResult<Void> deleteOrder(@RequestParam Long userId, @PathVariable Long orderId) {
        orderService.deleteOrder(userId, orderId);
        return ApiResult.ok();
    }

    // 주문 취소
    @PatchMapping("/{orderId}")
    public ApiResult<OrderResponse> cancelOrder(@RequestParam Long userId, @PathVariable Long orderId) {
        Order order = orderService.cancelOrder(userId, orderId);
        List<OrderItem> orderItemList = orderService.getOrderItemListByOrder(order);
        return ApiResult.ok(new OrderResponse(order, orderItemList));
    }
}
