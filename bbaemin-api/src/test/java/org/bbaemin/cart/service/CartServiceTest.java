package org.bbaemin.cart.service;

import java.util.List;
import org.bbaemin.cart.controller.response.CartResponse;
import org.bbaemin.cart.repository.CartItemMemoryRepository;
import org.bbaemin.cart.repository.CartItemRepository;
import org.bbaemin.cart.vo.Cart;
import org.bbaemin.cart.vo.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CartServiceTest {

    CartItemRepository cartItemRepository = new CartItemMemoryRepository();
    CartService cartService = new CartService(cartItemRepository);

    @BeforeEach
    void init() {
        CartItemMemoryRepository.clear();
    }

    @Test
    void getCart() {
        // given
        // when
        CartItem cartItem = cartService.addItem(1L, 3L);
        // then
        Cart cart = cartService.getCart(1L);
        System.out.println(cart);
        System.out.println(new CartResponse(cart));
    }

    @Test
    void addItem() {
        // given
        // when
        CartItem cartItem = cartService.addItem(1L, 3L);
        // then
        assertAll(
                () -> assertThat(cartItem.getItem().getItemId()).isEqualTo(3L),
                () -> assertThat(cartItem.getOrderCount()).isEqualTo(1),
                () -> assertThat(cartItem.getUserId()).isEqualTo(1L)
        );
    }

    @Test
    void plusCount() {
        // given
        CartItem cartItem = cartService.addItem(2L, 3L);
        // when
        CartItem updated = cartService.plusCount(2L, cartItem.getCartItemId());
        // then
        assertAll(
                () -> assertThat(updated.getCartItemId()).isEqualTo(cartItem.getCartItemId()),
                () -> assertThat(updated.getItem().getItemId()).isEqualTo(3L),
                () -> assertThat(updated.getOrderCount()).isEqualTo(2),
                () -> assertThat(updated.getUserId()).isEqualTo(2L)
        );
    }

    @Test
    void minusCount() {
        // given
        CartItem cartItem = cartService.addItem(2L, 3L);
        // when
        CartItem updated = cartService.minusCount(2L, cartItem.getCartItemId());
        // then
        assertAll(
                () -> assertThat(updated.getCartItemId()).isEqualTo(cartItem.getCartItemId()),
                () -> assertThat(updated.getItem().getItemId()).isEqualTo(3L),
                () -> assertThat(updated.getOrderCount()).isEqualTo(0),
                () -> assertThat(updated.getUserId()).isEqualTo(2L)
        );
    }

    @Test
    void removeItem() {
        // given
        CartItem cartItem = cartService.addItem(2L, 3L);
        // when
        Cart cart = cartService.removeItem(2L, cartItem.getCartItemId());
        // then
        assertThat(cart.getCartItemList().size()).isEqualTo(0);
    }

    @Test
    void removeItems() {
        // given
        CartItem cartItem1 = cartService.addItem(2L, 1L);
        CartItem cartItem2 = cartService.addItem(2L, 2L);
        CartItem cartItem3 = cartService.addItem(2L, 3L);
        // when
        Cart cart = cartService.removeItems(2L, List.of(cartItem1.getCartItemId(), cartItem2.getCartItemId()));
        // then
        assertAll(
                () -> assertThat(cart.getUserId()).isEqualTo(2L),
                () -> assertThat(cart.getCartItemList().size()).isEqualTo(1),
                () -> assertThat(cart.getCartItemList().get(0).getItem().getItemId()).isEqualTo(cartItem3.getItem().getItemId()),
                () -> assertThat(cart.getCartItemList().get(0).getOrderCount()).isEqualTo(1),
                () -> assertThat(cart.getCartItemList().get(0).getUserId()).isEqualTo(2L)
        );
    }

    @Test
    void removeAll() {
        // given
        CartItem cartItem1 = cartService.addItem(2L, 1L);
        CartItem cartItem2 = cartService.addItem(2L, 2L);
        CartItem cartItem3 = cartService.addItem(2L, 3L);
        // when
        Cart cart = cartService.removeAll(2L);
        // then
        assertAll(
                () -> assertThat(cart.getUserId()).isEqualTo(2L),
                () -> assertThat(cart.getCartItemList().size()).isEqualTo(0)
        );
    }
}