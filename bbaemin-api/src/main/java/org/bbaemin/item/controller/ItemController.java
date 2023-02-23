package org.bbaemin.item.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.item.controller.response.ItemResponse;
import org.bbaemin.item.domain.Item;
import org.bbaemin.item.service.ItemService;
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
    public ApiResult<ItemResponse> createItem(@Validated @RequestBody Item item) {
        Item saveItem = itemService.createItem(item);
        return ApiResult.ok(new ItemResponse(saveItem));
    }

    @PutMapping("/{itemId}")
    public ApiResult<ItemResponse> updateItem(@PathVariable Long itemId, @Validated @RequestBody Item item) {
        Item updateItem = itemService.updateItem(itemId, item);
        return ApiResult.ok(new ItemResponse(updateItem));
    }

    @DeleteMapping("/{itemId}")
    public ApiResult<Long> deleteItem(@PathVariable Long itemId) {
        return ApiResult.ok(itemService.deleteItem(itemId));
    }
}
