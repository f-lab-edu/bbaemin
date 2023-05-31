package org.bbaemin.user.review.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.user.review.repository.ReviewRepository;
import org.bbaemin.user.review.vo.Review;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

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

    @Transactional
    public Mono<Review> createReview(Long orderItemId, Review review) {
        review.setOrderItemId(orderItemId);
        return reviewRepository.save(review);
    }

    @Transactional
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
    public Mono<Void> deleteReview(Long reviewId) {
        return reviewRepository.deleteById(reviewId);
    }
}
