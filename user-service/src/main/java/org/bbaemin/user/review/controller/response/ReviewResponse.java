package org.bbaemin.user.review.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewResponse {

    private int score;
    private String content;
    private String image;

    @Builder
    private ReviewResponse(int score, String content, String image) {
        this.score = score;
        this.content = content;
        this.image = image;
    }
}
