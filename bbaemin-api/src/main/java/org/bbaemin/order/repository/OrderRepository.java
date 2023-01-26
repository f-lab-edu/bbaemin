package org.bbaemin.order.repository;

import org.bbaemin.order.vo.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> findByUserId(Long userId);

    Order findById(Long orderId);

    Order insert(Order order);

    Order update(Order order);

    void deleteById(Long orderId);
}
