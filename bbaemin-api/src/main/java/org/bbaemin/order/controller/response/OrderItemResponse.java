package org.bbaemin.order.controller.response;

import lombok.Getter;
import lombok.ToString;
import org.bbaemin.order.vo.OrderItem;

@ToString
@Getter
public class OrderItemResponse {

    private String itemName;
    private String itemDescription;
    private int orderPrice;          // 주문 가격 (할인 반영)
    private int orderCount;             // 주문 수량
    private int totalOrderPrice;     // 합계 = 주문 가격 * 주문 수량

    public OrderItemResponse(OrderItem orderItem) {
        this.itemName = orderItem.getItemName();
        this.itemDescription = orderItem.getItemDescription();
        this.orderPrice = orderItem.getOrderPrice();
        this.orderCount = orderItem.getOrderCount();
        this.totalOrderPrice = orderItem.getOrderPrice() * orderItem.getOrderCount();
    }
}
