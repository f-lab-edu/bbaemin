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
}
