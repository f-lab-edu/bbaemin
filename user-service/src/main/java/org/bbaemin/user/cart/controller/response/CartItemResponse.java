package org.bbaemin.user.cart.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CartItemResponse {

    private String itemName;
    private String itemDescription;
    private int orderPrice;
    private int orderCount;
    private int totalOrderPrice;

    @Builder
    private CartItemResponse(String itemName, String itemDescription, int orderPrice, int orderCount) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
        this.totalOrderPrice = orderPrice * orderCount;
    }

    public int getTotalOrderPrice() {
        return this.totalOrderPrice;
    }
}
