package org.bbaemin.admin.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.admin.delivery.service.DeliveryService;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.dto.request.SendDeliveryInfoRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/delivery")
@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/fee")
    public ApiResult<Integer> getDeliveryFee(@RequestParam(name = "orderAmount") Integer orderAmount) {
        int deliveryFee = deliveryService.getDeliveryFee(orderAmount);
        return ApiResult.ok(deliveryFee);
    }

    @PostMapping("/send")
    public ApiResult<Void> sendDeliveryInfo(@Validated @RequestBody SendDeliveryInfoRequest sendDeliveryInfoRequest) {
        deliveryService.sendDeliveryInfo(sendDeliveryInfoRequest.getDeliveryAddress(), sendDeliveryInfoRequest.getOrderId());
        return ApiResult.ok();
    }
}
