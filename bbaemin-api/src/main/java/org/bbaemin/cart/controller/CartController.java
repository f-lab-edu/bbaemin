package org.bbaemin.cart.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.controller.request.CreateCartItemRequest;
import org.bbaemin.cart.controller.request.UpdateCartItemCountRequest;
import org.bbaemin.cart.controller.response.CartResponse;
import org.bbaemin.cart.service.CartItemService;
import org.bbaemin.cart.service.DeliveryFeeService;
import org.bbaemin.cart.vo.CartItem;
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

    private final CartItemService cartItemService;
    private final DeliveryFeeService deliveryFeeService;

    // 장바구니 조회
    @GetMapping
    public CartResponse getCart(@PathVariable Long userId) {
        List<CartItem> cartItemList = cartItemService.getCartItemListByUserId(userId);
        int deliveryFee = deliveryFeeService.getDeliveryFee(cartItemList);
        return new CartResponse(cartItemList, deliveryFee);
    }

    // 장바구니에 추가
    @PostMapping("/items")
    public CartResponse addItem(@PathVariable Long userId, @RequestBody CreateCartItemRequest createCartItemRequest) {
        cartItemService.addItem(userId, createCartItemRequest.getItemId());
        return getCart(userId);
    }

    // 수량 변경
    @PutMapping("/items")
    public CartResponse updateCount(@PathVariable Long userId, @RequestBody UpdateCartItemCountRequest updateCartItemCountRequest) {
        cartItemService.updateCount(userId, updateCartItemCountRequest.getCartItemId(), updateCartItemCountRequest.getOrderCount());
        return getCart(userId);
    }

    // 장바구니에서 삭제
    @DeleteMapping("/items/{cartItemId}")
    public CartResponse removeItem(@PathVariable Long userId, @PathVariable Long cartItemId) {
        cartItemService.removeItem(userId, cartItemId);
        return getCart(userId);
    }

    // 장바구니에서 선택 삭제
    @DeleteMapping("/items")
    public CartResponse removeItems(@PathVariable Long userId, @RequestParam(value = "cartItemIds") List<Long> cartItemIds) { // removeItemList
        cartItemService.removeItems(userId, cartItemIds);
        return getCart(userId);
    }

    // 장바구니 비우기
    @DeleteMapping
    public CartResponse clear(@PathVariable Long userId) {
        cartItemService.clear(userId);
        return getCart(userId);
    }
}
