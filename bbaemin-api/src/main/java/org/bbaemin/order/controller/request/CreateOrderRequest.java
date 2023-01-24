package org.bbaemin.order.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CreateOrderRequest {

    private Long storeId;

    private List<OrderItemRequest> orderItemList;

    private String paymentMethod;   // 결제 수단

    private String deliveryAddress; // 배달주소
    private String phoneNumber;     // 전화번소
    private String email;           // 주문 내역 발송 메일
    private String messageToRider;  // 라이더님께

//    private boolean reserve;      // 바로 배달 vs 예약 배달
    // TODO - 품절 상품만 취소 vs 전체 주문 취소

    // 할인쿠폰 - 중복 사용
    private List<Long> discountCouponIdList;

    private Long orderAmount;     // 주문 금액 (할인 금액 반영)
    private Long deliveryFee;     // 배달료
    private Long paymentAmount;     // 결제 금액

    @Getter @Setter
    static class OrderItemRequest {
        private Long itemId;
        private Long orderPrice;
        private int orderCount;
        private Long totalOrderPrice;
    }
}
