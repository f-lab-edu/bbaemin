package org.bbaemin.item.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Item {

    private Long itemId;
    private Long categoryId;
    private Long storeId;

    private String name;
    private String description;
    private int price;
    private int quantity;

    private List<ItemImageRequest> itemImageRequest;

    @Getter @Builder
    public static class ItemImageRequest {
        private Long itemId;
        private String url;
        private String type;
    }
}
