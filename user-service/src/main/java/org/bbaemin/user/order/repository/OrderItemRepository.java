package org.bbaemin.user.order.repository;

import org.bbaemin.user.order.vo.OrderItem;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional(readOnly = true)
@Repository
public interface OrderItemRepository extends ReactiveCrudRepository<OrderItem, Long> {

    @Query("select * from order_item where order_id = :orderId")
    Flux<OrderItem> findByOrderId(Long orderId);

    @Transactional
    @Query("delete from order_item where order_id = :orderId")
    Mono<Void> deleteByOrderId(Long orderId);
}
