package org.bbaemin.user.review.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.bbaemin.user.review.kafka.message.CreateReviewMessage;
import org.bbaemin.user.review.kafka.message.DeleteReviewMessage;
import org.bbaemin.user.review.kafka.message.UpdateReviewMessage;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;    // 192.168.56.101:9092

    private Map<String, Object> getConfigProps() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }

    @Bean
    public ProducerFactory<String, CreateReviewMessage> createReviewMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getConfigProps());
    }

    @Bean
    public ProducerFactory<String, UpdateReviewMessage> updateReviewMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getConfigProps());
    }

    @Bean
    public ProducerFactory<String, DeleteReviewMessage> deleteReviewMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getConfigProps());
    }

    // Producer 생성
    @Bean
    public KafkaTemplate<String, CreateReviewMessage> createReviewMessageKafkaTemplate() {
        return new KafkaTemplate<>(createReviewMessageProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, UpdateReviewMessage> updateReviewMessageKafkaTemplate() {
        return new KafkaTemplate<>(updateReviewMessageProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, DeleteReviewMessage> deleteReviewMessageKafkaTemplate() {
        return new KafkaTemplate<>(deleteReviewMessageProducerFactory());
    }
}
