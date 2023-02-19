package org.bbaemin.order.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id",
            nullable = false, foreignKey = @ForeignKey(name = "fk_orderItem_order"))
    private Order order;

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

    @Builder
    private OrderItem(Long orderItemId, Order order, Long itemId, String itemName, String itemDescription, int orderPrice, int orderCount) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
