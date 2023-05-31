package org.bbaemin.user.cart.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bbaemin.user.cart.vo.CartItem;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
public class CartResponse {

    private List<CartItemResponse> cartItemList;
    private int orderAmount;
    private int deliveryFee;

    @Builder
    private CartResponse(List<CartItem> cartItemList, int orderAmount, int deliveryFee) {
        this.cartItemList = cartItemList.stream()
                .map(cartItem -> CartItemResponse.builder()
                        .itemName(cartItem.getItemName())
                        .itemDescription(cartItem.getItemDescription())
                        .orderPrice(cartItem.getOrderPrice())
                        .orderCount(cartItem.getOrderCount())
                        .build())
                .collect(Collectors.toList());
        this.orderAmount = orderAmount;
        this.deliveryFee = deliveryFee;
    }
}
