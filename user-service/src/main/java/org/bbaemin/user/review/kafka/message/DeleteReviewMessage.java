package org.bbaemin.user.review.kafka.message;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeleteReviewMessage {

    private Long reviewId;

    public DeleteReviewMessage(Long reviewId) {
        this.reviewId = reviewId;
    }
}
