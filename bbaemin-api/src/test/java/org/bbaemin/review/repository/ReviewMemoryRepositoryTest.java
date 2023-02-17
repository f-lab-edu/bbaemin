package org.bbaemin.review.repository;

import org.bbaemin.review.vo.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ReviewRepository.class)
class ReviewMemoryRepositoryTest {

    // ReviewRepository reviewRepository = new ReviewRepository();
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void findAll() {
        List<Review> reviewList = reviewRepository.findAll();
        System.out.println(reviewList);
    }

    @Test
    void findById() {
        Review review = reviewRepository.findById(1L);
        System.out.println(review);
    }

    @Test
    void insert() {
        Review review = Review.builder()
                .orderId(1L)
                .score(5)
                .content("content")
                .image("image")
                .build();
        Review created = reviewRepository.insert(review);
//        System.out.println(created);
        assertNotNull(created.getReviewId());
        assertEquals(review.getOrderId(), created.getOrderId());
        assertEquals(review.getScore(), created.getScore());
        assertEquals(review.getContent(), created.getContent());
        assertEquals(review.getImage(), created.getImage());
    }

    @Test
    void update() {
        // given
        Review created = reviewRepository.insert(Review.builder()
                .orderId(1L)
                .score(5)
                .content("content")
                .image("image")
                .build());

        // when
        Review review = Review.builder()
                .reviewId(created.getReviewId())
                .orderId(1L)
                .score(3)
                .content("updated_content")
                .image(null)
                .build();
        Review updated = reviewRepository.update(review);

        // then
        assertEquals(created.getReviewId(), updated.getReviewId());
        assertEquals(review.getOrderId(), updated.getOrderId());
        assertEquals(review.getScore(), updated.getScore());
        assertEquals(review.getContent(), updated.getContent());
        assertEquals(review.getImage(), updated.getImage());
    }

    @Test
    void delete() {
        // given
        Review created = reviewRepository.insert(Review.builder()
                .orderId(1L)
                .score(5)
                .content("content")
                .image("image")
                .build());

        // when
        reviewRepository.delete(created.getReviewId());

        // then
        assertNull(reviewRepository.findById(created.getReviewId()));
    }
}
