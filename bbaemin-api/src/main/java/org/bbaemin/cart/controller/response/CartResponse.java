package org.bbaemin.cart.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartResponse {

    private List<OrderItemResponse> orderItemList;
    private String orderAmount;
    private String deliveryFee;

    @Getter
    public static class OrderItemResponse {

        private String itemName;
        private String itemDescription;
        private String orderPrice;
        private int orderCount;
        private String totalOrderPrice;

        @Builder
        private OrderItemResponse(String itemName, String itemDescription, String orderPrice, int orderCount, String totalOrderPrice) {
            this.itemName = itemName;
            this.itemDescription = itemDescription;
            this.orderPrice = orderPrice;
            this.orderCount = orderCount;
            this.totalOrderPrice = totalOrderPrice;
        }
    }

    @Builder
    private CartResponse(List<OrderItemResponse> orderItemList, String orderAmount, String deliveryFee) {
        this.orderItemList = orderItemList;
        this.orderAmount = orderAmount;
        this.deliveryFee = deliveryFee;
    }
}
