package org.bbaemin.user.order.repository;

import org.bbaemin.user.order.vo.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Transactional(readOnly = true)
@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {

    @Query("select * from order where user_id = :userId")
    Flux<Order> findByUserId(Long userId);
}
