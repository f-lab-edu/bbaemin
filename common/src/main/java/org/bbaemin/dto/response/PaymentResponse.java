package org.bbaemin.dto.response;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PaymentResponse {

    private Long paymentId;
    private String status;

    public PaymentResponse(Long paymentId, String status) {
        this.paymentId = paymentId;
        this.status = status;
    }
}
