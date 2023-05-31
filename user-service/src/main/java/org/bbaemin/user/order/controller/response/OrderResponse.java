package org.bbaemin.user.order.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bbaemin.user.order.vo.OrderItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.bbaemin.util.StringUtils.getFormattedLocalDateTime;

@ToString
@Getter
public class OrderResponse {

    private Long orderId;           // 주문번호
    private String status;

    // TODO - CHECK
//    private String store;         // 가게
    private int paymentAmount;      // 결제 금액 = 주문 금액 + 배달료

    private String orderDate;       // 주문일시
    private int orderAmount;        // 주문 금액 (할인 금액 반영)
    private int deliveryFee;        // 배달료

    private String paymentMethod;   // 결제 수단

    private String deliveryAddress; // 배달주소
    private String phoneNumber;     // 전화번호
    private String email;           // 주문 내역 발송 메일
    private String messageToRider;  // 라이더님께

    private List<OrderItemResponse> orderItemList;

    @Builder
    private OrderResponse(Long orderId, String status, int paymentAmount, LocalDateTime orderDate,
                         int orderAmount, int deliveryFee, String paymentMethod, String deliveryAddress,
                         String phoneNumber, String email, String messageToRider, List<OrderItem> orderItemList) {
        this.orderId = orderId;
        this.status = status;
        this.paymentAmount = paymentAmount;
        this.orderDate = getFormattedLocalDateTime(orderDate);
        this.orderAmount = orderAmount;
        this.deliveryFee = deliveryFee;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.messageToRider = messageToRider;

        this.orderItemList = orderItemList.stream()
                .map(orderItem -> OrderItemResponse.builder()
                        .itemName(orderItem.getItemName())
                        .itemDescription(orderItem.getItemDescription())
                        .orderPrice(orderItem.getOrderPrice())
                        .orderCount(orderItem.getOrderCount())
                        .build())
                .collect(Collectors.toList());
    }
}
