package org.bbaemin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    public ConsumerFactory<String, OrderCompletedMessage> orderMessageConsumerFactory() {

    }
}
