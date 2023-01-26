package org.bbaemin.order.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import org.bbaemin.cart.vo.CartItem;

@ToString
@Getter
public class OrderItem {

    private Long orderItemId;
    private Long orderId;

    // private Long itemId;
    private Item_ item;

    private int orderPrice;
    private int orderCount;

    @Builder
    private OrderItem(Long orderItemId, Long orderId, Item_ item, int orderPrice, int orderCount) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.item = item;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getTotalOrderPrice() {
        return getOrderPrice() * getOrderCount();
    }

    public String getFormattedOrderPrice() {
        return String.format("%,d원", getOrderPrice());
    }

    public String getFormattedTotalOrderPrice() {
        return String.format("%,d원", getTotalOrderPrice());
    }
}
