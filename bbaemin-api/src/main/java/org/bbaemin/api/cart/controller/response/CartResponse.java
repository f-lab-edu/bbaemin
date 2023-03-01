package org.bbaemin.api.cart.controller.response;

import lombok.Getter;
import lombok.ToString;
import org.bbaemin.api.cart.vo.CartItem;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
public class CartResponse {

    private List<CartItemResponse> cartItemList;
    private int orderAmount;
    private int deliveryFee;

    public CartResponse(List<CartItem> cartItemList, int deliveryFee) {
        this.cartItemList = cartItemList.stream()
                .map(CartItemResponse::new).collect(Collectors.toList());
        this.orderAmount = getCartItemList().stream()
                .mapToInt(CartItemResponse::getTotalOrderPrice).sum();
        this.deliveryFee = deliveryFee;
    }
}
