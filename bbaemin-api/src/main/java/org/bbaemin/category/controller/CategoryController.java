package org.bbaemin.category.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.domain.CategoryDto;
import org.bbaemin.category.domain.CategoryEntity;
import org.bbaemin.category.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> listCategory() {
        return categoryService.listCategory();
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryEntity categoryEntity) {
        return categoryService.createCategory(categoryEntity);
    }

    @PutMapping("/{categoryId}")
    public CategoryDto updateCategory(@PathVariable Long categoryId, @RequestBody CategoryEntity categoryEntity) {
        return categoryService.updateCategory(categoryId, categoryEntity);
    }

    @DeleteMapping("/{categoryId}")
    public Long deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
