package org.bbaemin.item.controller;

import lombok.RequiredArgsConstructor;
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
    public List<ItemDTO> listItem() {
        return itemService.listItem();
    }

    @GetMapping("/{itemId}")
    public ItemDTO getItem(@PathVariable Long itemId) {
        return itemService.getItem(itemId);
    }

    @PostMapping
    public ItemDTO createItem(@Valid @RequestBody ItemEntity itemEntity) {
        return itemService.createItem(itemEntity);
    }

    @PutMapping("/{itemId}")
    public ItemDTO updateItem(@PathVariable Long itemId, @Valid @RequestBody ItemEntity itemEntity) {
        return itemService.updateItem(itemId, itemEntity);
    }

    @DeleteMapping("/{itemId}")
    public Long deleteItem(@PathVariable Long itemId) {
        return itemService.deleteItem(itemId);
    }
}
