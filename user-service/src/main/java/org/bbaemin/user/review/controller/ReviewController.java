package org.bbaemin.user.review.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.user.review.controller.request.CreateReviewRequest;
import org.bbaemin.user.review.controller.request.UpdateReviewRequest;
import org.bbaemin.user.review.controller.response.ReviewResponse;
import org.bbaemin.user.review.service.ReviewService;
import org.bbaemin.user.review.vo.Review;
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

@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

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
        return reviewService.createReview(orderItemId, Review.builder()
                        .score(createReviewRequest.getScore())
                        .content(createReviewRequest.getContent())
                        .image(createReviewRequest.getImage())
                        .build())
                .map(review -> ReviewResponse.builder()
                        .score(review.getScore())
                        .content(review.getContent())
                        .image(review.getImage())
                        .build())
                .map(ApiResult::ok);
    }

    // 리뷰 수정
    @PatchMapping("/{reviewId}")
    public Mono<ApiResult<ReviewResponse>> updateReview(@PathVariable Long reviewId,
                                                        @Validated @RequestBody UpdateReviewRequest updateReviewRequest) {
        return reviewService.updateReview(reviewId, updateReviewRequest.getScore(), updateReviewRequest.getContent(), updateReviewRequest.getImage())
                .map(review -> ReviewResponse.builder()
                        .score(review.getScore())
                        .content(review.getContent())
                        .image(review.getImage())
                        .build())
                .map(ApiResult::ok);
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public Mono<ApiResult<Void>> deleteReview(@PathVariable Long reviewId) {
        return reviewService.deleteReview(reviewId)
                .thenReturn(ApiResult.ok());
    }
}
