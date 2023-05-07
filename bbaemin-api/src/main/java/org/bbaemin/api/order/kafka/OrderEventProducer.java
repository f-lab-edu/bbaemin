package org.bbaemin.api.order.kafka;

import lombok.RequiredArgsConstructor;
import org.bbaemin.api.order.kafka.message.OrderMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderEventProducer {

    private String TOPIC = "order-message";

    private final KafkaTemplate<String, OrderMessage> orderMessageKafkaTemplate;

    public void send(OrderMessage message) {
        orderMessageKafkaTemplate.send(TOPIC, message);
    }
}
