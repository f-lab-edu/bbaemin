package org.bbaemin.user.order.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bbaemin.user.order.enums.OrderStatus;
import org.bbaemin.user.order.enums.PaymentMethod;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("orders")
public class Order {

    @Id
    @Column("order_id")
    private Long orderId;

    @Column("user_id")
    private Long userId;

    @Column("order_date")
    private LocalDateTime orderDate;       // 주문일시

    @Column("status")
    private String status;                 // OrderStatus : 주문완료, 주문취소 / 배달중, 배달완료, 배달취소

    @Column("order_amount")
    private int orderAmount;                // 주문 금액 (할인 금액 반영)

    @Column("delivery_fee")
    private int deliveryFee;                // 배달료

    @Column("payment_amount")
    private int paymentAmount;              // 결제 금액 = 주문 금액 + 배달료

    @Column("payment_method")
    private String paymentMethod;           // PaymentMethod : 결제 수단

    @Column("delivery_address")
    private String deliveryAddress;         // 배달주소

    @Column("phone_number")
    private String phoneNumber;             // 전화번호

    @Column("email")
    private String email;                   // 주문 내역 발송 메일

    @Column("message_to_rider")
    private String messageToRider;          // 라이더님께

    @Builder
    public Order(Long orderId, Long userId, LocalDateTime orderDate, OrderStatus status, int orderAmount, int deliveryFee, int paymentAmount,
                 PaymentMethod paymentMethod, String deliveryAddress, String phoneNumber, String email, String messageToRider) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status.name();
        this.orderAmount = orderAmount;
        this.deliveryFee = deliveryFee;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod.name();
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.messageToRider = messageToRider;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setStatus(OrderStatus status) {
        this.status = status.name();
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
