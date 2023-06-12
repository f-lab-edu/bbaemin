package org.bbaemin.user.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.user.review.kafka.message.CreateReviewMessage;
import org.bbaemin.user.review.kafka.message.DeleteReviewMessage;
import org.bbaemin.user.review.kafka.message.UpdateReviewMessage;
import org.bbaemin.user.review.repository.ReviewRepository;
import org.bbaemin.user.review.vo.Review;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Flux<Review> getReviewList() {
        return reviewRepository.findAll();
    }

    public Mono<Review> getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("reviewId : " + reviewId)));
    }

    public Mono<Review> createReview(Long orderItemId, Review review) {
        review.setOrderItemId(orderItemId);
        return reviewRepository.save(review);
    }

    @Transactional
    @KafkaListener(
            topics = "create-review-message",
            groupId = "create-review-message-consumers",
            containerFactory = "createReviewMessageConcurrentKafkaListenerContainerFactory"
    )
    public Mono<Review> createReview(@Payload CreateReviewMessage createReviewMessage) {

        log.info("**************** CreateReviewEventListener ****************");
        log.info(">>>>> 리뷰 등록 메세지 수신 : {}", createReviewMessage);
        log.info("***********************************************************");

        Long orderItemId = createReviewMessage.getOrderItemId();
        Review review = Review.builder()
                .score(createReviewMessage.getScore())
                .content(createReviewMessage.getContent())
                .image(createReviewMessage.getImage())
                .build();
        return createReview(orderItemId, review);
    }

    public Mono<Review> updateReview(Long reviewId, int score, String content, String image) {
        return getReview(reviewId)
                .map(review -> {
                    review.setScore(score);
                    review.setContent(content);
                    review.setImage(image);
                    return review;
                })
                .flatMap(reviewRepository::save);
    }

    @Transactional
    @KafkaListener(
            topics = "update-review-message",
            groupId = "update-review-message-consumers",
            containerFactory = "updateReviewMessageConcurrentKafkaListenerContainerFactory"
    )
    public Mono<Review> updateReview(@Payload UpdateReviewMessage updateReviewMessage) {

        log.info("**************** UpdateReviewEventListener ****************");
        log.info(">>>>> 리뷰 수정 메세지 수신 : {}", updateReviewMessage);
        log.info("***********************************************************");

        Long reviewId = updateReviewMessage.getReviewId();
        return updateReview(reviewId, updateReviewMessage.getScore(), updateReviewMessage.getContent(), updateReviewMessage.getImage());
    }

    public Mono<Void> deleteReview(Long reviewId) {
        return reviewRepository.deleteById(reviewId);
    }

    @Transactional
    @KafkaListener(
            topics = "delete-review-message",
            groupId = "delete-review-message-consumers",
            containerFactory = "deleteReviewMessageConcurrentKafkaListenerContainerFactory"
    )
    public Mono<Void> deleteReview(@Payload DeleteReviewMessage deleteReviewMessage) {

        log.info("**************** DeleteReviewEventListener ****************");
        log.info(">>>>> 리뷰 삭제 메세지 수신 : {}", deleteReviewMessage);
        log.info("***********************************************************");

        return deleteReview(deleteReviewMessage.getReviewId());
    }
}
