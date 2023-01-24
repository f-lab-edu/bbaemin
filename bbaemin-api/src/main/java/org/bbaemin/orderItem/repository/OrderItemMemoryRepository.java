package org.bbaemin.orderItem.repository;

import lombok.RequiredArgsConstructor;
import org.bbaemin.orderItem.vo.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemMemoryRepository implements OrderItemRepository {

    private static final Map<Long, OrderItem> map = new ConcurrentHashMap<>();
    // TODO - CHECK
    private static Long id = 0L;

    static {
        map.put(++id, OrderItem.builder()
                .itemName("서울우유 1000ml")
                .itemDescription("서울우유 1000ml")
                .orderPrice(3250L)
                .orderCount(1)
//                .totalOrderPrice(3250L)
                .userId(1L)
                .build());
        map.put(++id, OrderItem.builder()
                .itemName("비비고 소고기 무국 500g")
                .itemDescription("비비고 소고기 무국 500g")
                .orderPrice(5890L)
                .orderCount(1)
//                .totalOrderPrice(5890L)
                .userId(1L)
                .build());
    }

    @Override
    public OrderItem findById(Long orderItemId) {
        return map.get(orderItemId);
    }

    @Override
    public List<OrderItem> findByUserId(Long userId) {
        return map.values().stream()
                .filter(orderItem -> orderItem.getUserId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public void insert(OrderItem orderItem) {
        map.put(++id, orderItem);
    }

    @Override
    public void update(Long orderItemId, OrderItem orderItem) {
        map.put(orderItemId, orderItem);
    }

    @Override
    public void delete(Long orderItemId) {
        map.remove(orderItemId);
    }

    @Override
    public void delete(List<Long> orderItemIds) {
        orderItemIds.forEach(map::remove);
    }

    @Override
    public void deleteByUserId(Long userId) {
        Iterator<Map.Entry<Long, OrderItem>> iterator = map.entrySet().stream().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, OrderItem> next = iterator.next();
            if (next.getValue().getUserId().equals(userId)) {
                map.remove(next.getKey());
            }
        }
    }
}
