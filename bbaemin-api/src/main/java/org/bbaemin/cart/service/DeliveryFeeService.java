package org.bbaemin.cart.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.vo.Cart;
import org.bbaemin.cart.vo.CartItem;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryFeeService {

    private static final int CRITERIA = 10000;
    private static final int BASIC_COST = 3000;

    public int getDeliveryFee(Cart cart) {
        int orderAmount = cart.getCartItemList().stream()
                .mapToInt(CartItem::getTotalOrderPrice).sum();
        return orderAmount >= CRITERIA ? 0 : BASIC_COST;
    }
}
