package org.bbaemin.cart.controller.response;

import lombok.Getter;
import lombok.ToString;
import org.bbaemin.cart.vo.CartItem;

@ToString
@Getter
public class CartItemResponse {

    private String itemName;
    private String itemDescription;

    private int orderPrice;

    private int orderCount;

    private int totalOrderPrice;

    public CartItemResponse(CartItem cartItem) {
        this.itemName = cartItem.getItemName();
        this.itemDescription = cartItem.getItemDescription();
        this.orderPrice = cartItem.getOrderPrice();
        this.orderCount = cartItem.getOrderCount();
        this.totalOrderPrice = cartItem.getOrderPrice() * cartItem.getOrderCount();
    }

    public int getTotalOrderPrice() {
        return this.totalOrderPrice;
    }

}
