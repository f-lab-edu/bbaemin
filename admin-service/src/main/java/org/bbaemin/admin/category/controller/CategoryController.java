package org.bbaemin.admin.category.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.admin.category.controller.request.CreateCategoryRequest;
import org.bbaemin.admin.category.controller.request.UpdateCategoryRequest;
import org.bbaemin.admin.category.controller.response.CategoryResponse;
import org.bbaemin.admin.category.service.CategoryService;
import org.bbaemin.admin.category.vo.Category;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResult<List<CategoryResponse>> listCategory() {
        return ApiResult.ok(categoryService.listCategory().stream()
                .map(category -> CategoryResponse.builder()
                        .code(category.getCode())
                        .name(category.getName())
                        .description(category.getDescription())
                        .parentCode(category.getParent().getCode())
                        .build())
                .collect(Collectors.toList()));
    }

    @GetMapping("/{categoryId}")
    public ApiResult<CategoryResponse> getCategory(@PathVariable Long categoryId) {
        Category category = categoryService.getCategory(categoryId);
        return ApiResult.ok(CategoryResponse.builder()
                .code(category.getCode())
                .name(category.getName())
                .description(category.getDescription())
                .parentCode(category.getParent().getCode())
                .build());
    }

    @PostMapping
    public ApiResult<CategoryResponse> createCategory(@Validated @RequestBody CreateCategoryRequest createCategoryRequest) {

        Category category = Category.builder()
                .code(createCategoryRequest.getCode())
                .name(createCategoryRequest.getName())
                .description(createCategoryRequest.getDescription())
                .parent(createCategoryRequest.getParentId() != null ?
                        categoryService.getCategory(createCategoryRequest.getParentId()) :
                        null)
                .build();
        Category saved = categoryService.createCategory(category);
        CategoryResponse categoryResponse = CategoryResponse.builder()
                .code(saved.getCode())
                .name(saved.getName())
                .description(saved.getDescription())
                .parentCode(saved.getParent().getCode())
                .build();
        return ApiResult.created(categoryResponse);
    }

    @PutMapping("/{categoryId}")
    public ApiResult<CategoryResponse> updateCategory(@PathVariable Long categoryId, @Validated @RequestBody UpdateCategoryRequest updateCategoryRequest) {

        Category updated = categoryService.updateCategory(categoryId,
                updateCategoryRequest.getCode(),
                updateCategoryRequest.getName(),
                updateCategoryRequest.getDescription(),
                updateCategoryRequest.getParentId());

        CategoryResponse categoryResponse = CategoryResponse.builder()
                .code(updated.getCode())
                .name(updated.getName())
                .description(updated.getDescription())
                .parentCode(updated.getParent().getCode())
                .build();
        return ApiResult.ok(categoryResponse);
    }

    @DeleteMapping("/{categoryId}")
    public ApiResult<Long> deleteCategory(@PathVariable Long categoryId) {
        return ApiResult.ok(categoryService.deleteCategory(categoryId));
    }
}
