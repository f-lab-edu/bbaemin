package org.bbaemin.item.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class CreateItemRequest {

    private Long categoryId;

    private Long storeId;

    private String name;
    private String description;
    private int price;
    private int quantity;

    private List<ItemImageRequest> itemImageRequest;

    @Getter @Setter @Builder
    public static class ItemImageRequest {
        private Long itemId;
        private String url;
        private String type;
    }
}
