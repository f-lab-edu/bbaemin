package org.bbaemin.order.vo;

import java.time.LocalDateTime;

public class Coupon {

    private Long couponId;
    private String name;
    private String code;

    private int discountAmount;         // 할인금액
    private int minimumOrderAmount;     // 최소주문금액

    private LocalDateTime expireDate;   // 만료일자
}
