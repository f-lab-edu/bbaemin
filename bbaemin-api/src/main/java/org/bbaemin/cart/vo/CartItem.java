package org.bbaemin.cart.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CartItem {

    private Long cartItemId;
    private Long itemId;
    private String itemName;
    private String itemDescription;

    private int orderPrice;
    private int orderCount;

    private Long userId;    // orderer

    @Builder
    private CartItem(Long cartItemId, Long itemId, String itemName, String itemDescription, int orderPrice, int orderCount, Long userId) {
        this.cartItemId = cartItemId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
        this.userId = userId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}
