package org.bbaemin.user.order.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

import static org.bbaemin.util.StringUtils.getFormattedLocalDateTime;

@ToString
@Getter
public class OrderSummaryResponse {

    private Long orderId;
    private String status;          // 주문완료, 주문취소 / 배달중, 배달완료, 배달취소
    private int paymentAmount;      // 결제 금액
    private String orderDate;       // 주문일시

    @Builder
    private OrderSummaryResponse(Long orderId, String status, int paymentAmount, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.status = status;
        this.paymentAmount = paymentAmount;
        this.orderDate = getFormattedLocalDateTime(orderDate);
    }
}
