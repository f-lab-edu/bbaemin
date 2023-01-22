package org.bbaemin.cart.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.controller.response.CartResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RequestMapping("/api/v1/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    // 장바구니 조회
    @GetMapping
    public CartResponse getCart(Long userId) {
        return CartResponse.builder()
                .orderItemList(Arrays.asList(
                        CartResponse.OrderItemResponse.builder()
                                .itemName("서울우유 1000ml")
                                .itemDescription("서울우유 1000ml")
                                .orderPrice("3,250원")
                                .orderCount(1)
                                .totalOrderPrice("3,250원")
                                .build()
                ))
                .orderAmount("3,250원")
                .deliveryFee("3,000원")
                .build();
    }

    // 장바구니에 추가
    @PostMapping("/{itemId}")
    public CartResponse addItem(Long userId, @PathVariable Long itemId) {

        // add item

        return CartResponse.builder()
                .orderItemList(Arrays.asList(
                        CartResponse.OrderItemResponse.builder()
                                .itemName("서울우유 1000ml")
                                .itemDescription("서울우유 1000ml")
                                .orderPrice("3,250원")
                                .orderCount(1)
                                .totalOrderPrice("3,250원")
                                .build(),
                        CartResponse.OrderItemResponse.builder()
                                .itemName("비비고 소고기 무국 500g")
                                .itemDescription("비비고 소고기 무국 500g")
                                .orderPrice("5,890원")
                                .orderCount(1)
                                .totalOrderPrice("5,890원")
                                .build()
                ))
                .orderAmount("9,140원")
                .deliveryFee("3,000원")
                .build();
    }

    // 수량 변경
    @PatchMapping("/{orderItemId}")
    public CartResponse plusCount(Long userId, @PathVariable Long orderItemId) {

        // plus count

        return CartResponse.builder()
                .orderItemList(Arrays.asList(
                        CartResponse.OrderItemResponse.builder()
                                .itemName("서울우유 1000ml")
                                .itemDescription("서울우유 1000ml")
                                .orderPrice("3,250원")
                                .orderCount(2)
                                .totalOrderPrice("6,500원")
                                .build(),
                        CartResponse.OrderItemResponse.builder()
                                .itemName("비비고 소고기 무국 500g")
                                .itemDescription("비비고 소고기 무국 500g")
                                .orderPrice("5,890원")
                                .orderCount(1)
                                .totalOrderPrice("5,890원")
                                .build()
                ))
                .orderAmount("12,390원")
                .deliveryFee("3,000원")
                .build();
    }

    @PatchMapping("/{orderItemId}")
    public CartResponse minusCount(@PathVariable Long orderItemId) {

        // minus count

        return CartResponse.builder()
                .orderItemList(Arrays.asList(
                        CartResponse.OrderItemResponse.builder()
                                .itemName("서울우유 1000ml")
                                .itemDescription("서울우유 1000ml")
                                .orderPrice("3,250원")
                                .orderCount(1)
                                .totalOrderPrice("3,250원")
                                .build(),
                        CartResponse.OrderItemResponse.builder()
                                .itemName("비비고 소고기 무국 500g")
                                .itemDescription("비비고 소고기 무국 500g")
                                .orderPrice("5,890원")
                                .orderCount(1)
                                .totalOrderPrice("5,890원")
                                .build()
                ))
                .orderAmount("9,140원")
                .deliveryFee("3,000원")
                .build();
    }

    // 장바구니에서 삭제
    @DeleteMapping("/{orderItemId}")
    public CartResponse removeItem(Long userId, @PathVariable Long orderItemId) {

        // remove item

        return CartResponse.builder()
                .orderItemList(Arrays.asList(
                        CartResponse.OrderItemResponse.builder()
                                .itemName("서울우유 1000ml")
                                .itemDescription("서울우유 1000ml")
                                .orderPrice("3,250원")
                                .orderCount(1)
                                .totalOrderPrice("3,250원")
                                .build()
                ))
                .orderAmount("3,250원")
                .deliveryFee("3,000원")
                .build();
    }
/*
    // 장바구니에서 선택 삭제
    @DeleteMapping
    public CartResponse removeItems(Long userId, @RequestParam(value = "orderItemIds") List<Long> orderItemIds) { // removeItemList

        // remove checked items

        return CartResponse.builder()
                .orderItemList(Arrays.asList(
                        CartResponse.OrderItemResponse.builder()
                                .itemName("서울우유 1000ml")
                                .itemDescription("서울우유 1000ml")
                                .orderPrice("3,250원")
                                .orderCount(1)
                                .totalOrderPrice("3,250원")
                                .build()
                ))
                .orderAmount("3,250원")
                .deliveryFee("3,000원")
                .build();
    }*/

    // 장바구니 비우기
    @DeleteMapping
    public CartResponse removeAll() {

        // remove all

        return CartResponse.builder()
//                .orderItemList(Collections.emptyList())
                .build();
    }
}
