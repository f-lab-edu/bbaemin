package org.bbaemin.orderItem.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItem {
/*
    // private Long itemId;
    private Item item;*/
    private String itemName;
    private String itemDescription;

    private Long orderPrice;
    private int orderCount;

    private Long userId;    // orderer

    @Builder
    private OrderItem(String itemName, String itemDescription, Long orderPrice, int orderCount, Long userId) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
        this.userId = userId;
    }

    public void addCount() {
        this.orderCount++;
    }

    public void minusCount() {
        this.orderCount--;
    }

    public Long getTotalOrderPrice() {
        return this.orderPrice * this.orderCount;
    }
}
