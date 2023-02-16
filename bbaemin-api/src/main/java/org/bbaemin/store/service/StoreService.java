package org.bbaemin.store.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.domain.CategoryEntity;
import org.bbaemin.category.repository.CategoryRepository;
import org.bbaemin.store.domain.StoreDto;
import org.bbaemin.store.domain.StoreEntity;
import org.bbaemin.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    private StoreEntity oneStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow();
    }

    private CategoryEntity oneCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
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
    public List<StoreDto> listStore() {
        return storeRepository.findAll().stream()
                .map(StoreEntity::toDto)
                .collect(Collectors.toList());
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
    public StoreDto getStore(Long storeId) {
        return StoreEntity.toDto(storeRepository.findByStoreId(storeId)
                .orElseThrow());
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
    public StoreDto createStore(StoreEntity storeEntity) {
        CategoryEntity oneCategory = oneCategory(storeEntity.getStoreCategory().getCategoryId());
        // 매장 카테고리 연관관계 설정
        storeEntity.setStoreCategory(oneCategory);
        oneCategory.getStoreList().add(storeEntity);
        return StoreEntity.toDto(storeRepository.save(storeEntity));
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
    public StoreDto updateStore(Long storeId, StoreEntity storeEntity) {
        StoreEntity oneStore = oneStore(storeId);
        // 기존 매장 카테고리와 다를 시 새로운 연관관계 설정
        if (oneStore.getStoreCategory() != storeEntity.getStoreCategory()) {
            CategoryEntity oneCategory = oneCategory(storeEntity.getStoreCategory().getCategoryId());
            storeEntity.setStoreCategory(oneCategory);
            oneCategory.getStoreList().add(storeEntity);
        }
        oneStore.setName(storeEntity.getName());
        oneStore.setDescription(storeEntity.getDescription());
        oneStore.setOwner(storeEntity.getOwner());
        oneStore.setAddress(storeEntity.getAddress());
        oneStore.setZipCode(storeEntity.getZipCode());
        oneStore.setPhoneNumber(storeEntity.getPhoneNumber());
        return StoreEntity.toDto(oneStore);
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
