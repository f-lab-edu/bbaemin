package org.bbaemin.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PayRequest {

    private Long providerId;
    // 금액
    private Integer amount;
    private String referenceNumber;
    private String account;

    public PayRequest(Long providerId, Integer amount, String referenceNumber, String account) {
        this.providerId = providerId;
        this.amount = amount;
        this.referenceNumber = referenceNumber;
        this.account = account;
    }
}
