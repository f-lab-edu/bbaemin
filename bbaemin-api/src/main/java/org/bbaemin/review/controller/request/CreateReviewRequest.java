package org.bbaemin.review.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateReviewRequest {

    private Long orderId;

    private int score;
    private String content;
    private String image;

    @Builder
    private CreateReviewRequest(Long orderId, int score, String content, String image) {
        this.orderId = orderId;
        this.score = score;
        this.content = content;
        this.image = image;
    }
}
