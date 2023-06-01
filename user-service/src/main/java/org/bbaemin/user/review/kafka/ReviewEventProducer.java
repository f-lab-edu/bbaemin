package org.bbaemin.user.review.kafka;

import lombok.RequiredArgsConstructor;
import org.bbaemin.user.review.kafka.message.CreateReviewMessage;
import org.bbaemin.user.review.kafka.message.DeleteReviewMessage;
import org.bbaemin.user.review.kafka.message.UpdateReviewMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@RequiredArgsConstructor
@Component
public class ReviewEventProducer {

    private String TOPIC_CREATE_REVIEW = "create-review-message";
    private String TOPIC_UPDATE_REVIEW = "update-review-message";
    private String TOPIC_DELETE_REVIEW = "delete-review-message";

    private final KafkaTemplate<String, CreateReviewMessage> createReviewMessageKafkaTemplate;
    private final KafkaTemplate<String, UpdateReviewMessage> updateReviewMessageKafkaTemplate;
    private final KafkaTemplate<String, DeleteReviewMessage> deleteReviewMessageKafkaTemplate;

    public ListenableFuture<SendResult<String, CreateReviewMessage>> createReview(CreateReviewMessage message) {
        return createReviewMessageKafkaTemplate.send(TOPIC_CREATE_REVIEW, message);
    }

    public ListenableFuture<SendResult<String, UpdateReviewMessage>> updateReview(UpdateReviewMessage message) {
        return updateReviewMessageKafkaTemplate.send(TOPIC_UPDATE_REVIEW, message);
    }

    public ListenableFuture<SendResult<String, DeleteReviewMessage>> deleteReview(DeleteReviewMessage message) {
        return deleteReviewMessageKafkaTemplate.send(TOPIC_DELETE_REVIEW, message);
    }
}
