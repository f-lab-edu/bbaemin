package org.bbaemin.api.review.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bbaemin.api.order.vo.OrderItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", referencedColumnName = "order_item_id",
            nullable = false, foreignKey = @ForeignKey(name = "fk_review_orderItem"))
    private OrderItem orderItem;

    @Column(nullable = false)
    private int score;

    @Lob
    private String content;

    private String image;

    @Builder
    private Review(Long reviewId, OrderItem orderItem, int score, String content, String image) {
        this.reviewId = reviewId;
        this.orderItem = orderItem;
        this.score = score;
        this.content = content;
        this.image = image;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
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
