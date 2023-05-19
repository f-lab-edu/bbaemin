package org.bbaemin.user.cart.vo;

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
@Table("cart_item")
public class CartItem {

    @Id
    @Column("cart_item_id")
    private Long cartItemId;

    @Column("item_id")
    private Long itemId;

    @Column("item_name")
    private String itemName;

    @Column("item_description")
    private String itemDescription;

    @Column("order_price")
    private int orderPrice;

    @Column("order_count")
    private int orderCount;

    @Column("user_id")
    private Long userId;

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

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}
