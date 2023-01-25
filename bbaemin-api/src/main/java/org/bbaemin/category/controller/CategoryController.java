package org.bbaemin.category.controller;

import org.bbaemin.category.controller.request.CreateCategoryRequest;
import org.bbaemin.category.controller.response.CategoryResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @GetMapping
    public List<CategoryResponse> listCategory() {
        return List.of(CategoryResponse.builder()
                .code("100")
                .name("돼지고기")
                .description("돼지고기")
                .parent("육류")
                .build());
    }

    @GetMapping("/{idx}")
    public CategoryResponse getCategory(@PathVariable Long idx) {
        return CategoryResponse.builder()
                .code("100")
                .name("돼지고기")
                .description("돼지고기")
                .parent("육류")
                .build();
    }

    @PostMapping
    public CategoryResponse createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        return CategoryResponse.builder()
                .code("100")
                .name("돼지고기")
                .description("돼지고기")
                .parent("육류")
                .build();
    }

    @PutMapping("/{idx}")
    public CategoryResponse updateCategory(@PathVariable Long idx) {
        return CategoryResponse.builder()
                .code("100")
                .name("소고기")
                .description("소고기")
                .parent("육류")
                .build();
    }

    @DeleteMapping("/{idx}")
    public Long deleteCategory(@PathVariable Long idx) {
        return idx;
    }
}
