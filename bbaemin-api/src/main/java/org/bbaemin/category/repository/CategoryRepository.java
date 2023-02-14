package org.bbaemin.category.repository;

import org.bbaemin.category.controller.response.CategoryResponse;
import org.bbaemin.category.vo.Category;
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
     * 1. MethodName : findAll
     * 2. ClassName  : CategoryRepository.java
     * 3. Comment    : 카테고리 리스트 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public List<CategoryResponse> findAll() {
        return categoryList;
    }

    /**
     * <pre>
     * 1. MethodName : findById
     * 2. ClassName  : CategoryRepository.java
     * 3. Comment    : 카테고리 상세 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public CategoryResponse findById(Long categoryId) {
        return oneCategory(categoryId);
    }

    /**
     * <pre>
     * 1. MethodName : save
     * 2. ClassName  : CategoryRepository.java
     * 3. Comment    : 카테고리 등록
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public CategoryResponse save(Category category) {
        return CategoryResponse.builder()
                .categoryId(++categoryId)
                .code(category.getCode())
                .name(category.getName())
                .description(category.getDescription())
                .parent(category.getCode())
                .build();
    }

    /**
     * <pre>
     * 1. MethodName : update
     * 2. ClassName  : CategoryRepository.java
     * 3. Comment    : 카테고리 수정
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public CategoryResponse update(Long categoryId, Category category) {
        return CategoryResponse.builder()
                .categoryId(categoryId)
                .code(category.getCode())
                .name(category.getName())
                .description(category.getDescription())
                .parent(category.getCode())
                .build();
    }

    /**
     * <pre>
     * 1. MethodName : deleteById
     * 2. ClassName  : CategoryRepository.java
     * 3. Comment    : 카테고리 삭제
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public Long deleteById(Long categoryId) {
        categoryList.stream().filter(category -> category.getCategoryId().equals(categoryId))
                .collect(Collectors.toList()).remove(0);

        return categoryId;
    }
}
