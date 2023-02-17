package org.bbaemin.review.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.review.repository.ReviewRepository;
import org.bbaemin.review.vo.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> getReviewList() {
        return reviewRepository.findAll();
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    public Review createReview(Review review) {
        return reviewRepository.insert(review);
    }

    public Review updateReview(Long reviewId, int score, String content, String image) {
        Review review = reviewRepository.findById(reviewId);
        review.setScore(score);
        review.setContent(content);
        review.setImage(image);
        return reviewRepository.update(review);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.delete(reviewId);
    }
}
