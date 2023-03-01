package org.bbaemin.api.cart.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.api.cart.vo.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryFeeService {

    private static final int CRITERIA = 10000;
    private static final int BASIC_COST = 3000;

    public int getDeliveryFee(List<CartItem> cartItemList) {
        int orderAmount = cartItemList.stream()
                .mapToInt(cartItem -> cartItem.getOrderPrice() * cartItem.getOrderCount()).sum();
        return getDeliveryFee(orderAmount);
    }

    public int getDeliveryFee(int orderAmount) {
        return orderAmount >= CRITERIA ? 0 : BASIC_COST;
    }
}
