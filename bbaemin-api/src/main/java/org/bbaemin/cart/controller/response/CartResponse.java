package org.bbaemin.cart.controller.response;

import lombok.Getter;
import org.bbaemin.orderItem.controller.response.OrderItemResponse;
import org.bbaemin.orderItem.vo.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CartResponse {

    private List<OrderItemResponse> orderItemList;
    private String orderAmount;
    private String deliveryFee;

    public CartResponse(List<OrderItem> orderItemList) {

        this.orderItemList = orderItemList.stream()
                .map(OrderItemResponse::new).collect(Collectors.toList());

        long orderAmount = orderItemList.stream()
                .mapToLong(OrderItem::getTotalOrderPrice).sum();
        this.orderAmount = String.format("%,d원", orderAmount);
        this.deliveryFee = String.format("%,d원", orderAmount >= 10000 ? 0 : 3000);
    }
}
