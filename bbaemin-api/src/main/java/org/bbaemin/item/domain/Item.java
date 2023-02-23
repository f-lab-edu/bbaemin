package org.bbaemin.item.domain;

import lombok.*;
import org.bbaemin.category.domain.CategoryEntity;
import org.bbaemin.store.domain.StoreEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
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
    private StoreEntity itemStore;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity itemCategory;

    public static ItemDto toDto(Item entity) {
        return ItemDto.builder()
                .name(entity.name)
                .description(entity.description)
                .price(entity.price)
                .quantity(entity.quantity)
                .categoryName(entity.itemCategory.getName())
                .categoryDescription(entity.itemCategory.getDescription())
                .storeName(entity.itemStore.getName())
                .storeDescription(entity.itemStore.getDescription())
                .storeOwner(entity.itemStore.getOwner())
                .address(entity.itemStore.getAddress())
                .zipCode(entity.itemStore.getZipCode())
                .phoneNumber(entity.itemStore.getPhoneNumber())
                .build();
    }
}
