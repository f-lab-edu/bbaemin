package org.bbaemin.user.review.kafka.message;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateReviewMessage {

    private Long reviewId;
    private int score;
    private String content;
    private String image;

    public UpdateReviewMessage(Long reviewId, int score, String content, String image) {
        this.reviewId = reviewId;
        this.score = score;
        this.content = content;
        this.image = image;
    }
}
