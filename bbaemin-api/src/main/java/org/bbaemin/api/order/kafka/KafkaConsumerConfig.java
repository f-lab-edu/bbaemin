package org.bbaemin.api.order.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.bbaemin.api.order.service.OrderCompletedMessage;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ConsumerFactory<String, OrderCompletedMessage> orderCompletedMessageConsumerFactory() {

        JsonDeserializer<OrderCompletedMessage> orderCompletedMessageJsonDeserializer = new JsonDeserializer<>(OrderCompletedMessage.class);
        orderCompletedMessageJsonDeserializer.setRemoveTypeHeaders(false);
        orderCompletedMessageJsonDeserializer.addTrustedPackages("*");
        orderCompletedMessageJsonDeserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        // 컨슈머 그룹을 식별하는 고유 아이디
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "order-completed-consumers");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), orderCompletedMessageJsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCompletedMessage> orderCompletedMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderCompletedMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderCompletedMessageConsumerFactory());
        return factory;
    }
}
