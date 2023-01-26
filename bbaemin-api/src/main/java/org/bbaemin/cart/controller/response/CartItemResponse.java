package org.bbaemin.cart.controller.response;

import lombok.Getter;
import lombok.ToString;

import org.bbaemin.cart.vo.CartItem;

@ToString
@Getter
public class CartItemResponse {

    private String itemName;
    private String itemDescription;

    private String orderPrice;
    private int orderCount;
    private String totalOrderPrice;

    public CartItemResponse(CartItem cartItem) {
        this.itemName = cartItem.getItem().getName();
        this.itemDescription = cartItem.getItem().getDescription();
        this.orderPrice = cartItem.getFormattedOrderPrice();
        this.orderCount = cartItem.getOrderCount();
        this.totalOrderPrice = cartItem.getFormattedTotalOrderPrice();
    }
}
