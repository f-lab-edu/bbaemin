package org.bbaemin.cart.repository;

import org.bbaemin.cart.vo.CartItem;
import org.bbaemin.user.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    @Transactional
    @Modifying
    void deleteByUser(User user);
}
