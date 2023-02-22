package org.bbaemin.order.repository;

import lombok.RequiredArgsConstructor;
import org.bbaemin.order.vo.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemRepository {

    private final Map<Long, OrderItem> map = new ConcurrentHashMap<>();
    private Long id = 0L;

    public OrderItem findById(Long orderItemId) {
        return map.get(orderItemId);
    }

    public List<OrderItem> findByOrderId(Long orderId) {
        return map.values().stream()
                .filter(orderItem -> orderItem.getOrderId().equals(orderId)).collect(Collectors.toList());
    }

    public OrderItem insert(OrderItem orderItem) {
        Long orderItemId = ++id;
        orderItem.setOrderItemId(orderItemId);
        map.put(orderItemId, orderItem);
        return orderItem;
    }

    public OrderItem update(OrderItem orderItem) {
        map.put(orderItem.getOrderItemId(), orderItem);
        return orderItem;
    }

    public void deleteById(Long orderItemId) {
        map.remove(orderItemId);
    }

    public void deleteByOrderId(Long orderId) {
        map.entrySet().removeIf(entry -> entry.getValue().getOrderId().equals(orderId));
    }
}
