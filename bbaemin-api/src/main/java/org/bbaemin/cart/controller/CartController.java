package org.bbaemin.cart.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.controller.response.CartResponse;
import org.bbaemin.cart.service.CartService;
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
        return cartService.getCartResponse(userId);
    }

    // 장바구니에 추가
    @PostMapping("/{itemId}")
    public CartResponse addItem(Long userId, @PathVariable Long itemId) {
        return cartService.addItem(userId, itemId);
    }

    // 수량 변경
    @PutMapping("/{orderItemId}")
    public CartResponse plusCount(Long userId, @PathVariable Long orderItemId) {
        return cartService.plusCount(userId, orderItemId);
    }

    @PutMapping("/{orderItemId}")
    public CartResponse minusCount(Long userId, @PathVariable Long orderItemId) {
        return cartService.minusCount(userId, orderItemId);
    }

    // 장바구니에서 삭제
    @DeleteMapping("/{orderItemId}")
    public CartResponse removeItem(Long userId, @PathVariable Long orderItemId) {
        return cartService.removeItem(userId, orderItemId);
    }
/*
    // 장바구니에서 선택 삭제
    @DeleteMapping
    public CartResponse removeItems(Long userId, @RequestParam(value = "orderItemIds") List<Long> orderItemIds) { // removeItemList
        return cartService.removeItems(userId, orderItemIds);
    }*/

    // 장바구니 비우기
    @DeleteMapping
    public CartResponse removeAll(Long userId) {
        return cartService.removeAll(userId);
    }
}
