package org.bbaemin.admin.category.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.admin.category.vo.Category;
import org.bbaemin.admin.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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
    @Transactional(readOnly = true)
    public List<Category> listCategory() {
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
    @Transactional(readOnly = true)
    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("categoryId : " + categoryId));
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
    @Transactional
    public Category createCategory(Category category) {
        if (category.getParent() != null) {
            category.setParent(category.getParent());
            category.getChildren().add(category);
        }
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
    @Transactional
    public Category updateCategory(Long categoryId, Category category) {
        Category oneCategory = getCategory(categoryId);
        oneCategory.setCode(category.getCode());
        oneCategory.setName(category.getName());
        oneCategory.setDescription(category.getDescription());
        if (oneCategory.getParent() != null) {
            oneCategory.setParent(category.getParent());
            oneCategory.getChildren().add(category);
        }
        return oneCategory;
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
    @Transactional
    public Long deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return categoryId;
    }
}
