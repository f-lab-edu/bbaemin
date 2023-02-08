package org.bbaemin.category.repository;

import org.bbaemin.category.controller.request.CreateCategoryRequest;
import org.bbaemin.category.controller.request.UpdateCategoryRequest;
import org.bbaemin.category.controller.response.CategoryResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CategoryRepository {

    private static final List<CategoryResponse> categoryList = new ArrayList<>();
    private static Long categoryId = 0L;

    static {
        categoryList.add(
                CategoryResponse.builder()
                        .categoryId(++categoryId)
                        .code(100)
                        .name("육류")
                        .description("육류")
                        .parent(null)
                        .build()
        );

        categoryList.add(
                CategoryResponse.builder()
                        .categoryId(++categoryId)
                        .code(101)
                        .name("돼지고기")
                        .description("돼지고기")
                        .parent(100)
                        .build()
        );
    }

    public CategoryResponse oneCategory(Long categoryId) {
        return categoryList.stream().filter(category -> category.getCategoryId().equals(categoryId))
                .findFirst().orElseThrow();
    }

    /**
     * <pre>
     * 1. MethodName : listCategory
     * 2. ClassName  : CategoryRepository.java
     * 3. Comment    : 카테고리 리스트 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public List<CategoryResponse> listCategory() {
        return categoryList;
    }

    /**
     * <pre>
     * 1. MethodName : getCategory
     * 2. ClassName  : CategoryRepository.java
     * 3. Comment    : 카테고리 상세 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public CategoryResponse getCategory(Long categoryId) {
        return oneCategory(categoryId);
    }

    /**
     * <pre>
     * 1. MethodName : createCategory
     * 2. ClassName  : CategoryRepository.java
     * 3. Comment    : 카테고리 등록
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {
        return CategoryResponse.builder()
                .categoryId(++categoryId)
                .code(createCategoryRequest.getCode())
                .name(createCategoryRequest.getName())
                .description(createCategoryRequest.getDescription())
                .parent(createCategoryRequest.getCode())
                .build();
    }

    /**
     * <pre>
     * 1. MethodName : updateCategory
     * 2. ClassName  : CategoryRepository.java
     * 3. Comment    : 카테고리 수정
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public CategoryResponse updateCategory(Long categoryId, UpdateCategoryRequest updateCategoryRequest) {
        return CategoryResponse.builder()
                .categoryId(categoryId)
                .code(updateCategoryRequest.getCode())
                .name(updateCategoryRequest.getName())
                .description(updateCategoryRequest.getDescription())
                .parent(updateCategoryRequest.getCode())
                .build();
    }

    /**
     * <pre>
     * 1. MethodName : deleteCategory
     * 2. ClassName  : CategoryRepository.java
     * 3. Comment    : 카테고리 삭제
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public Long deleteCategory(Long categoryId) {
        categoryList.stream().filter(category -> category.getCategoryId().equals(categoryId))
                .collect(Collectors.toList()).remove(0);

        return categoryId;
    }
}
