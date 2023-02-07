package org.bbaemin.item.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.item.controller.request.CreateItemRequest;
import org.bbaemin.item.controller.request.UpdateItemRequest;
import org.bbaemin.item.controller.response.ItemResponse;
import org.bbaemin.item.service.ItemService;
import org.springframework.web.bind.annotation.*;

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
        return itemService.createItem(createItemRequest);
    }

    @PutMapping("/{itemId}")
    public ItemResponse updateItem(@PathVariable Long itemId, @RequestBody UpdateItemRequest updateItemRequest) {
        return itemService.updateItem(itemId, updateItemRequest);
    }

    @DeleteMapping("/{itemId}")
    public Long deleteItem(@PathVariable Long itemId) {
        return itemService.deleteItem(itemId);
    }
}
