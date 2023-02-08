package org.bbaemin.category.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.controller.request.CreateCategoryRequest;
import org.bbaemin.category.controller.request.UpdateCategoryRequest;
import org.bbaemin.category.controller.response.CategoryResponse;
import org.bbaemin.category.service.CategoryService;
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
        return categoryService.createCategory(createCategoryRequest);
    }

    @PutMapping("/{categoryId}")
    public CategoryResponse updateCategory(@PathVariable Long categoryId, @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        return categoryService.updateCategory(categoryId, updateCategoryRequest);
    }

    @DeleteMapping("/{categoryId}")
    public Long deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
