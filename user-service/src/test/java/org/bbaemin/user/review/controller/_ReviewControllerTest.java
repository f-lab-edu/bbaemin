package org.bbaemin.user.review.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(controllers = ReviewController.class,
        properties = {"spring.config.location=classpath:application-test.yml"})
class _ReviewControllerTest {
/*
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
        Order order = mock(Order.class);
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
        ResultActions resultActions = mockMvc.perform(post(BASE_URL + "/orders/{orderId}/orderItems/{orderItemId}",
                        order.getOrderId(), orderItem.getOrderItemId())
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
    }*/
}
