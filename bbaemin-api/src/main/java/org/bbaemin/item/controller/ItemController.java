package org.bbaemin.item.controller;

import org.bbaemin.item.controller.request.CreateItemRequest;
import org.bbaemin.item.controller.response.ItemImageResponse;
import org.bbaemin.item.controller.response.ItemResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    @GetMapping
    public List<ItemResponse> listItem() {
        return List.of(ItemResponse.builder()
                .category("유제품")
                .store("B마트 인천점")
                .name("서울우유 저지방우유 1000ml")
                .description("서울 우유 저지방우유 1000ml")
                .price(5000)
                .quantity(999)
                .itemImageResponse(
                        Arrays.asList(
                                ItemImageResponse.builder()
                                        .url("https://image.thumbnail.com")
                                        .type("thumbnail")
                                        .build(),
                                ItemImageResponse.builder()
                                        .url("https://image.detail.com")
                                        .type("detail")
                                        .build()
                        )
                )
                .build());
    }

    @GetMapping("/{itemId}")
    public ItemResponse getItem(@PathVariable Long itemId) {
        return ItemResponse.builder()
                .category("유제품")
                .store("B마트 인천점")
                .name("서울우유 저지방우유 1000ml")
                .description("서울 우유 저지방우유 1000ml")
                .price(5000)
                .quantity(999)
                .itemImageResponse(
                        Arrays.asList(
                                ItemImageResponse.builder()
                                        .url("https://image.thumbnail.com")
                                        .type("thumbnail")
                                        .build(),
                                ItemImageResponse.builder()
                                        .url("https://image.detail.com")
                                        .type("detail")
                                        .build()
                        )
                )
                .build();
    }

    @PostMapping
    public ItemResponse createItem(@RequestBody CreateItemRequest createItemRequest) {
        return ItemResponse.builder()
                .category("과일")
                .store("B마트 인천점")
                .name("청동사과")
                .description("맛있고 싱싱한 청동사과")
                .price(2000)
                .quantity(999)
                .itemImageResponse(
                        Arrays.asList(
                                ItemImageResponse.builder()
                                        .url("https://image.thumbnail.com")
                                        .type("thumb-nail")
                                        .build(),
                                ItemImageResponse.builder()
                                        .url("https://image.detail.com")
                                        .type("detail")
                                        .build()
                        )
                )
                .build();
    }

    @PutMapping("/{itemId}")
    public ItemResponse updateItem(@PathVariable Long itemId) {
        return ItemResponse.builder()
                .category("채소")
                .store("B마트 인천점")
                .name("브로콜리")
                .description("맛있고 싱싱한 브로콜리")
                .price(1000)
                .quantity(999)
                .itemImageResponse(
                        Arrays.asList(
                                ItemImageResponse.builder()
                                        .url("https://image.thumbnail.com")
                                        .type("thumb-nail")
                                        .build(),
                                ItemImageResponse.builder()
                                        .url("https://image.detail.com")
                                        .type("detail")
                                        .build()
                        )
                )
                .build();
    }

    @DeleteMapping("/{itemId}")
    public Long deleteItem(@PathVariable Long itemId) {
        return itemId;
    }
}
