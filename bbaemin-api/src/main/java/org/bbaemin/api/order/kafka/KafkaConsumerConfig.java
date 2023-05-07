package org.bbaemin.api.order.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.bbaemin.api.order.kafka.message.OrderCompletedMessage;
import org.bbaemin.api.order.kafka.message.OrderMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapAddress;

    @Bean
    public ConsumerFactory<String, OrderMessage> orderMessageConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        // 컨슈머 그룹을 식별하는 고유 아이디
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "order-message-consumers");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(OrderMessage.class));
    }

    @Bean
    public ConsumerFactory<String, OrderCompletedMessage> orderCompletedMessageConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        // 컨슈머 그룹을 식별하는 고유 아이디
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "order-completed-message-consumers");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(OrderCompletedMessage.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderMessage> orderMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderMessageConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCompletedMessage> orderCompletedMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderCompletedMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderCompletedMessageConsumerFactory());
        return factory;
    }
}
