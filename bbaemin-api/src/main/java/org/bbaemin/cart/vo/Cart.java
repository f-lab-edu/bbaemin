package org.bbaemin.cart.vo;

import lombok.Builder;
import lombok.Getter;
import org.bbaemin.orderItem.vo.OrderItem;

import java.util.List;

@Getter
public class Cart {

    private List<OrderItem> orderItemList;

    @Builder
    private Cart(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
