package org.bbaemin.cart.service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import org.bbaemin.cart.repository.CartItemRepository;
import org.bbaemin.cart.vo.Cart;
import org.bbaemin.cart.vo.CartItem;
import org.bbaemin.order.vo.Item_;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;

    public Cart getCart(Long userId) {
        List<CartItem> cartItemList = cartItemRepository.findByUserId(userId);
        return new Cart(userId, cartItemList);
    }

    public CartItem addItem(Long userId, Long itemId) {
//        Item item = itemRepository.findById(itemId);
        CartItem cartItem = CartItem.builder()
                .item(new Item_(itemId, "name", "description", 10000))
                .userId(userId)
                .orderCount(1)
                .build();
        cartItemRepository.insert(cartItem);
        return cartItem;
    }
/*
    public Cart addItem(Long userId, Long itemId, int orderPrice) {
//        Item item = itemRepository.findById(itemId);
        CartItem cartItem = CartItem.builder()
                .item(new Item_(itemId, "name", "description", 10000))
                .userId(userId)
                .orderCount(1)
                .orderPrice(orderPrice)
                .build();
        cartItemRepository.insert(cartItem);
        return getCart(userId);
    }*/

    public CartItem plusCount(Long userId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId);
        cartItem.plusCount();
        cartItemRepository.update(cartItem);
        return cartItem;
    }

    public CartItem minusCount(Long userId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId);
        cartItem.minusCount();
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

    public Cart removeAll(Long userId) {
        cartItemRepository.deleteByUserId(userId);
        return getCart(userId);
    }
}
