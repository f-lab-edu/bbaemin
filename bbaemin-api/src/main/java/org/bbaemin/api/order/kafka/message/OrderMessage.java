package org.bbaemin.api.order.kafka.message;

import lombok.Data;
import org.bbaemin.api.order.vo.Order;

import java.util.List;

@Data
public class OrderMessage {

    private Long userId;
    private Order order;
    private List<Long> discountCouponIdList;

    public OrderMessage(Long userId, Order order, List<Long> discountCouponIdList) {
        this.userId = userId;
        this.order = order;
        this.discountCouponIdList = discountCouponIdList;
    }
}
