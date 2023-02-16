package org.bbaemin.store.controller;

import lombok.RequiredArgsConstructor;
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
    public List<StoreDto> listStore() {
        return storeService.listStore();
    }

    @GetMapping("/{storeId}")
    public StoreDto getStore(@PathVariable Long storeId) {
        return storeService.getStore(storeId);
    }

    @PostMapping
    public StoreDto createStore(@Valid @RequestBody StoreEntity storeEntity) {
        return storeService.createStore(storeEntity);
    }

    @PutMapping("/{storeId}")
    public StoreDto updateStore(@PathVariable Long storeId, @Valid @RequestBody StoreEntity storeEntity) {
        return storeService.updateStore(storeId, storeEntity);
    }

    @DeleteMapping("/{storeId}")
    public Long deleteStore(@PathVariable Long storeId) {
        return storeService.deleteStore(storeId);
    }
}
