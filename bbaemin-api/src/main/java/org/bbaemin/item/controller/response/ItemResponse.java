package org.bbaemin.item.controller.response;

import lombok.Getter;
import org.bbaemin.item.vo.Item;

import java.util.List;

@Getter
public class ItemResponse {

    private Long itemId;

    private String category;

    private String store;

    private String name;
    private String description;
    private int price;
    private int quantity;

    private List<ItemImageResponse> itemImageResponse;

    public ItemResponse(Item item) {
        this.category = item.getItemCategory().getName();
        this.store = item.getItemStore().getName();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
    }
}
