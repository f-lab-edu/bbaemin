package org.bbaemin.order.controller.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.bbaemin.cart.vo.Cart;
import org.bbaemin.cart.vo.CartItem;
import org.bbaemin.order.vo.Order;

import static org.bbaemin.order.enums.OrderStatus.COMPLETE_ORDER;

@Getter
public class CreateOrderRequest {

    private Long userId;

    // TODO - CHECK
//    private Long storeId;
    private String deliveryAddress; // 배달주소
    private String phoneNumber;     // 전화번호
    private String email;           // 주문 내역 발송 메일
    private String messageToRider;  // 라이더님께

//    private boolean reserve;      // 바로 배달 vs 예약 배달
    // TODO - 품절 상품만 취소 vs 전체 주문 취소

    // 할인쿠폰 - 중복 사용
    private List<Long> discountCouponIdList;

    private String paymentMethod;   // 결제 수단

//    private int orderAmount;       // 주문 금액 (할인 금액 반영)
//    private int deliveryFee;       // 배달료
//    private int paymentAmount;     // 결제 금액

    @Builder
    private CreateOrderRequest(String deliveryAddress, String phoneNumber, String email, String messageToRider, List<Long> discountCouponIdList, String paymentMethod) {
//        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.messageToRider = messageToRider;
        this.discountCouponIdList = discountCouponIdList;
        this.paymentMethod = paymentMethod;
    }

    public Order toEntity(Cart cart) {
        List<CartItem> cartItemList = cart.getCartItemList();
        int orderAmount = cartItemList.stream().mapToInt(CartItem::getTotalOrderPrice).sum();
        int deliveryFee = getDeliveryFee(orderAmount);
        return Order.builder()
                .userId(cart.getUserId())
                .orderDate(LocalDateTime.now())
                .status(COMPLETE_ORDER)
//                .storeId(storeId)
                .orderItemList(cartItemList.stream()
                        .map(CartItem::toOrderItem).collect(Collectors.toList()))
                .orderAmount(orderAmount)
                .deliveryFee(deliveryFee)
                .paymentAmount(getPaymentAmount(orderAmount, deliveryFee, getDiscountCouponIdList()))
                .paymentMethod(getPaymentMethod())
                .deliveryAddress(getDeliveryAddress())
                .phoneNumber(getPhoneNumber())
                .email(getEmail())
                .messageToRider(getMessageToRider())
                .build();
    }

    public int getPaymentAmount(int orderAmount, int deliveryFee, List<Long> discountCouponIdList) {
        return orderAmount + deliveryFee;
    }

    public int getDeliveryFee(int orderAmount) {
        return orderAmount >= 10000 ? 0 : 3000;
    }
}
