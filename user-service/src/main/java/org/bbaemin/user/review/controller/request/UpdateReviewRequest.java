package org.bbaemin.user.review.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateReviewRequest {

    private int score;
    private String content;
    private String image;

    @Builder
    private UpdateReviewRequest(int score, String content, String image) {
        this.score = score;
        this.content = content;
        this.image = image;
    }
}
