package org.bbaemin.api.order.kafka;

import lombok.RequiredArgsConstructor;
import org.bbaemin.api.order.kafka.message.OrderCompletedMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderCompletedEventProducer {

    private String TOPIC = "order-completed-message";

    private final KafkaTemplate<String, OrderCompletedMessage> orderCompletedMessageKafkaTemplate;

    public void send(OrderCompletedMessage message) {
        orderCompletedMessageKafkaTemplate.send(TOPIC, message);
    }
}
