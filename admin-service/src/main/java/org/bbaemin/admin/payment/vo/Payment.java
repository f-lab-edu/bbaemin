package org.bbaemin.admin.payment.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bbaemin.admin.payment.enums.TransactionStatus;

// entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    private Long paymentId;

    private Long providerId;
    // 금액
    private Integer amount;
    private String referenceNumber;
    private String account;
    private TransactionStatus status;

    @Builder
    private Payment(Long paymentId, Long providerId, Integer amount, String referenceNumber, String account, TransactionStatus status) {
        this.paymentId = paymentId;
        this.providerId = providerId;
        this.amount = amount;
        this.referenceNumber = referenceNumber;
        this.account = account;
        this.status = status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
