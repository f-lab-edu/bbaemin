package org.bbaemin.user.review.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("review")
public class Review {

    @Id
    @Column("review_id")
    private Long reviewId;

    @Column("order_item_id")
    private Long orderItemId;

    @Column("score")
    private int score;

    @Column("content")
    private String content;

    @Column("image")
    private String image;

    @Builder
    private Review(Long reviewId, Long orderItemId, int score, String content, String image) {
        this.reviewId = reviewId;
        this.orderItemId = orderItemId;
        this.score = score;
        this.content = content;
        this.image = image;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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
