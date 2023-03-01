package org.bbaemin.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bbaemin.order.vo.OrderItem;
import org.bbaemin.review.controller.request.CreateReviewRequest;
import org.bbaemin.review.controller.request.UpdateReviewRequest;
import org.bbaemin.review.service.ReviewService;
import org.bbaemin.review.vo.Review;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReviewController.class,
        properties = {"spring.config.location=classpath:application-test.yml"})
class ReviewControllerTest {

    private final String BASE_URL = "/api/v1/reviews";

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ReviewService reviewService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listReview() throws Exception {
        // given
        OrderItem orderItem = mock(OrderItem.class);
        Review review = Review.builder()
                .reviewId(1L)
                .orderItem(orderItem)
                .score(5)
                .content("good")
                .image(null)
                .build();
        doReturn(List.of(review))
                .when(reviewService).getReviewList();

        // when
        // then
        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result").value(Matchers.hasSize(1)));
        verify(reviewService).getReviewList();
    }

    @Test
    void getReview() throws Exception {
        // given
        OrderItem orderItem = mock(OrderItem.class);
        Review review = Review.builder()
                .reviewId(1L)
                .orderItem(orderItem)
                .score(5)
                .content("good")
                .image(null)
                .build();
        doReturn(review)
                .when(reviewService).getReview(1L);

        // when
        // then
        mockMvc.perform(get(BASE_URL + "/{reviewId}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.score").value(review.getScore()))
                .andExpect(jsonPath("$.result.content").value(review.getContent()))
                .andExpect(jsonPath("$.result.image").value(review.getImage()));
        verify(reviewService).getReview(1L);
    }

    @Test
    void createReview() throws Exception {
        // given
        OrderItem orderItem = mock(OrderItem.class);
        Review review = Review.builder()
                .reviewId(1L)
                .orderItem(orderItem)
                .score(5)
                .content("good")
                .image(null)
                .build();
        doReturn(review)
                .when(reviewService).createReview(anyLong(), any(Review.class));

        // when
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .orderItemId(1L)
                .score(5)
                .content("good")
                .image(null)
                .build();
        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createReviewRequest)))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CREATED"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.score").value(review.getScore()))
                .andExpect(jsonPath("$.result.content").value(review.getContent()))
                .andExpect(jsonPath("$.result.image").value(review.getImage()));
        verify(reviewService).createReview(anyLong(), any(Review.class));
    }

    @Test
    void updateReview() throws Exception {
        // given
        OrderItem orderItem = mock(OrderItem.class);
        Review review = Review.builder()
                .reviewId(1L)
                .orderItem(orderItem)
                .score(1)
                .content("bad")
                .image(null)
                .build();
        doReturn(review)
                .when(reviewService).updateReview(1L, 1, "bad", null);

        // when
        UpdateReviewRequest updateReviewRequest = UpdateReviewRequest.builder()
                .score(1)
                .content("bad")
                .image(null)
                .build();
        ResultActions resultActions = mockMvc.perform(patch(BASE_URL + "/{reviewId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReviewRequest)))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.score").value(review.getScore()))
                .andExpect(jsonPath("$.result.content").value(review.getContent()))
                .andExpect(jsonPath("$.result.image").value(review.getImage()));
        verify(reviewService).updateReview(1L, updateReviewRequest.getScore(), updateReviewRequest.getContent(), updateReviewRequest.getImage());
    }

    @Test
    void deleteReview() throws Exception {
        // given
        doNothing()
                .when(reviewService).deleteReview(1L);
        // when
        ResultActions resultActions = mockMvc.perform(delete(BASE_URL + "/{reviewId}", 1L)).andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").doesNotExist());
    }
}
