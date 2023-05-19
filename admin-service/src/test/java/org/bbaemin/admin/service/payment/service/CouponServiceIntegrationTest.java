package org.bbaemin.admin.service.payment.service;

import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@Transactional
@SpringBootTest(properties = {"spring.config.location=classpath:application-test.yml"})
class CouponServiceIntegrationTest {
/*
    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository;
    @Autowired
    UserCouponRepository userCouponRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void apply() {
        // given
        User user = userRepository.save(User.builder()
                .email("user@email.com")
                .nickname("user")
                .image(null)
                .password("password")
                .phoneNumber("010-1234-5678")
                .build());
        Coupon coupon = couponRepository.save(Coupon.builder()
                .name("coupon")
                .code("ABCD")
                .discountAmount(1000)
                .minimumOrderAmount(20000)
                .expireDate(LocalDateTime.of(2023, 12, 31, 23, 59, 59))
                .build());
        UserCoupon userCoupon = userCouponRepository.save(UserCoupon.builder()
                .user(user)
                .coupon(coupon)
                .registeredAt(LocalDateTime.of(2023, 2, 28, 12, 54, 11))
                .build());

        // when
        int totalDiscountAmount = couponService.apply(30000, Arrays.asList(userCoupon.getUserCouponId()));

        // then
        assertThat(totalDiscountAmount).isEqualTo(coupon.getDiscountAmount());
        UserCoupon find = userCouponRepository.findById(userCoupon.getUserCouponId()).orElseThrow();
        assertThat(find.isUsed()).isTrue();
        assertThat(find.getUsedAt()).isNotNull();
    }

    @Test
    void apply_coupons() {
        // given
        User user = userRepository.save(User.builder()
                .email("user@email.com")
                .nickname("user")
                .image(null)
                .password("password")
                .phoneNumber("010-1234-5678")
                .build());
        Coupon coupon1 = couponRepository.save(Coupon.builder()
                .name("coupon1")
                .code("ABCD")
                .discountAmount(1000)
                .minimumOrderAmount(20000)
                .expireDate(LocalDateTime.of(2023, 12, 31, 23, 59, 59))
                .build());
        UserCoupon userCoupon1 = userCouponRepository.save(UserCoupon.builder()
                .user(user)
                .coupon(coupon1)
                .registeredAt(LocalDateTime.of(2023, 2, 28, 12, 54, 11))
                .build());

        Coupon coupon2 = couponRepository.save(Coupon.builder()
                .name("coupon2")
                .code("EFGH")
                .discountAmount(2000)
                .minimumOrderAmount(30000)
                .expireDate(LocalDateTime.of(2023, 12, 31, 23, 59, 59))
                .build());
        UserCoupon userCoupon2 = userCouponRepository.save(UserCoupon.builder()
                .user(user)
                .coupon(coupon2)
                .registeredAt(LocalDateTime.of(2023, 2, 28, 12, 54, 11))
                .build());

        // when
        int totalDiscountAmount = couponService.apply(30000, Arrays.asList(userCoupon1.getUserCouponId(), userCoupon2.getUserCouponId()));

        // then
        assertThat(totalDiscountAmount).isEqualTo(coupon1.getDiscountAmount() + coupon2.getDiscountAmount());
        UserCoupon find1 = userCouponRepository.findById(userCoupon1.getUserCouponId()).orElseThrow();
        assertThat(find1.isUsed()).isTrue();
        assertThat(find1.getUsedAt()).isNotNull();
        UserCoupon find2 = userCouponRepository.findById(userCoupon2.getUserCouponId()).orElseThrow();
        assertThat(find2.isUsed()).isTrue();
        assertThat(find2.getUsedAt()).isNotNull();
    }*/
}
