package org.bbaemin.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class ItemResponse {

    private Long itemId;
    private String categoryName;
    private String storeName;
    private String name;
    private String description;
    private int price;
    private int quantity;

    private List<ItemImageResponse> itemImageList;

    @Builder
    private ItemResponse(Long itemId, String categoryName, String storeName, String name, String description, int price, int quantity, List<ItemImageResponse> itemImageList) {
        this.itemId = itemId;
        this.categoryName = categoryName;
        this.storeName = storeName;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.itemImageList = itemImageList;
    }
}
