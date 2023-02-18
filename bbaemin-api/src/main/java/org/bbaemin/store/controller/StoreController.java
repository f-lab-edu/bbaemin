package org.bbaemin.store.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.store.domain.StoreDto;
import org.bbaemin.store.domain.StoreEntity;
import org.bbaemin.store.service.StoreService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ApiResult<List<StoreDto>> listStore() {
        return ApiResult.ok(storeService.listStore());
    }

    @GetMapping("/{storeId}")
    public ApiResult<StoreDto> getStore(@PathVariable Long storeId) {
        return ApiResult.ok(storeService.getStore(storeId));
    }

    @PostMapping
    public ApiResult<StoreDto> createStore(@Valid @RequestBody StoreEntity storeEntity) {
        return ApiResult.ok(storeService.createStore(storeEntity));
    }

    @PutMapping("/{storeId}")
    public ApiResult<StoreDto> updateStore(@PathVariable Long storeId, @Valid @RequestBody StoreEntity storeEntity) {
        return ApiResult.ok(storeService.updateStore(storeId, storeEntity));
    }

    @DeleteMapping("/{storeId}")
    public ApiResult<Long> deleteStore(@PathVariable Long storeId) {
        return ApiResult.ok(storeService.deleteStore(storeId));
    }
}
