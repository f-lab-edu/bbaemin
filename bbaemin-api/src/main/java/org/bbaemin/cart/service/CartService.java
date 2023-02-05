package org.bbaemin.cart.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.repository.CartItemRepository;
import org.bbaemin.cart.vo.Cart;
import org.bbaemin.cart.vo.CartItem;
import org.bbaemin.order.vo.Item_;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;

    public Cart getCart(Long userId) {
        List<CartItem> cartItemList = cartItemRepository.findByUserId(userId);
        return new Cart(userId, cartItemList);
    }

    public CartItem addItem(Long userId, Long itemId) {
        CartItem cartItem = CartItem.builder()
                .item(new Item_(itemId, "name", "description", 10000))
                .userId(userId)
                .orderCount(1)
                .build();
        cartItemRepository.insert(cartItem);
        return cartItem;
    }

    public CartItem updateCount(Long userId, Long cartItemId, int orderCount) {
        CartItem cartItem = cartItemRepository.findById(cartItemId);
        cartItem.setOrderCount(orderCount);
        cartItemRepository.update(cartItem);
        return cartItem;
    }

    public Cart removeItem(Long userId, Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        return getCart(userId);
    }

    public Cart removeItems(Long userId, List<Long> cartItemIds) {
        cartItemRepository.deleteByIds(cartItemIds);
        return getCart(userId);
    }

    public Cart clear(Long userId) {
        cartItemRepository.deleteByUserId(userId);
        return getCart(userId);
    }
}
