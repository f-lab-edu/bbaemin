package org.bbaemin.cart.vo;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class Cart {

    private Long userId;
    private List<CartItem> cartItemList;

    public Cart(Long userId, List<CartItem> cartItemList) {
        this.userId = userId;
        this.cartItemList = cartItemList;
    }

    private int getOrderAmount() {
        return getCartItemList().stream()
                .mapToInt(CartItem::getTotalOrderPrice).sum();
    }

    private int getDeliveryFee(int criteria) {
        return getOrderAmount() >= criteria ? 0 : 3000;
    }

    public String getFormattedOrderAmount() {
        return String.format("%,d원", getOrderAmount());
    }

    public String getFormattedDeliveryFee(int criteria) {
        return String.format("%,d원", getDeliveryFee(criteria));
    }
}
