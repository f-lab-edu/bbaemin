package org.bbaemin.user.cart.repository;

import org.bbaemin.user.cart.vo.CartItem;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional(readOnly = true)
@Repository
public interface CartItemRepository extends ReactiveCrudRepository<CartItem, Long> {

    @Query("select * from cart_item where user_id = :userId")
    Flux<CartItem> findByUserId(Long userId);

    @Query("select * from cart_item where user_id = :userId and item_id = :itemId")
    Mono<CartItem> findByUserIdAndItemId(@Param("userId") Long userId, @Param("itemId") Long itemId);

    @Transactional
    @Query("delete from cart_item where user_id = :userId")
    Mono<Void> deleteByUserId(Long userId);
}
