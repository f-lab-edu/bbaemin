package org.bbaemin.admin.item.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.admin.category.service.CategoryService;
import org.bbaemin.admin.category.vo.Category;
import org.bbaemin.admin.delivery.service.StoreService;
import org.bbaemin.admin.delivery.vo.Store;
import org.bbaemin.admin.item.repository.ItemRepository;
import org.bbaemin.admin.item.vo.Item;
import org.bbaemin.exception.StockShortageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final CategoryService categoryService;
    private final StoreService storeService;


    @Transactional(readOnly = true)
    public List<Item> listItem() {
        return itemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Item getItem(Long itemId) {
        return itemRepository.findByItemId(itemId).orElseThrow(() -> new NoSuchElementException("itemId : " + itemId));
    }

    @Transactional
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long itemId, String name, String description, int price, int quantity, Long categoryId, Long storeId) {

        Item updated = getItem(itemId);
        updated.setName(name);
        updated.setDescription(description);
        updated.setPrice(price);
        updated.setQuantity(quantity);

        // 카테고리 수정
        if (!Objects.equals(updated.getItemCategory().getCategoryId(), categoryId)) {
            Category category = categoryService.getCategory(categoryId);
            updated.setItemCategory(category);
        }

        // 매장 수정
        if (!Objects.equals(updated.getItemStore().getStoreId(), storeId)) {
            Store store = storeService.getStore(storeId);
            updated.setItemStore(store);
        }

        return updated;
    }

    @Transactional
    public Item deductItem(Long itemId, int orderCount) {

        Item updated = getItem(itemId);

        int quantity = updated.getQuantity();
        if (quantity < orderCount) {
            throw new StockShortageException(String.format("itemId : {}, orderCount : {}", itemId, orderCount));
        }
        updated.setQuantity(quantity - orderCount);
        return updated;
    }

    @Transactional
    public Item restoreItem(Long itemId, int orderCount) {

        Item updated = getItem(itemId);

        int quantity = updated.getQuantity();
        updated.setQuantity(quantity + orderCount);
        return updated;
    }

    @Transactional
    public Long deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
        return itemId;
    }
}
