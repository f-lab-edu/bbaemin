package org.bbaemin.user.review.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
/*
    @InjectMocks
    ReviewService reviewService;
    @Mock
    ReviewRepository reviewRepository;
    @Mock
    OrderService orderService;

    @Test
    void createReview() {
        // given
        // when
        Review review = mock(Review.class);
        OrderItem orderItem = mock(OrderItem.class);
        doReturn(orderItem)
                .when(orderService).getOrderItem(1L);
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
    }*/
}
