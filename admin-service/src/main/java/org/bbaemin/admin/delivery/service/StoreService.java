package org.bbaemin.admin.delivery.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.admin.category.service.CategoryService;
import org.bbaemin.admin.category.vo.Category;
import org.bbaemin.admin.delivery.repository.StoreRepository;
import org.bbaemin.admin.delivery.vo.Store;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryService categoryService;


    @Transactional(readOnly = true)
    public List<Store> listStore() {
        return storeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Store getStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new NoSuchElementException("storeId : " + storeId));
    }

    @Transactional
    public Store createStore(Store store) {
        Category category = categoryService.getCategory(store.getStoreCategory().getCategoryId());
        store.setStoreCategory(category);
        return storeRepository.save(store);
    }

    @Transactional
    public Store updateStore(Long storeId, String name, String description, String owner, String address, String zipCode, String phoneNumber, Long categoryId) {

        Store updated = getStore(storeId);
        updated.setName(name);
        updated.setDescription(description);
        updated.setOwner(owner);
        updated.setAddress(address);
        updated.setZipCode(zipCode);
        updated.setPhoneNumber(phoneNumber);

        if (!Objects.equals(updated.getStoreCategory().getCategoryId(), categoryId)) {
            Category category = categoryService.getCategory(categoryId);
            updated.setStoreCategory(category);
        }
        return updated;
    }

    @Transactional
    public Long deleteStore(Long storeId) {
        storeRepository.deleteById(storeId);
        return storeId;
    }
}
