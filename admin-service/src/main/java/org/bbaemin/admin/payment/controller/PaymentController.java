package org.bbaemin.admin.payment.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.admin.payment.service.PaymentService;
import org.bbaemin.admin.payment.vo.Payment;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.dto.request.CancelPayRequest;
import org.bbaemin.dto.request.PayRequest;
import org.bbaemin.dto.response.PaymentResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ApiResult<PaymentResponse> process(@Validated @RequestBody PayRequest payRequest) {
        Payment payment = paymentService.pay(payRequest.getProviderId(), payRequest.getAmount(), payRequest.getReferenceNumber(), payRequest.getAccount());
        return ApiResult.ok(new PaymentResponse(payment.getPaymentId(), payment.getStatus().name()));
    }

    @PostMapping("/cancel")
    public ApiResult<Void> cancel(@Validated @RequestBody CancelPayRequest cancelPayRequest) {
        paymentService.cancelPay(cancelPayRequest.getOrderId());
        return ApiResult.ok();
    }
}
