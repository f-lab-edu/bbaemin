package org.bbaemin.category.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.domain.CategoryDto;
import org.bbaemin.category.domain.CategoryEntity;
import org.bbaemin.category.service.CategoryService;
import org.bbaemin.config.response.ApiResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResult<List<CategoryDto>> listCategory() {
        return ApiResult.ok(categoryService.listCategory());
    }

    @GetMapping("/{categoryId}")
    public ApiResult<CategoryDto> getCategory(@PathVariable Long categoryId) {
        return ApiResult.ok(categoryService.getCategory(categoryId));
    }

    @PostMapping
    public ApiResult<CategoryDto> createCategory(@RequestBody CategoryEntity categoryEntity) {
        return ApiResult.ok(categoryService.createCategory(categoryEntity));
    }

    @PutMapping("/{categoryId}")
    public ApiResult<CategoryDto> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryEntity categoryEntity) {
        return ApiResult.ok(categoryService.updateCategory(categoryId, categoryEntity));
    }

    @DeleteMapping("/{categoryId}")
    public ApiResult<Long> deleteCategory(@PathVariable Long categoryId) {
        return ApiResult.ok(categoryService.deleteCategory(categoryId));
    }
}
