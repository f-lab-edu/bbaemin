package org.bbaemin.order.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.order.controller.request.CreateOrderRequest;
import org.bbaemin.order.controller.response.OrderDetailResponse;
import org.bbaemin.order.controller.response.OrderItemResponse;
import org.bbaemin.order.controller.response.OrderResponse;
import org.bbaemin.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrderResponse> getOrderResponseListByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::new).collect(Collectors.toList());
    }

    public OrderDetailResponse getOrderDetailResponse(Long userId, Long orderId) {
        return ;
    }

    public OrderDetailResponse order(Long userId, CreateOrderRequest createOrderRequest) {
        return null;
    }

    public void deleteOrder(Long userId, Long orderId) {

    }

    public OrderDetailResponse cancelOrder(Long userId, Long orderId) {
        return null;
    }
}
