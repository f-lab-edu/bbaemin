package org.bbaemin.cart.controller.response;

import lombok.ToString;
import org.bbaemin.cart.vo.CartItem;

import static org.bbaemin.util.StringUtils.getFormattedPrice;

@ToString
public class CartItemResponse {

    private String itemName;
    private String itemDescription;

    private int orderPrice;
    private String orderPriceStr;

    private int orderCount;

    private int totalOrderPrice;
    private String totalOrderPriceStr;

    public CartItemResponse(CartItem cartItem) {
        this.itemName = cartItem.getItemName();
        this.itemDescription = cartItem.getItemDescription();
        this.orderPrice = cartItem.getOrderPrice();
        this.orderPriceStr = getFormattedPrice(cartItem.getOrderPrice());
        this.orderCount = cartItem.getOrderCount();
        this.totalOrderPrice = cartItem.getOrderPrice() * cartItem.getOrderCount();
        this.totalOrderPriceStr = getFormattedPrice(cartItem.getOrderPrice() * cartItem.getOrderCount());
    }

    public int getTotalOrderPrice() {
        return this.totalOrderPrice;
    }

}
