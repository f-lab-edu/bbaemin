package org.bbaemin.user.review.controller;

import org.bbaemin.config.response.ApiResult;
import org.bbaemin.user.review.controller.request.CreateReviewRequest;
import org.bbaemin.user.review.controller.request.UpdateReviewRequest;
import org.bbaemin.user.review.kafka.ReviewEventProducer;
import org.bbaemin.user.review.kafka.message.CreateReviewMessage;
import org.bbaemin.user.review.kafka.message.DeleteReviewMessage;
import org.bbaemin.user.review.kafka.message.UpdateReviewMessage;
import org.bbaemin.user.review.repository.ReviewRepository;
import org.bbaemin.user.review.service.ReviewService;
import org.bbaemin.user.review.vo.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@Import({ReviewEventProducer.class, ReviewService.class})
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient(timeout = "10000")
@WebFluxTest(ReviewController.class)
class ReviewControllerTest {

    private final String BASE_URL = "/api/v1/reviews";

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ReviewRepository reviewRepository;
    @MockBean
    KafkaTemplate<String, CreateReviewMessage> createReviewMessageKafkaTemplate;
    @MockBean
    KafkaTemplate<String, UpdateReviewMessage> updateReviewMessageKafkaTemplate;
    @MockBean
    KafkaTemplate<String, DeleteReviewMessage> deleteReviewMessageKafkaTemplate;

    @Test
    void createReview() {
        // given
        // when
        // then
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .orderItemId(1L)
                .score(5)
                .content("good")
                .image(null)
                .build();
        webTestClient.post()
                .uri(BASE_URL + "/orders/{orderId}/orderItems/{orderItemId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createReviewRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.CREATED.name())
                .jsonPath("$.result.score").isEqualTo(createReviewRequest.getScore())
                .jsonPath("$.result.content").isEqualTo(createReviewRequest.getContent())
                .jsonPath("$.result.image").isEqualTo(createReviewRequest.getImage());

        verify(createReviewMessageKafkaTemplate).send(anyString(), any(CreateReviewMessage.class));
    }

    @Test
    void updateReview() {
        // given
        Review review = Review.builder()
                .reviewId(1L)
                .orderItemId(1L)
                .score(1)
                .content("bad")
                .image(null)
                .build();
        doReturn(Mono.just(review)).when(reviewRepository).findById(1L);

        // when
        // then
        UpdateReviewRequest updateReviewRequest = UpdateReviewRequest.builder()
                .score(1)
                .content("bad")
                .image(null)
                .build();
        webTestClient.patch()
                .uri(BASE_URL + "/{reviewId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateReviewRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.SUCCESS.name())
                .jsonPath("$.result.score").isEqualTo(updateReviewRequest.getScore())
                .jsonPath("$.result.content").isEqualTo(updateReviewRequest.getContent())
                .jsonPath("$.result.image").isEqualTo(updateReviewRequest.getImage());

        verify(updateReviewMessageKafkaTemplate).send(anyString(), any(UpdateReviewMessage.class));
    }

    @Test
    void deleteReview() {
        // given
        // when
        // then
        webTestClient.delete()
                .uri(BASE_URL + "/{reviewId}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.SUCCESS.name())
                .jsonPath("$.result").doesNotExist();

        verify(deleteReviewMessageKafkaTemplate).send(anyString(), any(DeleteReviewMessage.class));
    }
}
