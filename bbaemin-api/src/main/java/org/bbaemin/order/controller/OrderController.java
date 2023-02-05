package org.bbaemin.order.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.order.controller.request.CreateOrderRequest;
import org.bbaemin.order.controller.response.OrderResponse;
import org.bbaemin.order.controller.response.OrderSummaryResponse;
import org.bbaemin.order.service.OrderService;
import org.bbaemin.order.vo.Order;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.bbaemin.order.enums.OrderStatus.COMPLETE_ORDER;

@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    // 주문 내역 리스트
    @GetMapping
    public List<OrderSummaryResponse> listOrder(Long userId) {
        return orderService.getOrderListByUserId(userId).stream()
                .map(OrderSummaryResponse::new).collect(Collectors.toList());
    }

    // 주문 내역 상세보기
    @GetMapping("/{orderId}")
    public OrderResponse getOrder(Long userId, @PathVariable Long orderId) {
        Order order = orderService.getOrder(userId, orderId);
        return new OrderResponse(order);
    }

    // 주문
    @PostMapping
    public OrderResponse order(Long userId, @RequestBody CreateOrderRequest createOrderRequest) {
        // TODO - CHECK : toEntity()
        Order order = Order.builder()
                .userId(userId)
                .orderDate(LocalDateTime.now())
                .status(COMPLETE_ORDER)
                .deliveryAddress(createOrderRequest.getDeliveryAddress())
                .phoneNumber(createOrderRequest.getPhoneNumber())
                .email(createOrderRequest.getEmail())
                .messageToRider(createOrderRequest.getMessageToRider())
                .discountCouponIdList(createOrderRequest.getDiscountCouponIdList())
                .paymentMethod(createOrderRequest.getPaymentMethod())
                .build();
        Order saved = orderService.order(userId, order);
        return new OrderResponse(saved);
    }

    // 주문 내역 삭제
    @DeleteMapping("/{orderId}")
    public void deleteOrder(Long userId, @PathVariable Long orderId) {
        orderService.deleteOrder(userId, orderId);
    }

    // 주문 취소
    @PutMapping("/{orderId}")
    public OrderResponse cancelOrder(Long userId, @PathVariable Long orderId) {
        Order order = orderService.cancelOrder(userId, orderId);
        return new OrderResponse(order);
    }
}
