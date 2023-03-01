package org.bbaemin.api.review.controller.response;

import lombok.Getter;
import org.bbaemin.api.review.vo.Review;

@Getter
public class ReviewResponse {

    private int score;
    private String content;
    private String image;


    public ReviewResponse(Review review) {
        this.score = review.getScore();
        this.content = review.getContent();
        this.image = review.getImage();
    }
}
