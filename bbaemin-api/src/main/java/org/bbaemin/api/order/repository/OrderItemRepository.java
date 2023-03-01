package org.bbaemin.api.order.repository;

import org.bbaemin.api.order.vo.Order;
import org.bbaemin.api.order.vo.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);

    // TODO - CHECK : @Modifying
    @Transactional
    @Modifying
    void deleteByOrder(Order order);
}
