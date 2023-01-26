package org.bbaemin.order.controller.response;

import lombok.Getter;
import lombok.ToString;

import org.bbaemin.order.vo.OrderItem;

@ToString
@Getter
public class OrderItemResponse {

    private String itemName;
    private String itemDescription;
    private String orderPrice;          // 주문 가격 (할인 반영)
    private int orderCount;             // 주문 수량
    private String totalOrderPrice;     // 합계 = 주문 가격 * 주문 수량

    public OrderItemResponse(OrderItem orderItem) {
        this.itemName = orderItem.getItem().getName();
        this.itemDescription = orderItem.getItem().getDescription();
        this.orderPrice = orderItem.getFormattedOrderPrice();
        this.orderCount = orderItem.getOrderCount();
        this.totalOrderPrice = orderItem.getFormattedTotalOrderPrice();
    }
}
