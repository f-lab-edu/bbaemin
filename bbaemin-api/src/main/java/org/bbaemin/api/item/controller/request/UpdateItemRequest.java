package org.bbaemin.api.item.controller.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
public class UpdateItemRequest {

    @NotBlank(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    @NotBlank(message = "매장 ID는 필수입니다.")
    private Long storeId;

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @NotBlank(message = "상품 상세내용은 필수입니다.")
    private String description;

    @NotBlank(message = "상품가격은 필수입니다.")
    private int price;

    @NotBlank(message = "상품 재고는 필수입니다.")
    private int quantity;

    private List<ItemImageRequest> itemImageRequest;

    @Getter @Builder
    public static class ItemImageRequest {
        private Long itemId;
        private String url;
        private String type;
    }
}
