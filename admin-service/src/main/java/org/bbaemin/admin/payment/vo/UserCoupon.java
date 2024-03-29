package org.bbaemin.admin.payment.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coupon_id")
    private Long userCouponId;
/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
            nullable = false, foreignKey = @ForeignKey(name = "fk_userCoupon_user"))
    private User user;*/
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", referencedColumnName = "coupon_id",
            nullable = false, foreignKey = @ForeignKey(name = "fk_userCoupon_coupon"))
    private Coupon coupon;

    private LocalDateTime registeredAt;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean used;
    private LocalDateTime usedAt;

    @Builder
    private UserCoupon(Long userCouponId, Long userId, Coupon coupon, LocalDateTime registeredAt, boolean used, LocalDateTime usedAt) {
        this.userCouponId = userCouponId;
        this.userId = userId;
        this.coupon = coupon;
        this.registeredAt = registeredAt;
        this.used = used;
        this.usedAt = usedAt;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }
}
