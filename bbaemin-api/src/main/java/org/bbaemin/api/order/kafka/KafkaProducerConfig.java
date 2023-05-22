package org.bbaemin.api.order.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.bbaemin.api.order.kafka.message.OrderCompletedMessage;
import org.bbaemin.api.order.kafka.message.OrderMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProducerConfig {
/*
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;*/

    private Map<String, Object> getConfigProps() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }

    // Kafka Producer Config
    @Bean
    public ProducerFactory<String, OrderMessage> orderMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getConfigProps());
    }

    @Bean
    public ProducerFactory<String, OrderCompletedMessage> orderCompletedMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getConfigProps());
    }

    // Producer 생성
    @Bean
    public KafkaTemplate<String, OrderMessage> orderMessageKafkaTemplate() {
        return new KafkaTemplate<>(orderMessageProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, OrderCompletedMessage> orderCompletedMessageKafkaTemplate() {
        return new KafkaTemplate<>(orderCompletedMessageProducerFactory());
    }
}
