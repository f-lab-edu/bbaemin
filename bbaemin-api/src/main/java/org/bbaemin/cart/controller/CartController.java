package org.bbaemin.cart.controller;

import lombok.RequiredArgsConstructor;

import org.bbaemin.cart.controller.response.CartResponse;
import org.bbaemin.cart.service.CartService;
import org.bbaemin.cart.vo.Cart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니 조회
    @GetMapping
    public CartResponse getCart(Long userId) {
        Cart cart = cartService.getCart(userId);
        return new CartResponse(cart);
    }

    // 장바구니에 추가
    @PostMapping("/{itemId}")
    public CartResponse addItem(Long userId, @PathVariable Long itemId) {
        cartService.addItem(userId, itemId);
        Cart cart = cartService.getCart(userId);
        return new CartResponse(cart);
    }

    // 수량 변경
    @PutMapping("/{cartItemId}")
    public CartResponse plusCount(Long userId, @PathVariable Long cartItemId) {
        cartService.plusCount(userId, cartItemId);
        Cart cart = cartService.getCart(userId);
        return new CartResponse(cart);
    }

    @PutMapping("/{cartItemId}")
    public CartResponse minusCount(Long userId, @PathVariable Long cartItemId) {
        cartService.minusCount(userId, cartItemId);
        Cart cart = cartService.getCart(userId);
        return new CartResponse(cart);
    }

    // 장바구니에서 삭제
    @DeleteMapping("/{cartItemId}")
    public CartResponse removeItem(Long userId, @PathVariable Long cartItemId) {
        Cart cart = cartService.removeItem(userId, cartItemId);
        return new CartResponse(cart);
    }
/*
    // 장바구니에서 선택 삭제
    @DeleteMapping
    public CartResponse removeItems(Long userId, @RequestParam(value = "cartItemIds") List<Long> cartItemIds) { // removeItemList
        Cart cart = cartService.removeItems(userId, cartItemIds);
        return new CartResponse(cart);
    }*/

    // 장바구니 비우기
    @DeleteMapping
    public CartResponse removeAll(Long userId) {
        Cart cart = cartService.removeAll(userId);
        return new CartResponse(cart);
    }
}
