package org.bbaemin.item.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.item.controller.request.CreateItemRequest;
import org.bbaemin.item.controller.request.UpdateItemRequest;
import org.bbaemin.item.controller.response.ItemResponse;
import org.bbaemin.item.service.ItemService;
import org.bbaemin.item.vo.Item;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemResponse> listItem() {
        return itemService.listItem();
    }

    @GetMapping("/{itemId}")
    public ItemResponse getItem(@PathVariable Long itemId) {
        return itemService.getItem(itemId);
    }

    @PostMapping
    public ItemResponse createItem(@RequestBody CreateItemRequest createItemRequest) {
        Item item = Item.builder()
                .categoryId(createItemRequest.getCategoryId())
                .storeId(createItemRequest.getStoreId())
                .price(createItemRequest.getPrice())
                .quantity(createItemRequest.getQuantity())
                .itemImageRequest(
                        Arrays.asList(
                                Item.ItemImageRequest.builder()
                                        .itemId(createItemRequest.getItemImageRequest().get(0).getItemId())
                                        .url(createItemRequest.getItemImageRequest().get(0).getUrl())
                                        .type(createItemRequest.getItemImageRequest().get(0).getType())
                                        .build(),
                                Item.ItemImageRequest.builder()
                                        .itemId(createItemRequest.getItemImageRequest().get(1).getItemId())
                                        .url(createItemRequest.getItemImageRequest().get(1).getUrl())
                                        .type(createItemRequest.getItemImageRequest().get(1).getType())
                                        .build()
                        ))
                .build();

        return itemService.createItem(item);
    }

    @PutMapping("/{itemId}")
    public ItemResponse updateItem(@PathVariable Long itemId, @RequestBody UpdateItemRequest updateItemRequest) {
        Item item = Item.builder()
                .categoryId(updateItemRequest.getCategoryId())
                .storeId(updateItemRequest.getStoreId())
                .price(updateItemRequest.getPrice())
                .quantity(updateItemRequest.getQuantity())
                .itemImageRequest(
                        Arrays.asList(
                                Item.ItemImageRequest.builder()
                                        .itemId(updateItemRequest.getItemImageRequest().get(0).getItemId())
                                        .url(updateItemRequest.getItemImageRequest().get(0).getUrl())
                                        .type(updateItemRequest.getItemImageRequest().get(0).getType())
                                        .build(),
                                Item.ItemImageRequest.builder()
                                        .itemId(updateItemRequest.getItemImageRequest().get(1).getItemId())
                                        .url(updateItemRequest.getItemImageRequest().get(1).getUrl())
                                        .type(updateItemRequest.getItemImageRequest().get(1).getType())
                                        .build()
                        ))
                .build();

        return itemService.updateItem(itemId, item);
    }

    @DeleteMapping("/{itemId}")
    public Long deleteItem(@PathVariable Long itemId) {
        return itemService.deleteItem(itemId);
    }
}
