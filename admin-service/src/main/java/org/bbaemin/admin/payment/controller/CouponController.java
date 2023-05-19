package org.bbaemin.admin.payment.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.admin.payment.service.CouponService;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.dto.request.ApplyCouponRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/coupons")
@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/apply")
    public ApiResult<Integer> applyCoupon(@Validated @RequestBody ApplyCouponRequest applyCouponRequest) {
        int totalDiscountAmount = couponService.apply(applyCouponRequest.getOrderAmount(), applyCouponRequest.getDiscountCouponIdList());
        return ApiResult.ok(totalDiscountAmount);
    }
}
