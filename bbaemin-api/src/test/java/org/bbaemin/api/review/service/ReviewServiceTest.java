package org.bbaemin.api.review.service;

import org.bbaemin.api.order.vo.OrderItem;
import org.bbaemin.api.review.repository.ReviewRepository;
import org.bbaemin.api.review.service.ReviewService;
import org.bbaemin.api.review.vo.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @InjectMocks
    ReviewService reviewService;
    @Mock
    ReviewRepository reviewRepository;

    @Test
    void createReview() {
        // given
        // when
        Review review = mock(Review.class);
        reviewService.createReview(1L, review);

        // then
        verify(review).setOrderItem(any(OrderItem.class));
        verify(reviewRepository).save(review);
    }

    @Test
    void updateReview() {
        // given
        Review review = mock(Review.class);
        doReturn(Optional.of(review))
                .when(reviewRepository).findById(1L);
        // when
        reviewService.updateReview(1L, 5, "good", "image");

        // then
        verify(review).setScore(5);
        verify(review).setContent("good");
        verify(review).setImage("image");
    }

    @Test
    void deleteReview() {
        // given
        Review review = mock(Review.class);
        doReturn(Optional.of(review))
                .when(reviewRepository).findById(1L);
        // when
        reviewService.deleteReview(1L);

        // then
        verify(reviewRepository).delete(review);
    }
}
