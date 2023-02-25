package org.bbaemin.order.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bbaemin.order.enums.OrderStatus;
import org.bbaemin.order.enums.PaymentMethod;
import org.bbaemin.user.vo.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
            nullable = false, foreignKey = @ForeignKey(name = "fk_order_user"))
    private User user;                      // orderer

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;       // 주문일시

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;             // 주문완료, 주문취소 / 배달중, 배달완료, 배달취소

    @Column(name = "order_amount", nullable = false)
    private int orderAmount;                // 주문 금액 (할인 금액 반영)

    @Column(name = "delivery_fee", nullable = false)
    private int deliveryFee;                // 배달료

    @Column(name = "payment_amount", nullable = false)
    private int paymentAmount;              // 결제 금액 = 주문 금액 + 배달료

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;    // 결제 수단

//    private Long storeId;                 // 가게

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;         // 배달주소

    @Column(name = "phone_number")
    private String phoneNumber;             // 전화번호

    @Column(name = "email")
    private String email;                   // 주문 내역 발송 메일

    @Column(name = "message_to_rider")
    @Lob
    private String messageToRider;          // 라이더님께

    @Builder
    public Order(Long orderId, User user, LocalDateTime orderDate, OrderStatus status, int orderAmount, int deliveryFee, int paymentAmount,
            PaymentMethod paymentMethod, String deliveryAddress, String phoneNumber, String email, String messageToRider) {
        this.orderId = orderId;
        this.user = user;
        this.orderDate = orderDate;
        this.status = status;
        this.orderAmount = orderAmount;
        this.deliveryFee = deliveryFee;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.messageToRider = messageToRider;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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
