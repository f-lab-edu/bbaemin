package org.bbaemin.order.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, updatable = false)
    private String code;

    @Column(nullable = false)
    private int discountAmount;         // 할인금액

    private Integer minimumOrderAmount;     // 최소주문금액

    private LocalDateTime expireDate;   // 만료일자

    @Builder
    private Coupon(Long couponId, String name, String code, int discountAmount, Integer minimumOrderAmount, LocalDateTime expireDate) {
        this.couponId = couponId;
        this.name = name;
        this.code = code;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.expireDate = expireDate;
    }
}
