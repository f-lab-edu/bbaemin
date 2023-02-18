package org.bbaemin.item.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.item.domain.ItemDTO;
import org.bbaemin.item.domain.ItemEntity;
import org.bbaemin.item.service.ItemService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ApiResult<List<ItemDTO>> listItem() {
        return ApiResult.ok(itemService.listItem());
    }

    @GetMapping("/{itemId}")
    public ApiResult<ItemDTO> getItem(@PathVariable Long itemId) {
        return ApiResult.ok(itemService.getItem(itemId));
    }

    @PostMapping
    public ApiResult<ItemDTO> createItem(@Valid @RequestBody ItemEntity itemEntity) {
        return ApiResult.ok(itemService.createItem(itemEntity));
    }

    @PutMapping("/{itemId}")
    public ApiResult<ItemDTO> updateItem(@PathVariable Long itemId, @Valid @RequestBody ItemEntity itemEntity) {
        return ApiResult.ok(itemService.updateItem(itemId, itemEntity));
    }

    @DeleteMapping("/{itemId}")
    public ApiResult<Long> deleteItem(@PathVariable Long itemId) {
        return ApiResult.ok(itemService.deleteItem(itemId));
    }
}
