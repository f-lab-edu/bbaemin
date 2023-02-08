package org.bbaemin.order.repository;

import org.bbaemin.order.enums.OrderStatus;
import org.bbaemin.order.vo.Item_;
import org.bbaemin.order.vo.Order;
import org.bbaemin.order.vo.OrderItem;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class OrderRepository {

    private static final Map<Long, Order> map = new ConcurrentHashMap<>();
    private static Long id = 0L;

    static {
        map.put(++id, Order.builder()
                        .orderId(id)
                        .userId(1L)
                        .orderDate(LocalDateTime.now())
                        .status(OrderStatus.COMPLETE_ORDER)
                        .orderItemList(List.of(
                                OrderItem.builder()
                                        .orderItemId(1L)
                                        .orderId(id)
                                        .item(new Item_(1L, "item1", "description1", 3000))
                                        .orderPrice(3000)
                                        .orderCount(1)
                                        .build(),
                                OrderItem.builder()
                                        .orderItemId(2L)
                                        .orderId(id)
                                        .item(new Item_(2L, "item2", "description2", 5000))
                                        .orderPrice(5000)
                                        .orderCount(1)
                                        .build()))
                        .orderAmount(8000)
                        .deliveryFee(3000)
                        .paymentAmount(11000)
                        .paymentMethod("신용/체크카드")
                        .deliveryAddress("서울시 강동구")
                        .phoneNumber("010-1234-5678")
                        .email("user@email.com")
                        .messageToRider("감사합니다")
                        .build());
    }

    public static void clear() {
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
