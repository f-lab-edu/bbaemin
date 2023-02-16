package org.bbaemin.item.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ItemResponse {

    private Long itemId;

    private String category;

    private String store;

    private String name;
    private String description;
    private int price;
    private int quantity;

    private List<ItemImageResponse> itemImageResponse;
}
