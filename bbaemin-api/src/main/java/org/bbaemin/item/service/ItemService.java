package org.bbaemin.item.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.domain.CategoryEntity;
import org.bbaemin.category.repository.CategoryRepository;
import org.bbaemin.item.domain.ItemDTO;
import org.bbaemin.item.domain.ItemEntity;
import org.bbaemin.item.repository.ItemRepository;
import org.bbaemin.store.domain.StoreEntity;
import org.bbaemin.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;

    private CategoryEntity oneCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }

    private StoreEntity oneStore(Long storeId) {
        return storeRepository.findByStoreId(storeId).orElseThrow();
    }

    private ItemEntity oneItem(Long itemId) {
        return itemRepository.findByItemId(itemId).orElseThrow();
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
    public List<ItemDTO> listItem() {
        return itemRepository.findAll()
                .stream().map(ItemEntity::toDto)
                .collect(Collectors.toList());
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
    public ItemDTO getItem(Long itemId) {
        return ItemEntity.toDto(oneItem(itemId));
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
    public ItemDTO createItem(ItemEntity itemEntity) {
        // 아이템 매장 연관관계 설정
        StoreEntity oneStore = oneStore(itemEntity.getItemStore().getStoreId());
        itemEntity.setItemStore(oneStore);
        oneStore.getItemList().add(itemEntity);
        // 아이템 카테고리 연관관계 설정
        CategoryEntity oneCategory = oneCategory(itemEntity.getItemCategory().getCategoryId());
        itemEntity.setItemCategory(oneCategory);
        oneCategory.getItemList().add(itemEntity);
        return ItemEntity.toDto(itemRepository.save(itemEntity));
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
    public ItemDTO updateItem(Long itemId, ItemEntity itemEntity) {
        ItemEntity oneItem = oneItem(itemId);
        // 카테고리 수정
        if (!Objects.equals(oneItem.getItemCategory().getCategoryId(), itemEntity.getItemCategory().getCategoryId())) {
            CategoryEntity oneCategory = oneCategory(itemEntity.getItemCategory().getCategoryId());
            itemEntity.setItemCategory(oneCategory);
            oneCategory.getItemList().add(itemEntity);
        }

        // 매장 수정
        if (!Objects.equals(oneItem.getItemStore().getStoreId(), itemEntity.getItemStore().getStoreId())) {
            StoreEntity oneStore = oneStore(itemEntity.getItemStore().getStoreId());
            itemEntity.setItemStore(oneStore);
            oneStore.getItemList().add(itemEntity);
        }

        oneItem.setName(itemEntity.getName());
        oneItem.setDescription(itemEntity.getDescription());
        oneItem.setPrice(itemEntity.getPrice());
        oneItem.setQuantity(itemEntity.getQuantity());
        return ItemEntity.toDto(itemEntity);
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
