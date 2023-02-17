package org.bbaemin.review.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Review {

    private Long reviewId;
    private Long orderId;

    private int score;
    private String content;
    private String image;

    @Builder
    private Review(Long reviewId, Long orderId, int score, String content, String image) {
        this.reviewId = reviewId;
        this.orderId = orderId;
        this.score = score;
        this.content = content;
        this.image = image;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
