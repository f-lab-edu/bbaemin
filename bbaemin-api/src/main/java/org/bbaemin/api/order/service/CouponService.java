package org.bbaemin.api.order.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.api.order.repository.UserCouponRepository;
import org.bbaemin.api.order.vo.UserCoupon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CouponService {

    private final UserCouponRepository userCouponRepository;

    // 쿠폰 사용
    @Transactional
    public int apply(int orderAmount, List<Long> userCouponIdList) {

        int totalDiscountAmount = 0;
        for (Long userCouponId : userCouponIdList) {
            UserCoupon userCoupon = userCouponRepository.findById(userCouponId)
                    .orElseThrow(() -> new NoSuchElementException("userCouponId : " + userCouponId));

            if (userCoupon.getCoupon().getExpireDate().isBefore(LocalDateTime.now())) {
                // TODO - 예외 처리
                throw new RuntimeException();
            }

            if (userCoupon.isUsed()) {
                // TODO - 예외 처리
                throw new RuntimeException();
            }

            Integer minimumOrderAmount = userCoupon.getCoupon().getMinimumOrderAmount();
            if (minimumOrderAmount != null && minimumOrderAmount > orderAmount) {
                // TODO - 예외 처리
                throw new RuntimeException();
            }
            userCoupon.setUsed(true);
            userCoupon.setUsedAt(LocalDateTime.now());
            totalDiscountAmount += userCoupon.getCoupon().getDiscountAmount();
        }
        return totalDiscountAmount;
    }

    // TODO - 사용자 쿠폰 조회
    // TODO - 쿠폰 등록
}
