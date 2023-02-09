package org.bbaemin.order.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrderItem {

    private Long orderItemId;
    private Long orderId;

    private Long itemId;
    private String itemName;
    private String itemDescription;

    private int orderPrice;
    private int orderCount;

    @Builder
    private OrderItem(Long orderItemId, Long orderId, Long itemId, String itemName, String itemDescription, int orderPrice, int orderCount) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
