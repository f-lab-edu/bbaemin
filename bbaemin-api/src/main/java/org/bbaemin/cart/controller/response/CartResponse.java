package org.bbaemin.cart.controller.response;

import lombok.Getter;
import lombok.ToString;
import org.bbaemin.cart.vo.CartItem;

import java.util.List;
import java.util.stream.Collectors;

import static org.bbaemin.util.StringUtils.getFormattedPrice;

@ToString
@Getter
public class CartResponse {

    private List<CartItemResponse> cartItemList;
    private String orderAmountStr;
    private String deliveryFeeStr;

    public CartResponse(List<CartItem> cartItemList, int deliveryFee) {
        this.cartItemList = cartItemList.stream()
                .map(CartItemResponse::new).collect(Collectors.toList());
        this.orderAmountStr = getFormattedPrice(getOrderAmount());
        this.deliveryFeeStr = getFormattedPrice(deliveryFee);
    }

    private int getOrderAmount() {
        return getCartItemList().stream()
                .mapToInt(CartItemResponse::getTotalOrderPrice).sum();
    }

}
