package org.bbaemin.admin.delivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private static final int CRITERIA = 10000;
    private static final int BASIC_COST = 3000;

    public int getDeliveryFee(int orderAmount) {
        return orderAmount >= CRITERIA ? 0 : BASIC_COST;
    }
}
