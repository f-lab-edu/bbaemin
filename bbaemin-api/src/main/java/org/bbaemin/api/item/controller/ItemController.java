package org.bbaemin.api.item.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.api.item.vo.Item;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.api.item.controller.request.CreateItemRequest;
import org.bbaemin.api.item.controller.request.UpdateItemRequest;
import org.bbaemin.api.item.controller.response.ItemResponse;
import org.bbaemin.api.item.service.ItemService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
                .map(ItemResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/{itemId}")
    public ApiResult<ItemResponse> getItem(@PathVariable Long itemId) {
        Item getItem = itemService.getItem(itemId);
        return ApiResult.ok(new ItemResponse(getItem));
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

        Item saveItem = itemService.createItem(item);
        return ApiResult.created(new ItemResponse(saveItem));
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

        Item updateItem = itemService.updateItem(itemId, item);
        return ApiResult.ok(new ItemResponse(updateItem));
    }

    @DeleteMapping("/{itemId}")
    public ApiResult<Long> deleteItem(@PathVariable Long itemId) {
        return ApiResult.ok(itemService.deleteItem(itemId));
    }
}
