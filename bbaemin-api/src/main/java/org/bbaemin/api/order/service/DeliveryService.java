package org.bbaemin.api.order.service;

import lombok.extern.slf4j.Slf4j;
import org.bbaemin.api.order.kafka.message.OrderCompletedMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeliveryService {

    @KafkaListener(
            topics = "order-completed-message",
            groupId = "order-completed-message-consumers",
            containerFactory = "orderCompletedMessageConcurrentKafkaListenerContainerFactory"
    )
    public void listen(@Payload OrderCompletedMessage orderCompletedMessage) {
        log.info("**************** Delivery Service Consumer ****************");
        log.info(">>>>> 주문 완료 메세지 수신 : {}", orderCompletedMessage);
        log.info("***********************************************************");
    }
}
