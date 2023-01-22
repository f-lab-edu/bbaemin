package org.bbaemin.order.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemResponse {

    private String itemName;
    private String itemDescription;
    private String orderPrice;          // 주문 가격 (할인 반영)
    private int orderCount;             // 주문 수량
    private String totalOrderPrice;     // 합계 = 주문 가격 * 주문 수량

    @Builder
    public OrderItemResponse(String itemName, String itemDescription, String orderPrice, int orderCount, String totalOrderPrice) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
        this.totalOrderPrice = totalOrderPrice;
    }
}
