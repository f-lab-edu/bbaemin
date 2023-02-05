package org.bbaemin.order.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.bbaemin.cart.vo.Cart;
import org.bbaemin.cart.vo.CartItem;
import org.bbaemin.order.enums.OrderStatus;

import static org.bbaemin.order.enums.OrderStatus.CANCEL_ORDER;

@ToString
@Getter
public class Order {

    private Long orderId;

    private Long userId;            // orderer
    private LocalDateTime orderDate;       // 주문일시

    private OrderStatus status;          // 주문완료, 주문취소 / 배달중, 배달완료, 배달취소

    private List<OrderItem> orderItemList;

    private int orderAmount;     // 주문 금액 (할인 금액 반영)
    private int deliveryFee;     // 배달료
    private int paymentAmount;   // 결제 금액 = 주문 금액 + 배달료

    // TODO - enum으로 변경
    private String paymentMethod;   // 결제 수단

//    private Long storeId;           // 가게
    private String deliveryAddress; // 배달주소
    private String phoneNumber;     // 전화번호
    private String email;           // 주문 내역 발송 메일
    private String messageToRider;  // 라이더님께

    private List<Long> discountCouponIdList;

    @Builder
    public Order(Long orderId, Long userId, LocalDateTime orderDate, OrderStatus status, List<OrderItem> orderItemList, int orderAmount, int deliveryFee, int paymentAmount,
            String paymentMethod, String deliveryAddress, String phoneNumber, String email, String messageToRider, List<Long> discountCouponIdList) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.orderItemList = orderItemList;
        this.orderAmount = orderAmount;
        this.deliveryFee = deliveryFee;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.messageToRider = messageToRider;
        this.discountCouponIdList = discountCouponIdList;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        List<OrderItem> orderItemList = getOrderItemList();
        StringBuilder sb = new StringBuilder(orderItemList.get(0).getItem().getName());
        int size = orderItemList.size();
        if (size > 1) {
            sb.append(String.format(" 외 %d개", size - 1));
        }
        return sb.toString();
    }

    public String getFormattedPaymentAmount() {
        return String.format("%,d원", getPaymentAmount());
    }

    public String getFormattedOrderAmount() {
        return String.format("%,d원", getOrderAmount());
    }

    public String getFormattedDeliveryFee() {
        return String.format("%,d원", getDeliveryFee());
    }

    public String getFormattedOrderDate() {
        return getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public void setCart(Cart cart) {

        List<CartItem> cartItemList = cart.getCartItemList();
        int orderAmount = cartItemList.stream().mapToInt(CartItem::getTotalOrderPrice).sum();
        int deliveryFee = getDeliveryFee(orderAmount);

        this.orderItemList = cartItemList.stream()
                .map(CartItem::toOrderItem).collect(Collectors.toList());
        this.orderAmount = orderAmount;
        this.deliveryFee = deliveryFee;
        this.paymentAmount = getPaymentAmount(orderAmount, deliveryFee, getDiscountCouponIdList());
    }

    private int getPaymentAmount(int orderAmount, int deliveryFee, List<Long> discountCouponIdList) {
        return orderAmount + deliveryFee;
    }

    private int getDeliveryFee(int orderAmount) {
        return orderAmount >= 10000 ? 0 : 3000;
    }
}
