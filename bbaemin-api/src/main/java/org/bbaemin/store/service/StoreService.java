package org.bbaemin.store.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.domain.Category;
import org.bbaemin.category.repository.CategoryRepository;
import org.bbaemin.category.service.CategoryService;
import org.bbaemin.store.domain.StoreDto;
import org.bbaemin.store.domain.Store;
import org.bbaemin.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryService categoryService;


    public Category getCategory(Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    /**
     * <pre>
     * 1. MethodName : listStore
     * 2. ClassName  : StoreService.java
     * 3. Comment    : 매장 리스트 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 14.
     * </pre>
     */
    @Transactional(readOnly = true)
    public List<Store> listStore() {
        return storeRepository.findAll();
    }

    /**
     * <pre>
     * 1. MethodName : getStore
     * 2. ClassName  : StoreService.java
     * 3. Comment    : 매장 상세 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 14.
     * </pre>
     */
    @Transactional(readOnly = true)
    public Store getStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new NoSuchElementException("storeId : " + storeId));
    }

    /**
     * <pre>
     * 1. MethodName : createStore
     * 2. ClassName  : StoreService.java
     * 3. Comment    : 매장 등록
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 14.
     * </pre>
     */
    @Transactional
    public Store createStore(Store store) {
        Category oneCategory = getCategory(store.getStoreCategory().getCategoryId());
        // 매장 카테고리 연관관계 설정
        store.setStoreCategory(oneCategory);
        oneCategory.getStoreList().add(store);
        return storeRepository.save(store);
    }

    /**
     * <pre>
     * 1. MethodName : updateStore
     * 2. ClassName  : StoreService.java
     * 3. Comment    : 매장 수정
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 14.
     * </pre>
     */
    @Transactional
    public Store updateStore(Long storeId, Store store) {
        Store oneStore = getStore(storeId);
        // 기존 매장 카테고리와 다를 시 새로운 연관관계 설정
        if (!Objects.equals(oneStore.getStoreCategory().getCategoryId(), store.getStoreCategory().getCategoryId())) {
            Category oneCategory = getCategory(store.getStoreCategory().getCategoryId());
            store.setStoreCategory(oneCategory);
            oneCategory.getStoreList().add(store);
        }
        oneStore.setName(store.getName());
        oneStore.setDescription(store.getDescription());
        oneStore.setOwner(store.getOwner());
        oneStore.setAddress(store.getAddress());
        oneStore.setZipCode(store.getZipCode());
        oneStore.setPhoneNumber(store.getPhoneNumber());
        return oneStore;
    }

    /**
     * <pre>
     * 1. MethodName : deleteStore
     * 2. ClassName  : StoreService.java
     * 3. Comment    : 매장 삭제
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 14.
     * </pre>
     */
    @Transactional
    public Long deleteStore(Long storeId) {
        storeRepository.deleteById(storeId);
        return storeId;
    }
}
