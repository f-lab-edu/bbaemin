package org.bbaemin.category.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.controller.request.CreateCategoryRequest;
import org.bbaemin.category.controller.request.UpdateCategoryRequest;
import org.bbaemin.category.controller.response.CategoryResponse;
import org.bbaemin.category.service.CategoryService;
import org.bbaemin.category.vo.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> listCategory() {
        return categoryService.listCategory();
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @PostMapping
    public CategoryResponse createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        Category category = Category.builder()
                .code(createCategoryRequest.getCode())
                .name(createCategoryRequest.getName())
                .description(createCategoryRequest.getDescription())
                .parentId(createCategoryRequest.getParentId())
                .build();

        return categoryService.createCategory(category);
    }

    @PutMapping("/{categoryId}")
    public CategoryResponse updateCategory(@PathVariable Long categoryId, @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        Category category = Category.builder()
                .code(updateCategoryRequest.getCode())
                .name(updateCategoryRequest.getName())
                .description(updateCategoryRequest.getDescription())
                .parentId(updateCategoryRequest.getParentId())
                .build();

        return categoryService.updateCategory(categoryId, category);
    }

    @DeleteMapping("/{categoryId}")
    public Long deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
