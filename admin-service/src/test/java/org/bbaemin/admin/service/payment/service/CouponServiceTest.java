package org.bbaemin.admin.service.payment.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {
/*
    @InjectMocks
    CouponService couponService;
    @Mock
    UserCouponRepository userCouponRepository;

    @DisplayName("쿠폰 적용")
    @Test
    void apply_coupon() {
        // given
        User user = mock(User.class);
        Coupon coupon = Coupon.builder()
                .couponId(1L)
                .name("coupon")
                .code("ABCD")
                .discountAmount(2000)
                .minimumOrderAmount(10000)
                .expireDate(LocalDateTime.of(2023, 5, 10, 23, 59, 59))
                .build();
        UserCoupon userCoupon = UserCoupon.builder()
                .userCouponId(1L)
                .user(user)
                .coupon(coupon)
                .registeredAt(LocalDateTime.of(2023, 2, 25, 10, 34, 33))
                .used(false)
                .build();
        doReturn(Optional.of(userCoupon))
                .when(userCouponRepository).findById(1L);

        // when
        int totalDiscountAmount = couponService.apply(20000, List.of(1L));
        // then
        assertTrue(userCoupon.isUsed());
        assertNotNull(userCoupon.getUsedAt());
        assertEquals(coupon.getDiscountAmount(), totalDiscountAmount);
    }

    @DisplayName("사용기한이 만료된 쿠폰")
    @Test
    void apply_already_expired() {
        // given
        User user = mock(User.class);
        Coupon coupon = Coupon.builder()
                .couponId(1L)
                .name("coupon")
                .code("ABCD")
                .discountAmount(2000)
                .minimumOrderAmount(10000)
                .expireDate(LocalDateTime.of(2021, 5, 10, 23, 59, 59))
                .build();
        UserCoupon userCoupon = UserCoupon.builder()
                .userCouponId(1L)
                .user(user)
                .coupon(coupon)
                .registeredAt(LocalDateTime.of(2023, 2, 25, 10, 34, 33))
                .used(false)
                .build();
        doReturn(Optional.of(userCoupon))
                .when(userCouponRepository).findById(1L);

        // when
        // then
        assertThrows(RuntimeException.class, () -> {
            couponService.apply(5000, List.of(1L));
        });
    }

    @DisplayName("쿠폰 적용 - 최소주문금액 미충족")
    @Test
    void apply_coupon_when_orderAmount_is_not_enough() {
        // given
        User user = mock(User.class);
        Coupon coupon = Coupon.builder()
                .couponId(1L)
                .name("coupon")
                .code("ABCD")
                .discountAmount(2000)
                .minimumOrderAmount(10000)
                .expireDate(LocalDateTime.of(2023, 5, 10, 23, 59, 59))
                .build();
        UserCoupon userCoupon = UserCoupon.builder()
                .userCouponId(1L)
                .user(user)
                .coupon(coupon)
                .registeredAt(LocalDateTime.of(2023, 2, 25, 10, 34, 33))
                .used(false)
                .build();
        doReturn(Optional.of(userCoupon))
                .when(userCouponRepository).findById(1L);

        // when
        assertThrows(RuntimeException.class, () -> {
            couponService.apply(5000, List.of(1L));
        });
        // then
        assertFalse(userCoupon.isUsed());
        assertNull(userCoupon.getUsedAt());
    }

    @DisplayName("이미 사용된 쿠폰")
    @Test
    void apply_already_used_coupon() {
        // given
        User user = mock(User.class);
        Coupon coupon = Coupon.builder()
                .couponId(1L)
                .name("coupon")
                .code("ABCD")
                .discountAmount(2000)
                .minimumOrderAmount(10000)
                .expireDate(LocalDateTime.of(2023, 5, 10, 23, 59, 59))
                .build();
        UserCoupon userCoupon = UserCoupon.builder()
                .userCouponId(1L)
                .user(user)
                .coupon(coupon)
                .registeredAt(LocalDateTime.of(2023, 2, 25, 10, 34, 33))
                .used(true)
                .usedAt(LocalDateTime.of(2023, 2, 25, 10, 40, 33))
                .build();
        doReturn(Optional.of(userCoupon))
                .when(userCouponRepository).findById(1L);

        // when
        // then
        assertThrows(RuntimeException.class, () -> {
            couponService.apply(5000, List.of(1L));
        });
    }*/
}
