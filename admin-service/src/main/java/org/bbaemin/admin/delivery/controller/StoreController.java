package org.bbaemin.admin.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.admin.category.service.CategoryService;
import org.bbaemin.admin.category.vo.Category;
import org.bbaemin.admin.delivery.controller.request.CreateStoreRequest;
import org.bbaemin.admin.delivery.controller.request.UpdateStoreRequest;
import org.bbaemin.admin.delivery.controller.response.StoreResponse;
import org.bbaemin.admin.delivery.service.StoreService;
import org.bbaemin.admin.delivery.vo.Store;
import org.bbaemin.config.response.ApiResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final CategoryService categoryService;

    @GetMapping
    public ApiResult<List<StoreResponse>> listStore() {
        List<Store> storeList = storeService.listStore();
        List<StoreResponse> storeResponseList = storeList.stream()
                .map(store -> StoreResponse.builder()
                        .categoryCode(store.getStoreCategory().getCode())
                        .name(store.getName())
                        .description(store.getDescription())
                        .owner(store.getOwner())
                        .address(store.getAddress())
                        .zipCode(store.getZipCode())
                        .phoneNumber(store.getPhoneNumber())
                        .build())
                .collect(Collectors.toList());
        return ApiResult.ok(storeResponseList);
    }

    @GetMapping("/{storeId}")
    public ApiResult<StoreResponse> getStore(@PathVariable Long storeId) {
        Store store = storeService.getStore(storeId);
        return ApiResult.ok(StoreResponse.builder()
                .categoryCode(store.getStoreCategory().getCode())
                .name(store.getName())
                .description(store.getDescription())
                .owner(store.getOwner())
                .address(store.getAddress())
                .zipCode(store.getZipCode())
                .phoneNumber(store.getPhoneNumber())
                .build());
    }

    @PostMapping
    public ApiResult<StoreResponse> createStore(@Validated @RequestBody CreateStoreRequest createStoreRequest) {

        Category category = categoryService.getCategory(createStoreRequest.getCategoryId());
        Store store = Store.builder()
                .name(createStoreRequest.getName())
                .description(createStoreRequest.getDescription())
                .owner(createStoreRequest.getOwner())
                .address(createStoreRequest.getAddress())
                .zipCode(createStoreRequest.getZipCode())
                .phoneNumber(createStoreRequest.getPhoneNumber())
                .storeCategory(category)
                .build();
        Store saved = storeService.createStore(store);
        StoreResponse storeResponse = StoreResponse.builder()
                .categoryCode(saved.getStoreCategory().getCode())
                .name(saved.getName())
                .description(saved.getDescription())
                .owner(saved.getOwner())
                .address(saved.getAddress())
                .zipCode(saved.getZipCode())
                .phoneNumber(saved.getPhoneNumber())
                .build();
        return ApiResult.created(storeResponse);
    }

    @PutMapping("/{storeId}")
    public ApiResult<StoreResponse> updateStore(@PathVariable Long storeId, @Validated @RequestBody UpdateStoreRequest updateStoreRequest) {

        Store updated = storeService.updateStore(storeId,
                updateStoreRequest.getName(),
                updateStoreRequest.getDescription(),
                updateStoreRequest.getOwner(),
                updateStoreRequest.getAddress(),
                updateStoreRequest.getZipCode(),
                updateStoreRequest.getPhoneNumber(),
                updateStoreRequest.getCategoryId());

        StoreResponse storeResponse = StoreResponse.builder()
                .categoryCode(updated.getStoreCategory().getCode())
                .name(updated.getName())
                .description(updated.getDescription())
                .owner(updated.getOwner())
                .address(updated.getAddress())
                .zipCode(updated.getZipCode())
                .phoneNumber(updated.getPhoneNumber())
                .build();
        return ApiResult.ok(storeResponse);
    }

    @DeleteMapping("/{storeId}")
    public ApiResult<Long> deleteStore(@PathVariable Long storeId) {
        return ApiResult.ok(storeService.deleteStore(storeId));
    }
}
