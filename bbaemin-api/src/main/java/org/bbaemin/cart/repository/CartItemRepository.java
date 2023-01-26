package org.bbaemin.cart.repository;

import java.util.List;
import org.bbaemin.cart.vo.CartItem;

public interface CartItemRepository {

    CartItem findById(Long cartItemId);

    List<CartItem> findByUserId(Long userId);

    CartItem insert(CartItem cartItem);

    CartItem update(CartItem cartItem);

    void deleteById(Long cartItemId);

    void deleteByIds(List<Long> cartItemIds);

    void deleteByUserId(Long userId);
}
