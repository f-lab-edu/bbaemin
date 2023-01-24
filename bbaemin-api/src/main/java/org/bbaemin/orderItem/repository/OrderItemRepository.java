package org.bbaemin.orderItem.repository;

import org.bbaemin.orderItem.vo.OrderItem;

import java.util.List;

public interface OrderItemRepository {

    OrderItem findById(Long orderItemId);

    List<OrderItem> findByUserId(Long userId);

    void insert(OrderItem orderItem);

    void update(Long orderItemId, OrderItem orderItem);

    void delete(Long orderItemId);

    void delete(List<Long> orderItemIds);

    void deleteByUserId(Long userId);
}
