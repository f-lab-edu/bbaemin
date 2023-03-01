package org.bbaemin.review.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.order.service.OrderService;
import org.bbaemin.order.vo.OrderItem;
import org.bbaemin.review.repository.ReviewRepository;
import org.bbaemin.review.vo.Review;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderService orderService;

    public List<Review> getReviewList() {
        return reviewRepository.findAll();
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("reviewId : " + reviewId));
    }

    @Transactional
    public Review createReview(Long orderItemId, Review review) {
        OrderItem orderItem = orderService.getOrderItem(orderItemId);
        review.setOrderItem(orderItem);
        return reviewRepository.save(review);
    }

    @Transactional
    public Review updateReview(Long reviewId, int score, String content, String image) {
        Review review = getReview(reviewId);
        review.setScore(score);
        review.setContent(content);
        review.setImage(image);
        return review;
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = getReview(reviewId);
        reviewRepository.delete(review);
    }
}
