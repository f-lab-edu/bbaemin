package org.bbaemin.order.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderDetailResponse {

    private String status;

    private String store;           // 가게
    private String description;
    private String paymentAmount;   // 결제 금액 = 주문 금액 + 배달료

    private String orderDate;       // 주문일시
    private String orderId;           // 주문번호

    private List<OrderItemResponse> orderItemList;

    private String orderAmount;     // 주문 금액 (할인 금액 반영)
    private String deliveryFee;     // 배달료

    private String paymentMethod;   // 결제 수단

    private String deliveryAddress; // 배달주소
    private String phoneNumber;     // 전화번소
    private String email;           // 주문 내역 발송 메일
    private String messageToRider;  // 라이더님께

    @Builder
    public OrderDetailResponse(String status, String store, String description, String paymentAmount, String orderDate, String orderId, List<OrderItemResponse> orderItemList, String orderAmount, String deliveryFee, String paymentMethod, String deliveryAddress, String phoneNumber, String email, String messageToRider) {
        this.status = status;
        this.store = store;
        this.description = description;
        this.paymentAmount = paymentAmount;
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.orderItemList = orderItemList;
        this.orderAmount = orderAmount;
        this.deliveryFee = deliveryFee;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.messageToRider = messageToRider;
    }
}
