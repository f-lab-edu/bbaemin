package org.bbaemin.cart.controller.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import org.bbaemin.cart.vo.Cart;

@ToString
@Getter
public class CartResponse {

    private List<CartItemResponse> cartItemList;
    private String orderAmount;
    private String deliveryFee;

    public CartResponse(Cart cart) {

        this.cartItemList = cart.getCartItemList().stream()
                .map(CartItemResponse::new).collect(Collectors.toList());

        this.orderAmount = cart.getFormattedOrderAmount();
        this.deliveryFee = cart.getFormattedDeliveryFee(10000);
    }
}
