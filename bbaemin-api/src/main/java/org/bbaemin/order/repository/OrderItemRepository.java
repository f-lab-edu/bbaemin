package org.bbaemin.order.repository;

import lombok.RequiredArgsConstructor;
import org.bbaemin.order.vo.Item_;
import org.bbaemin.order.vo.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemRepository {

    private static final Map<Long, OrderItem> map = new ConcurrentHashMap<>();
    private static Long id = 0L;

    static {
        map.put(++id, OrderItem.builder()
                        .orderItemId(id)
                        .orderId(1L)
                        .item(new Item_(1L, "item1", "description1", 3000))
                        .orderPrice(3000)
                        .orderCount(1)
                        .build());
        map.put(++id, OrderItem.builder()
                        .orderItemId(id)
                        .orderId(1L)
                        .item(new Item_(2L, "item2", "description2", 5000))
                        .orderPrice(5000)
                        .orderCount(1)
                        .build());
    }

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
        Iterator<Map.Entry<Long, OrderItem>> iterator = map.entrySet().stream().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, OrderItem> next = iterator.next();
            if (next.getValue().getOrderId().equals(orderId)) {
                map.remove(next.getKey());
            }
        }
    }
}
