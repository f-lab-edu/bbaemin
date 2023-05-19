package org.bbaemin.user.order.vo;

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
@Table("order_item")
public class OrderItem {

    @Id
    @Column("order_item_id")
    private Long orderItemId;

    @Column("order_id")
    private Long orderId;

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

    @Builder
    private OrderItem(Long orderItemId, Long orderId, Long itemId, String itemName, String itemDescription, int orderPrice, int orderCount) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
