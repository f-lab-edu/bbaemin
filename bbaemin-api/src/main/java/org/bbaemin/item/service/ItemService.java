package org.bbaemin.item.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.item.controller.request.CreateItemRequest;
import org.bbaemin.item.controller.request.UpdateItemRequest;
import org.bbaemin.item.controller.response.ItemResponse;
import org.bbaemin.item.repository.ItemRepository;
import org.bbaemin.item.vo.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * <pre>
     * 1. MethodName : listItem
     * 2. ClassName  : ItemService.java
     * 3. Comment    : 아이템 리스트 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    public List<ItemResponse> listItem() {
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
    public ItemResponse getItem(Long itemId) {
        return itemRepository.findById(itemId);
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
    public ItemResponse createItem(Item item) {
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
    public ItemResponse updateItem(Long itemId, Item item) {
        return itemRepository.update(itemId, item);
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
    public Long deleteItem(Long itemId) {
        return itemRepository.deleteById(itemId);
    }
}
