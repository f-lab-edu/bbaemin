package org.bbaemin.admin.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.admin.payment.enums.TransactionStatus;
import org.bbaemin.admin.payment.vo.Payment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    public Payment pay(Long providerId, Integer amount, String referenceNumber, String account) {

        log.info("************************* Pay ********************************");
        log.info(">>>>> [결제] providerId : {}, amount : {}", providerId, amount);
        log.info("**************************************************************");

        return Payment.builder()
                .providerId(providerId)
                .amount(amount)
                .referenceNumber(referenceNumber)
                .account(account)
                .status(TransactionStatus.SUCCESS)
                .build();
    }

    public void cancelPay(Long orderId) {

        log.info("************************* Cancel Pay *************************");
        log.info(">>>>> [결제 취소] orderId : {}", orderId);
        log.info("**************************************************************");
    }
}
