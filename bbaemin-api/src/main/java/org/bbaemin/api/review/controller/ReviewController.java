package org.bbaemin.api.review.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.api.review.service.ReviewService;
import org.bbaemin.api.review.vo.Review;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.api.review.controller.request.CreateReviewRequest;
import org.bbaemin.api.review.controller.request.UpdateReviewRequest;
import org.bbaemin.api.review.controller.response.ReviewResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 리스트
    @GetMapping
    public ApiResult<List<ReviewResponse>> listReview() {
        List<ReviewResponse> reviewList = reviewService.getReviewList().stream()
                .map(ReviewResponse::new).collect(Collectors.toList());
        return ApiResult.ok(reviewList);
    }

    // 리뷰 조회
    @GetMapping("/{reviewId}")
    public ApiResult<ReviewResponse> getReview(@PathVariable Long reviewId) {
        Review review = reviewService.getReview(reviewId);
        return ApiResult.ok(new ReviewResponse(review));
    }

    // 리뷰 등록
    @PostMapping("/orders/{orderId}/orderItems/{orderItemId}")
    public ApiResult<ReviewResponse> createReview(@PathVariable Long orderId, @PathVariable Long orderItemId,
                                                  @Validated @RequestBody CreateReviewRequest createReviewRequest) {
        Review review = Review.builder()
                .score(createReviewRequest.getScore())
                .content(createReviewRequest.getContent())
                .image(createReviewRequest.getImage())
                .build();
        Review created = reviewService.createReview(createReviewRequest.getOrderItemId(), review);
        return ApiResult.created(new ReviewResponse(created));
    }

    // 리뷰 수정
    @PatchMapping("/{reviewId}")
    public ApiResult<ReviewResponse> updateReview(@PathVariable Long reviewId,
                                                  @Validated @RequestBody UpdateReviewRequest updateReviewRequest) {
        Review updated = reviewService.updateReview(reviewId, updateReviewRequest.getScore(), updateReviewRequest.getContent(), updateReviewRequest.getImage());
        return ApiResult.ok(new ReviewResponse(updated));
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ApiResult<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiResult.ok();
    }
}
