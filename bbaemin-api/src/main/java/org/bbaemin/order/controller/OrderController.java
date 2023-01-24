package org.bbaemin.order.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.order.controller.request.CreateOrderRequest;
import org.bbaemin.order.controller.response.OrderDetailResponse;
import org.bbaemin.order.controller.response.OrderResponse;
import org.bbaemin.order.service.OrderService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    // 주문 내역 리스트
    @GetMapping
    public List<OrderResponse> listOrder(Long userId) {
        return orderService.getOrderResponseListByUserId(userId);
    }

    // 주문 내역 상세보기
    @GetMapping("/{orderId}")
    public OrderDetailResponse getOrderDetail(Long userId, @PathVariable Long orderId) {
        return orderService.getOrderDetailResponse(userId, orderId);
    }

    // 주문
    @PostMapping
    public OrderDetailResponse order(Long userId, @RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.order(userId, createOrderRequest);
    }

    // 주문 내역 삭제
    @DeleteMapping("/{orderId}")
    public void deleteOrder(Long userId, @PathVariable Long orderId) {
        orderService.deleteOrder(userId, orderId);
    }

    // 주문 취소
    @PatchMapping("/{orderId}")
    public OrderDetailResponse cancelOrder(Long userId, @PathVariable Long orderId) {
        return orderService.cancelOrder(userId, orderId);
    }
}
