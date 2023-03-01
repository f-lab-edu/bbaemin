package org.bbaemin.api.category.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.api.category.controller.request.UpdateCategoryRequest;
import org.bbaemin.api.category.service.CategoryService;
import org.bbaemin.api.category.controller.request.CreateCategoryRequest;
import org.bbaemin.api.category.controller.response.CategoryResponse;
import org.bbaemin.api.category.vo.Category;
import org.bbaemin.config.response.ApiResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResult<List<CategoryResponse>> listCategory() {
        return ApiResult.ok(categoryService.listCategory().stream()
                .map(CategoryResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/{categoryId}")
    public ApiResult<CategoryResponse> getCategory(@PathVariable Long categoryId) {
        Category getCategory = categoryService.getCategory(categoryId);
        return ApiResult.ok(new CategoryResponse(getCategory));
    }

    @PostMapping
    public ApiResult<CategoryResponse> createCategory(@Validated @RequestBody CreateCategoryRequest createCategoryRequest) {
        Category category = Category.builder()
                .code(createCategoryRequest.getCode())
                .name(createCategoryRequest.getName())
                .description(createCategoryRequest.getDescription())
                .useYn(createCategoryRequest.isUseYn())
                .parent(createCategoryRequest.getParentId() != null ?
                        categoryService.getCategory(createCategoryRequest.getParentId()) :
                        null)
                .build();

        Category getCategory = categoryService.createCategory(category);
        return ApiResult.ok(new CategoryResponse(getCategory));
    }

    @PutMapping("/{categoryId}")
    public ApiResult<CategoryResponse> updateCategory(@PathVariable Long categoryId, @Validated @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        Category category = Category.builder()
                .code(updateCategoryRequest.getCode())
                .name(updateCategoryRequest.getName())
                .description(updateCategoryRequest.getDescription())
                .useYn(updateCategoryRequest.isUseYn())
                .parent(updateCategoryRequest.getParentId() != null ?
                        categoryService.getCategory(updateCategoryRequest.getParentId()) :
                        null)
                .build();

        Category getCategory = categoryService.updateCategory(categoryId, category);
        return ApiResult.ok(new CategoryResponse(getCategory));
    }

    @DeleteMapping("/{categoryId}")
    public ApiResult<Long> deleteCategory(@PathVariable Long categoryId) {
        return ApiResult.ok(categoryService.deleteCategory(categoryId));
    }
}
