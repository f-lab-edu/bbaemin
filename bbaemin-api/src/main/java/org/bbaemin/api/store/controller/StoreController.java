package org.bbaemin.api.store.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.api.store.vo.Store;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.api.store.controller.request.CreateStoreRequest;
import org.bbaemin.api.store.controller.request.UpdateStoreRequest;
import org.bbaemin.api.store.controller.response.StoreResponse;
import org.bbaemin.api.store.service.StoreService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ApiResult<List<StoreResponse>> listStore() {
        return ApiResult.ok(storeService.listStore().stream()
                .map(StoreResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/{storeId}")
    public ApiResult<StoreResponse> getStore(@PathVariable Long storeId) {
        Store getStore = storeService.getStore(storeId);
        return ApiResult.ok(new StoreResponse(getStore));
    }

    @PostMapping
    public ApiResult<Store> createStore(@Validated @RequestBody CreateStoreRequest createStoreRequest) {
        Store store = Store.builder()
                .name(createStoreRequest.getName())
                .description(createStoreRequest.getDescription())
                .owner(createStoreRequest.getOwner())
                .address(createStoreRequest.getAddress())
                .zipCode(createStoreRequest.getZipCode())
                .phoneNumber(createStoreRequest.getPhoneNumber())
                .useYn(createStoreRequest.isUseYn())
                .storeCategory(storeService.getCategory(createStoreRequest.getCategoryId()))
                .build();

        return ApiResult.ok(storeService.createStore(store));
    }

    @PutMapping("/{storeId}")
    public ApiResult<Store> updateStore(@PathVariable Long storeId, @Validated @RequestBody UpdateStoreRequest updateStoreRequest) {
        Store store = Store.builder()
                .name(updateStoreRequest.getName())
                .description(updateStoreRequest.getDescription())
                .owner(updateStoreRequest.getOwner())
                .address(updateStoreRequest.getAddress())
                .zipCode(updateStoreRequest.getZipCode())
                .phoneNumber(updateStoreRequest.getPhoneNumber())
                .useYn(updateStoreRequest.isUseYn())
                .storeCategory(storeService.getCategory(updateStoreRequest.getCategoryId()))
                .build();

        return ApiResult.ok(storeService.updateStore(storeId, store));
    }

    @DeleteMapping("/{storeId}")
    public ApiResult<Long> deleteStore(@PathVariable Long storeId) {
        return ApiResult.ok(storeService.deleteStore(storeId));
    }
}
