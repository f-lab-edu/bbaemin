package org.bbaemin.api.order.kafka;

import lombok.RequiredArgsConstructor;
import org.bbaemin.api.order.service.OrderCompletedMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@RequiredArgsConstructor
@Component
public class OrderCompletedEventProducer {

    private String TOPIC = "order-completed-message-v1";

    private final KafkaTemplate<String, OrderCompletedMessage> orderCompletedMessageKafkaTemplate;

    public void send(OrderCompletedMessage message) {
//        ListenableFuture<SendResult<String, OrderCompletedMessage>> future =
                orderCompletedMessageKafkaTemplate.send(TOPIC, message);
    }
}
