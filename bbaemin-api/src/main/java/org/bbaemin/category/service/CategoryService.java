package org.bbaemin.category.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.controller.response.CategoryResponse;
import org.bbaemin.category.repository.CategoryRepository;
import org.bbaemin.category.vo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * <pre>
     * 1. MethodName : listCategory
     * 2. ClassName  : CategoryService.java
     * 3. Comment    : 카테고리 리스트 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public List<CategoryResponse> listCategory() {
        return categoryRepository.findAll();
    }

    /**
     * <pre>
     * 1. MethodName : getCategory
     * 2. ClassName  : CategoryService.java
     * 3. Comment    : 카테고리 상세 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public CategoryResponse getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    /**
     * <pre>
     * 1. MethodName : createCategory
     * 2. ClassName  : CategoryService.java
     * 3. Comment    : 카테고리 등록
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public CategoryResponse createCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * <pre>
     * 1. MethodName : updateCategory
     * 2. ClassName  : CategoryService.java
     * 3. Comment    : 카테고리 수정
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public CategoryResponse updateCategory(Long categoryId, Category category) {
        return categoryRepository.update(categoryId, category);
    }

    /**
     * <pre>
     * 1. MethodName : deleteCategory
     * 2. ClassName  : CategoryService.java
     * 3. Comment    : 카테고리 삭제
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 08.
     * </pre>
     */
    public Long deleteCategory(Long categoryId) {
        return categoryRepository.deleteById(categoryId);
    }
}
