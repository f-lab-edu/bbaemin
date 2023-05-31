package org.bbaemin.user.cart.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.user.cart.controller.request.CreateCartItemRequest;
import org.bbaemin.user.cart.controller.request.UpdateCartItemCountRequest;
import org.bbaemin.user.cart.controller.response.CartItemResponse;
import org.bbaemin.user.cart.controller.response.CartResponse;
import org.bbaemin.user.cart.service.CartItemService;
import org.bbaemin.user.cart.vo.CartItem;
import org.bbaemin.user.order.service.DeliveryFeeService;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping("/api/v1/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;
    private final DeliveryFeeService deliveryFeeService;

    // 장바구니 조회
    @GetMapping
    public Mono<ApiResult<CartResponse>> getCart(@RequestParam(name = "userId") Long userId) {

        Flux<CartItem> cartItemFlux = cartItemService.getCartItemListByUserId(userId);

        Mono<List<CartItem>> cartItemList = cartItemFlux.collectList();
        Mono<Integer> orderAmount = cartItemFlux
                .map(cartItem -> cartItem.getOrderPrice() * cartItem.getOrderCount())
                .reduce(0, Integer::sum);
        Mono<Integer> deliveryFee = orderAmount.flatMap(deliveryFeeService::getDeliveryFee);
        return Mono.zip(cartItemList, orderAmount, deliveryFee)
                .map(tuple -> CartResponse.builder()
                        .cartItemList(tuple.getT1())
                        .orderAmount(tuple.getT2())
                        .deliveryFee(tuple.getT3())
                        .build())
                .map(ApiResult::ok);
    }

    // 장바구니에 추가
    @PostMapping("/items")
    public Mono<ApiResult<CartItemResponse>> addItem(@RequestParam(name = "userId") Long userId, @Validated @RequestBody CreateCartItemRequest createCartItemRequest) {
        return cartItemService.addItem(userId, createCartItemRequest.getItemId())
                .map(cartItem -> CartItemResponse.builder()
                        .itemName(cartItem.getItemName())
                        .itemDescription(cartItem.getItemDescription())
                        .orderPrice(cartItem.getOrderPrice())
                        .orderCount(cartItem.getOrderCount())
                        .build())
                .map(ApiResult::created);
    }

    // 수량 변경
    @PatchMapping("/items")
    public Mono<ApiResult<CartItemResponse>> updateCount(@RequestParam(name = "userId") Long userId, @Validated @RequestBody UpdateCartItemCountRequest updateCartItemCountRequest) {
        return cartItemService.updateCount(userId, updateCartItemCountRequest.getCartItemId(), updateCartItemCountRequest.getOrderCount())
                .map(cartItem -> CartItemResponse.builder()
                        .itemName(cartItem.getItemName())
                        .itemDescription(cartItem.getItemDescription())
                        .orderPrice(cartItem.getOrderPrice())
                        .orderCount(cartItem.getOrderCount())
                        .build())
                .map(ApiResult::ok);
    }

    // 장바구니에서 삭제
    @DeleteMapping("/items/{cartItemId}")
    public Mono<ApiResult<Void>> removeItem(@PathVariable Long cartItemId, @RequestParam(name = "userId") Long userId) {
        return cartItemService.removeItem(userId, cartItemId)
                .thenReturn(ApiResult.ok());
    }

    // 장바구니에서 선택 삭제
    @DeleteMapping("/items")
    public Mono<ApiResult<Void>> removeItems(@RequestParam(name = "userId") Long userId, @RequestParam(value = "cartItemIds") List<Long> cartItemIdList) {
        return cartItemService.removeItems(userId, cartItemIdList)
                .thenReturn(ApiResult.ok());
    }

    // 장바구니 비우기
    @DeleteMapping
    public Mono<ApiResult<Void>> clear(@RequestParam(name = "userId") Long userId) {
        return cartItemService.clear(userId)
                .thenReturn(ApiResult.ok());
    }
}
