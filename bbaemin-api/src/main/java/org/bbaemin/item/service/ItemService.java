package org.bbaemin.item.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.domain.Category;
import org.bbaemin.category.service.CategoryService;
import org.bbaemin.item.repository.ItemRepository;
import org.bbaemin.item.vo.Item;
import org.bbaemin.store.domain.Store;
import org.bbaemin.store.service.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final CategoryService categoryService;
    private final StoreService storeService;
    private final ItemRepository itemRepository;

    public Category getCategory(Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    public Store getStore(Long storeId) {
        return storeService.getStore(storeId);
    }


    /**
     * <pre>
     * 1. MethodName : listItem
     * 2. ClassName  : ItemService.java
     * 3. Comment    : 아이템 리스트 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    @Transactional(readOnly = true)
    public List<Item> listItem() {
        return itemRepository.findAll();
    }

    /**
     * <pre>
     * 1. MethodName : getItem
     * 2. ClassName  : ItemService.java
     * 3. Comment    : 아이템 상세 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    @Transactional(readOnly = true)
    public Item getItem(Long itemId) {
        return itemRepository.findByItemId(itemId).orElseThrow(() -> new NoSuchElementException("itemId : " + itemId));
    }

    /**
     * <pre>
     * 1. MethodName : createItem
     * 2. ClassName  : ItemService.java
     * 3. Comment    : 아이템 등록
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    @Transactional
    public Item createItem(Item item) {
        // 아이템 매장 연관관계 설정
        Store oneStore = getStore(item.getItemStore().getStoreId());
        item.setItemStore(oneStore);
        oneStore.getItemList().add(item);
        // 아이템 카테고리 연관관계 설정
        Category oneCategory = getCategory(item.getItemCategory().getCategoryId());
        item.setItemCategory(oneCategory);
        oneCategory.getItemList().add(item);
        return itemRepository.save(item);
    }

    /**
     * <pre>
     * 1. MethodName : updateItem
     * 2. ClassName  : ItemService.java
     * 3. Comment    : 아이템 수정
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    @Transactional
    public Item updateItem(Long itemId, Item item) {
        Item oneItem = getItem(itemId);
        // 카테고리 수정
        if (!Objects.equals(oneItem.getItemCategory().getCategoryId(), item.getItemCategory().getCategoryId())) {
            Category getCategory = getCategory(item.getItemCategory().getCategoryId());
            item.setItemCategory(getCategory);
            getCategory.getItemList().add(item);
        }

        // 매장 수정
        if (!Objects.equals(oneItem.getItemStore().getStoreId(), item.getItemStore().getStoreId())) {
            Store getStore = getStore(item.getItemStore().getStoreId());
            item.setItemStore(getStore);
            getStore.getItemList().add(item);
        }

        oneItem.setName(item.getName());
        oneItem.setDescription(item.getDescription());
        oneItem.setPrice(item.getPrice());
        oneItem.setQuantity(item.getQuantity());
        return item;
    }

    /**
     * <pre>
     * 1. MethodName : deleteItem
     * 2. ClassName  : ItemService.java
     * 3. Comment    : 아이템 삭제
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    @Transactional
    public Long deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
        return itemId;
    }
}
