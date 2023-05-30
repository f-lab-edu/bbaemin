package org.bbaemin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SendDeliveryInfoRequest {

    private String deliveryAddress;
    private Long orderId;
}
