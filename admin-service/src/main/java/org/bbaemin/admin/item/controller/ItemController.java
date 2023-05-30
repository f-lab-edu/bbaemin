package org.bbaemin.admin.item.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.admin.category.service.CategoryService;
import org.bbaemin.admin.category.vo.Category;
import org.bbaemin.admin.delivery.service.StoreService;
import org.bbaemin.admin.delivery.vo.Store;
import org.bbaemin.admin.item.controller.request.CreateItemRequest;
import org.bbaemin.admin.item.controller.request.UpdateItemRequest;
import org.bbaemin.admin.item.service.ItemService;
import org.bbaemin.admin.item.vo.Item;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.dto.request.DeductItemRequest;
import org.bbaemin.dto.request.RestoreItemRequest;
import org.bbaemin.dto.response.ItemResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final CategoryService categoryService;
    private final StoreService storeService;

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

        Category category = categoryService.getCategory(createItemRequest.getCategoryId());
        Store store = storeService.getStore(createItemRequest.getStoreId());
        Item item = Item.builder()
                .itemCategory(category)
                .itemStore(store)
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

    @PatchMapping("/{itemId}")
    public ApiResult<ItemResponse> updateItem(@PathVariable Long itemId, @Validated @RequestBody UpdateItemRequest updateItemRequest) {

        Item updated = itemService.updateItem(itemId,
                updateItemRequest.getName(),
                updateItemRequest.getDescription(),
                updateItemRequest.getPrice(),
                updateItemRequest.getQuantity(),
                updateItemRequest.getCategoryId(),
                updateItemRequest.getStoreId());

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

    @PatchMapping("/{itemId}/deduct")
    public ApiResult<ItemResponse> deductItem(@PathVariable Long itemId, @Validated @RequestBody DeductItemRequest deductItemRequest) {
        Item updated = itemService.deductItem(itemId, deductItemRequest.getOrderCount());
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

    @PatchMapping("/{itemId}/restore")
    public ApiResult<ItemResponse> restoreItem(@PathVariable Long itemId, @Validated @RequestBody RestoreItemRequest restoreItemRequest) {
        Item updated = itemService.restoreItem(itemId, restoreItemRequest.getOrderCount());
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
}
