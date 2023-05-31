package org.bbaemin.admin.item.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bbaemin.admin.category.vo.Category;
import org.bbaemin.admin.delivery.vo.Store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    @Lob
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    @JoinColumn(name = "store_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Store itemStore;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category itemCategory;

    @Builder
    private Item(Long itemId, String name, String description, int price, int quantity, Store itemStore, Category itemCategory) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        if (!Objects.isNull(itemStore)) {
            this.itemStore = itemStore;
            // 아이템-매장 연관관계 설정
            itemStore.addItem(this);
        }
        if (!Objects.isNull(itemCategory)) {
            this.itemCategory = itemCategory;
            // 아이템-카테고리 연관관계 설정
            itemCategory.addItem(this);
        }
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setItemStore(Store itemStore) {
        this.itemStore = itemStore;
        itemStore.addItem(this);
    }

    public void setItemCategory(Category itemCategory) {
        this.itemCategory = itemCategory;
        itemCategory.addItem(this);
    }
}
