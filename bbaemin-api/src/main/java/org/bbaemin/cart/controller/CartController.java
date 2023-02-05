package org.bbaemin.cart.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.controller.request.CreateCartItemRequest;
import org.bbaemin.cart.controller.request.UpdateCartItemCountRequest;
import org.bbaemin.cart.controller.response.CartResponse;
import org.bbaemin.cart.service.CartService;
import org.bbaemin.cart.vo.Cart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/users/{userId}/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니 조회
    @GetMapping
    public CartResponse getCart(@PathVariable Long userId) {
        Cart cart = cartService.getCart(userId);
        return new CartResponse(cart);
    }

    // 장바구니에 추가
    @PostMapping("/items")
    public CartResponse addItem(@PathVariable Long userId, @RequestBody CreateCartItemRequest createCartItemRequest) {
        cartService.addItem(userId, createCartItemRequest.getItemId());
        Cart cart = cartService.getCart(userId);
        return new CartResponse(cart);
    }

    // 수량 변경
    @PutMapping("/items")
    public CartResponse updateCount(@PathVariable Long userId, @RequestBody UpdateCartItemCountRequest updateCartItemCountRequest) {
        cartService.updateCount(userId, updateCartItemCountRequest.getCartItemId(), updateCartItemCountRequest.getOrderCount());
        Cart cart = cartService.getCart(userId);
        return new CartResponse(cart);
    }

    // 장바구니에서 삭제
    @DeleteMapping("/items/{cartItemId}")
    public CartResponse removeItem(@PathVariable Long userId, @PathVariable Long cartItemId) {
        Cart cart = cartService.removeItem(userId, cartItemId);
        return new CartResponse(cart);
    }

    // 장바구니에서 선택 삭제
    @DeleteMapping("/items")
    public CartResponse removeItems(@PathVariable Long userId, @RequestParam(value = "cartItemIds") List<Long> cartItemIds) { // removeItemList
        Cart cart = cartService.removeItems(userId, cartItemIds);
        return new CartResponse(cart);
    }

    // 장바구니 비우기
    @DeleteMapping
    public CartResponse clear(@PathVariable Long userId) {
        Cart cart = cartService.clear(userId);
        return new CartResponse(cart);
    }
}
