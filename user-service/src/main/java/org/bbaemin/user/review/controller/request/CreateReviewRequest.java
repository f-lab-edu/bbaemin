package org.bbaemin.user.review.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateReviewRequest {

    private Long orderItemId;

    private int score;
    private String content;
    private String image;

    @Builder
    private CreateReviewRequest(Long orderItemId, int score, String content, String image) {
        this.orderItemId = orderItemId;
        this.score = score;
        this.content = content;
        this.image = image;
    }
}
