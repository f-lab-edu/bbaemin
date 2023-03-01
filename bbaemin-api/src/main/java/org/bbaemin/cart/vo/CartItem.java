package org.bbaemin.cart.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bbaemin.item.vo.Item;
import org.bbaemin.user.vo.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id",
            referencedColumnName = "item_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_cartItem_item"))
    private Item item;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_description", nullable = false)
    private String itemDescription;

    @Column(name = "order_price", nullable = false)
    private int orderPrice;

    @Column(name = "order_count", nullable = false)
    private int orderCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            referencedColumnName = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_cartItem_user"))
    private User user;    // orderer

    @Builder
    private CartItem(Long cartItemId, Item item, String itemName, String itemDescription, int orderPrice, int orderCount, User user) {
        this.cartItemId = cartItemId;
        this.item = item;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
        this.user = user;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}
