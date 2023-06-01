package org.bbaemin.user.review.kafka.message;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateReviewMessage {

    private Long orderItemId;
    private int score;
    private String content;
    private String image;

    public CreateReviewMessage(Long orderItemId, int score, String content, String image) {
        this.orderItemId = orderItemId;
        this.score = score;
        this.content = content;
        this.image = image;
    }
}
