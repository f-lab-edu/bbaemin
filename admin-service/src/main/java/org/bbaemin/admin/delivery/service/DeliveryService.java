package org.bbaemin.admin.delivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {

    private static final int CRITERIA = 10000;
    private static final int BASIC_COST = 3000;

    public int getDeliveryFee(int orderAmount) {
        return orderAmount >= CRITERIA ? 0 : BASIC_COST;
    }

    public void sendDeliveryInfo(String deliveryAddress, Long orderId) {

        log.info("************************** Send Delivery Info **************************");
        log.info(">>>>> [배달정보 전달] address : {}, orderId : {}", deliveryAddress, orderId);
        log.info("************************************************************************");
    }
}
