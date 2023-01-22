package org.bbaemin.order.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponse {

    private Long orderId;
    private String status;          // 주문완료, 주문취소 / 배달중, 배달완료, 배달취소
    private String description;
    private String paymentAmount;   // 결제 금액
    private String orderDate;       // 주문일시

    @Builder
    private OrderResponse(Long orderId, String status, String description, String paymentAmount, String orderDate) {
        this.orderId = orderId;
        this.status = status;
        this.description = description;
        this.paymentAmount = paymentAmount;
        this.orderDate = orderDate;
    }
}
