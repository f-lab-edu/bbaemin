package org.bbaemin.order.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.service.CartService;
import org.bbaemin.cart.vo.Cart;
import org.bbaemin.order.enums.OrderStatus;
import org.bbaemin.order.repository.OrderItemRepository;
import org.bbaemin.order.repository.OrderRepository;
import org.bbaemin.order.vo.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;

    public List<Order> getOrderListByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrder(Long userId, Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Order order(Long userId, Order order) {

        Cart cart = cartService.getCart(userId);
        order.setCart(cart);
        cartService.clear(userId);

        Order saved = orderRepository.insert(order);
        saved.getOrderItemList().forEach(orderItem -> {
            orderItem.setOrderId(saved.getOrderId());
            orderItemRepository.insert(orderItem);
        });
        return saved;
    }

    public void deleteOrder(Long userId, Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order cancelOrder(Long userId, Long orderId) {
        // TODO - updateStatusCancel
        Order order = orderRepository.findById(orderId);
        order.setStatus(OrderStatus.CANCEL_ORDER);
        return orderRepository.update(order);
    }
}
