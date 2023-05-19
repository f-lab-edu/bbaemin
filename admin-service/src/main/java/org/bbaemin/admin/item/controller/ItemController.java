package org.bbaemin.admin.item.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.admin.item.controller.request.CreateItemRequest;
import org.bbaemin.admin.item.controller.request.UpdateItemRequest;
import org.bbaemin.admin.item.service.ItemService;
import org.bbaemin.admin.item.vo.Item;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.dto.response.ItemResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ApiResult<List<ItemResponse>> listItem() {
        return ApiResult.ok(itemService.listItem().stream()
                .map(item -> ItemResponse.builder()
                        .itemId(item.getItemId())
                        .categoryName(item.getItemCategory().getName())
                        .storeName(item.getItemStore().getName())
                        .name(item.getName())
                        .description(item.getDescription())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList()));
    }

    @GetMapping("/{itemId}")
    public ApiResult<ItemResponse> getItem(@PathVariable Long itemId) {
        Item item = itemService.getItem(itemId);
        ItemResponse response = ItemResponse.builder()
                .itemId(item.getItemId())
                .categoryName(item.getItemCategory().getName())
                .storeName(item.getItemStore().getName())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();
        return ApiResult.ok(response);
    }

    @PostMapping
    public ApiResult<ItemResponse> createItem(@Validated @RequestBody CreateItemRequest createItemRequest) {
        Item item = Item.builder()
                .itemCategory(itemService.getCategory(createItemRequest.getCategoryId()))
                .itemStore(itemService.getStore(createItemRequest.getStoreId()))
                .name(createItemRequest.getName())
                .description(createItemRequest.getDescription())
                .price(createItemRequest.getPrice())
                .quantity(createItemRequest.getQuantity())
                .build();
        Item saved = itemService.createItem(item);
        ItemResponse response = ItemResponse.builder()
                .itemId(saved.getItemId())
                .categoryName(saved.getItemCategory().getName())
                .storeName(saved.getItemStore().getName())
                .name(saved.getName())
                .description(saved.getDescription())
                .price(saved.getPrice())
                .quantity(saved.getQuantity())
                .build();
        return ApiResult.created(response);
    }

    @PutMapping("/{itemId}")
    public ApiResult<ItemResponse> updateItem(@PathVariable Long itemId, @Validated @RequestBody UpdateItemRequest updateItemRequest) {
        Item item = Item.builder()
                .itemCategory(itemService.getCategory(updateItemRequest.getCategoryId()))
                .itemStore(itemService.getStore(updateItemRequest.getStoreId()))
                .name(updateItemRequest.getName())
                .description(updateItemRequest.getDescription())
                .price(updateItemRequest.getPrice())
                .quantity(updateItemRequest.getQuantity())
                .build();
        Item updated = itemService.updateItem(itemId, item);
        ItemResponse response = ItemResponse.builder()
                .itemId(updated.getItemId())
                .categoryName(updated.getItemCategory().getName())
                .storeName(updated.getItemStore().getName())
                .name(updated.getName())
                .description(updated.getDescription())
                .price(updated.getPrice())
                .quantity(updated.getQuantity())
                .build();
        return ApiResult.ok(response);
    }

    @DeleteMapping("/{itemId}")
    public ApiResult<Long> deleteItem(@PathVariable Long itemId) {
        return ApiResult.ok(itemService.deleteItem(itemId));
    }
}
