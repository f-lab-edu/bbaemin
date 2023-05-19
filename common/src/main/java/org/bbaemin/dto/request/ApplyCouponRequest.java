package org.bbaemin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApplyCouponRequest {

    private int orderAmount;
    private List<Long> discountCouponIdList;
}
