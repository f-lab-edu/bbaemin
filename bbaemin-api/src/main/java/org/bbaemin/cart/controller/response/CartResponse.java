package org.bbaemin.cart.controller.response;

import lombok.Getter;
import lombok.ToString;
import org.bbaemin.cart.vo.Cart;

import java.util.List;
import java.util.stream.Collectors;

import static org.bbaemin.util.StringUtils.getFormattedPrice;

@ToString
@Getter
public class CartResponse {

    private List<CartItemResponse> cartItemList;
    private String orderAmountStr;
    private String deliveryFeeStr;

    private int getOrderAmount() {
        return getCartItemList().stream()
                .mapToInt(CartItemResponse::getTotalOrderPrice).sum();
    }

    public CartResponse(Cart cart, int deliveryFee) {
        this.cartItemList = cart.getCartItemList().stream()
                .map(CartItemResponse::new).collect(Collectors.toList());
        this.orderAmountStr = getFormattedPrice(getOrderAmount());
        this.deliveryFeeStr = getFormattedPrice(deliveryFee);
    }
}
