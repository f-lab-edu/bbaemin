package org.bbaemin.user.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.user.review.controller.request.CreateReviewRequest;
import org.bbaemin.user.review.controller.request.UpdateReviewRequest;
import org.bbaemin.user.review.controller.response.ReviewResponse;
import org.bbaemin.user.review.kafka.ReviewEventProducer;
import org.bbaemin.user.review.kafka.message.CreateReviewMessage;
import org.bbaemin.user.review.kafka.message.DeleteReviewMessage;
import org.bbaemin.user.review.kafka.message.UpdateReviewMessage;
import org.bbaemin.user.review.service.ReviewService;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewEventProducer reviewEventProducer;

    // 리뷰 리스트
    @GetMapping
    public Mono<ApiResult<List<ReviewResponse>>> listReview() {
        return reviewService.getReviewList().map(review -> ReviewResponse.builder()
                        .score(review.getScore())
                        .content(review.getContent())
                        .image(review.getImage())
                        .build())
                .collectList()
                .map(ApiResult::ok);
    }

    // 리뷰 조회
    @GetMapping("/{reviewId}")
    public Mono<ApiResult<ReviewResponse>> getReview(@PathVariable Long reviewId) {
        return reviewService.getReview(reviewId)
                .map(review -> ReviewResponse.builder()
                        .score(review.getScore())
                        .content(review.getContent())
                        .image(review.getImage())
                        .build())
                .map(ApiResult::ok);
    }

    // 리뷰 등록
    @PostMapping("/orders/{orderId}/orderItems/{orderItemId}")
    public Mono<ApiResult<ReviewResponse>> createReview(@PathVariable Long orderId, @PathVariable Long orderItemId,
                                                        @Validated @RequestBody CreateReviewRequest createReviewRequest) {

        CreateReviewMessage createReviewMessage = new CreateReviewMessage(orderItemId, createReviewRequest.getScore(), createReviewRequest.getContent(), createReviewRequest.getImage());
        ListenableFuture<SendResult<String, CreateReviewMessage>> future = reviewEventProducer.createReview(createReviewMessage);
//        future.addCallback(result -> log.info("success"), ex -> log.error("fail : {}", ex.getMessage()));

        ReviewResponse reviewResponse = ReviewResponse.builder()
                .score(createReviewRequest.getScore())
                .content(createReviewRequest.getContent())
                .image(createReviewRequest.getImage())
                .build();
        return Mono.just(ApiResult.created(reviewResponse));
    }

    // 리뷰 수정
    @PatchMapping("/{reviewId}")
    public Mono<ApiResult<ReviewResponse>> updateReview(@PathVariable Long reviewId,
                                                        @Validated @RequestBody UpdateReviewRequest updateReviewRequest) {

        UpdateReviewMessage updateReviewMessage = new UpdateReviewMessage(reviewId, updateReviewRequest.getScore(), updateReviewRequest.getContent(), updateReviewRequest.getImage());
        reviewEventProducer.updateReview(updateReviewMessage);

        return reviewService.getReview(reviewId)
                .map(review -> ReviewResponse.builder()
                        .score(updateReviewRequest.getScore())
                        .content(updateReviewRequest.getContent())
                        .image(updateReviewRequest.getImage())
                        .build())
                .map(ApiResult::ok);
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public Mono<ApiResult<Void>> deleteReview(@PathVariable Long reviewId) {

        DeleteReviewMessage deleteReviewMessage = new DeleteReviewMessage(reviewId);
        reviewEventProducer.deleteReview(deleteReviewMessage);

        return Mono.just(ApiResult.ok());
    }
}
