package org.bbaemin.api.cart.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.api.cart.controller.response.CartResponse;
import org.bbaemin.api.cart.controller.request.CreateCartItemRequest;
import org.bbaemin.api.cart.controller.request.UpdateCartItemCountRequest;
import org.bbaemin.api.cart.controller.response.CartItemResponse;
import org.bbaemin.api.cart.service.CartItemService;
import org.bbaemin.api.cart.service.DeliveryFeeService;
import org.bbaemin.api.cart.vo.CartItem;
import org.bbaemin.config.response.ApiResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;
    private final DeliveryFeeService deliveryFeeService;

    // 장바구니 조회
    @GetMapping
    public ApiResult<CartResponse> getCart(@RequestParam(name = "userId") Long userId) {
        List<CartItem> cartItemList = cartItemService.getCartItemListByUserId(userId);
        int deliveryFee = deliveryFeeService.getDeliveryFee(cartItemList);
        return ApiResult.ok(new CartResponse(cartItemList, deliveryFee));
    }

    // 장바구니에 추가
    @PostMapping("/items")
    public ApiResult<CartItemResponse> addItem(@RequestParam(name = "userId") Long userId, @Validated @RequestBody CreateCartItemRequest createCartItemRequest) {
        CartItem cartItem = cartItemService.addItem(userId, createCartItemRequest.getItemId());
        return ApiResult.created(new CartItemResponse(cartItem));
    }

    // 수량 변경
    @PatchMapping("/items")
    public ApiResult<CartItemResponse> updateCount(@RequestParam(name = "userId") Long userId, @Validated @RequestBody UpdateCartItemCountRequest updateCartItemCountRequest) {
        CartItem cartItem = cartItemService.updateCount(userId, updateCartItemCountRequest.getCartItemId(), updateCartItemCountRequest.getOrderCount());
        return ApiResult.ok(new CartItemResponse(cartItem));
    }

    // 장바구니에서 삭제
    @DeleteMapping("/items/{cartItemId}")
    public ApiResult<Void> removeItem(@PathVariable Long cartItemId, @RequestParam(name = "userId") Long userId) {
        cartItemService.removeItem(userId, cartItemId);
        return ApiResult.ok();
    }

    // 장바구니에서 선택 삭제
    @DeleteMapping("/items")
    public ApiResult<Void> removeItems(@RequestParam(name = "userId") Long userId, @RequestParam(value = "cartItemIds") List<Long> cartItemIdList) {
        cartItemService.removeItems(userId, cartItemIdList);
        return ApiResult.ok();
    }

    // 장바구니 비우기
    @DeleteMapping
    public ApiResult<Void> clear(@RequestParam(name = "userId") Long userId) {
        cartItemService.clear(userId);
        return ApiResult.ok();
    }
}
