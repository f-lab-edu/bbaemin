package org.bbaemin.order.repository;

import java.util.List;
import org.bbaemin.order.vo.OrderItem;

public interface OrderItemRepository {

    OrderItem findById(Long orderItemId);

    List<OrderItem> findByOrderId(Long orderId);

    OrderItem insert(OrderItem orderItem);

    OrderItem update(OrderItem orderItem);

    void deleteById(Long orderItemId);

    void deleteByOrderId(Long orderId);
}
