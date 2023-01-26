package org.bbaemin.order.controller.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import org.bbaemin.order.vo.Order;

@ToString
@Getter
public class OrderResponse {

    private Long orderId;           // 주문번호

    private String status;

    // TODO - CHECK
//    private String store;           // 가게
    private String description;
    private String paymentAmount;   // 결제 금액 = 주문 금액 + 배달료

    private String orderDate;       // 주문일시

    private List<OrderItemResponse> orderItemList;

    private String orderAmount;     // 주문 금액 (할인 금액 반영)
    private String deliveryFee;     // 배달료

    private String paymentMethod;   // 결제 수단

    private String deliveryAddress; // 배달주소
    private String phoneNumber;     // 전화번호
    private String email;           // 주문 내역 발송 메일
    private String messageToRider;  // 라이더님께

    public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.status = order.getStatus().getName();
        this.description = order.getDescription();
        this.paymentAmount = order.getFormattedPaymentAmount();
        this.orderDate = order.getFormattedOrderDate();
        this.orderItemList = order.getOrderItemList().stream()
                .map(OrderItemResponse::new).collect(Collectors.toList());
        this.orderAmount = order.getFormattedOrderAmount();
        this.deliveryFee = order.getFormattedDeliveryFee();
        this.paymentMethod = order.getPaymentMethod();
        this.deliveryAddress = order.getDeliveryAddress();
        this.phoneNumber = order.getPhoneNumber();
        this.email = order.getEmail();
        this.messageToRider = order.getMessageToRider();
    }
}
