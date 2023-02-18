package org.bbaemin.order.repository;

import org.bbaemin.order.vo.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class OrderRepository {

    private final Map<Long, Order> map = new ConcurrentHashMap<>();
    private Long id = 0L;

    public void clear() {
        map.clear();
    }

    public List<Order> findByUserId(Long userId) {
        return map.values().stream()
                .filter(order -> order.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public Order findById(Long orderId) {
        return map.get(orderId);
    }

    public Order insert(Order order) {
        Long orderId = ++id;
        order.setOrderId(orderId);
        map.put(orderId, order);
        return order;
    }

    public Order update(Order order) {
        map.put(order.getOrderId(), order);
        return order;
    }

    public void deleteById(Long orderId) {
        map.remove(orderId);
    }
}
