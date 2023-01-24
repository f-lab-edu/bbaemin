package org.bbaemin.orderItem.controller.response;

import lombok.Getter;
import org.bbaemin.orderItem.vo.OrderItem;

@Getter
public class OrderItemResponse {

    private String itemName;
    private String itemDescription;
    private String orderPrice;
    private int orderCount;
    private String totalOrderPrice;

    public OrderItemResponse(OrderItem orderItem) {
        this.itemName = orderItem.getItemName();
        this.itemDescription = orderItem.getItemDescription();
        this.orderPrice = String.format("%,d원", orderItem.getOrderPrice());
        this.orderCount = orderItem.getOrderCount();
        this.totalOrderPrice = String.format("%,d원", orderItem.getTotalOrderPrice());
    }
}
