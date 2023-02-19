package org.bbaemin.cart.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    // TODO - Item과 연관관계
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_description", nullable = false)
    private String itemDescription;

    @Column(name = "order_price", nullable = false)
    private int orderPrice;

    @Column(name = "order_count", nullable = false)
    private int orderCount;

    // TODO - User와 연관관계
    @Column(name = "user_id", nullable = false)
    private Long userId;    // orderer

    @Builder
    private CartItem(Long cartItemId, Long itemId, String itemName, String itemDescription, int orderPrice, int orderCount, Long userId) {
        this.cartItemId = cartItemId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
        this.userId = userId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}
