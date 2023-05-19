package org.bbaemin.user.order.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrderItemResponse {

    private String itemName;
    private String itemDescription;
    private int orderPrice;          // 주문 가격 (할인 반영)
    private int orderCount;             // 주문 수량
    private int totalOrderPrice;     // 합계 = 주문 가격 * 주문 수량

    @Builder
    private OrderItemResponse(String itemName, String itemDescription, int orderPrice, int orderCount) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
        this.totalOrderPrice = orderPrice * orderCount;
    }
}
