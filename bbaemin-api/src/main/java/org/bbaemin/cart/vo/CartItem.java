package org.bbaemin.cart.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import org.bbaemin.order.vo.Item_;
import org.bbaemin.order.vo.OrderItem;

@ToString
@Getter
public class CartItem {

    private Long cartItemId;

    // private Long itemId;
    private Item_ item;

//    private int orderPrice;
    private int orderCount;

    private Long userId;    // orderer

    @Builder
    private CartItem(Long cartItemId, Item_ item, int orderCount, Long userId) {
        this.cartItemId = cartItemId;
        this.item = item;
        this.orderCount = orderCount;
        this.userId = userId;
    }

    public int getOrderPrice() {
        return item.getPrice();
    }

    public int getTotalOrderPrice() {
        return getOrderPrice() * getOrderCount();
    }

    public OrderItem toOrderItem() {
        return OrderItem.builder()
                .item(getItem())
                .orderPrice(getOrderPrice())
                .orderCount(getOrderCount())
                .build();
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getFormattedOrderPrice() {
        return String.format("%,d원", getOrderPrice());
    }

    public String getFormattedTotalOrderPrice() {
        return String.format("%,d원", getTotalOrderPrice());
    }
}
