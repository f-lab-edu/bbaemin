package org.bbaemin.item.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateItemRequest {
    private Long categoryId;

    private Long storeId;

    private String name;
    private String description;
    private int price;
    private int quantity;
}
