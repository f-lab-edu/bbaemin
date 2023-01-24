package org.bbaemin.order.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Order {

    private Long orderId;

    private Long userId;            // orderer

    // TODO - enum으로 변경
    private String status;          // 주문완료, 주문취소 / 배달중, 배달완료, 배달취소

    private String store;           // 가게
    private String description;
    private Long paymentAmount;   // 결제 금액 = 주문 금액 + 배달료

    private LocalDateTime orderDate;       // 주문일시

//    private List<OrderItem> orderItemList;

    private Long orderAmount;     // 주문 금액 (할인 금액 반영)
    private Long deliveryFee;     // 배달료

    // TODO - enum으로 변경
    private String paymentMethod;   // 결제 수단

    private String deliveryAddress; // 배달주소
    private String phoneNumber;     // 전화번호
    private String email;           // 주문 내역 발송 메일
    private String messageToRider;  // 라이더님께

    @Builder
    private Order(Long orderId, String status, String description, String paymentAmount, String orderDate) {
        this.orderId = orderId;
        this.status = status;
        this.description = description;
        this.paymentAmount = paymentAmount;
        this.orderDate = orderDate;
    }
}
