package org.bbaemin.api.order.controller.response;

import lombok.Getter;
import lombok.ToString;
import org.bbaemin.api.order.vo.Order;

import static org.bbaemin.util.StringUtils.getFormattedLocalDateTime;

@ToString
@Getter
public class OrderSummaryResponse {

    private Long orderId;
    private String status;          // 주문완료, 주문취소 / 배달중, 배달완료, 배달취소
    private int paymentAmount;      // 결제 금액
    private String orderDate;       // 주문일시

    public OrderSummaryResponse(Order order) {
        this.orderId = order.getOrderId();
        this.status = order.getStatus().getName();
        this.paymentAmount = order.getPaymentAmount();
        this.orderDate = getFormattedLocalDateTime(order.getOrderDate());
    }
}
