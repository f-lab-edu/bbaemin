package org.bbaemin.category.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.domain.CategoryDto;
import org.bbaemin.category.domain.CategoryEntity;
import org.bbaemin.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private CategoryEntity oneCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }

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
    public List<CategoryDto> listCategory() {
        return categoryRepository.findAll()
                .stream().map(CategoryEntity::toDto)
                .collect(Collectors.toList());
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
    public CategoryDto getCategory(Long categoryId) {
        return CategoryEntity.toDto(oneCategory(categoryId));
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
    public CategoryDto createCategory(CategoryEntity categoryEntity) {
        if (categoryEntity.getParent() != null) {
            categoryEntity.setParent(categoryEntity.getParent());
            categoryEntity.getChildren().add(categoryEntity);
        }
        return CategoryEntity.toDto(categoryRepository.save(categoryEntity));
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
    public CategoryDto updateCategory(Long categoryId, CategoryEntity categoryEntity) {
        CategoryEntity oneCategory = oneCategory(categoryId);
        oneCategory.setCode(categoryEntity.getCode());
        oneCategory.setName(categoryEntity.getName());
        oneCategory.setDescription(categoryEntity.getDescription());
        if (oneCategory.getParent() != null) {
            oneCategory.setParent(categoryEntity.getParent());
            oneCategory.getChildren().add(categoryEntity);
        }
        return CategoryEntity.toDto(oneCategory);
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
        categoryRepository.findById(categoryId);
        return categoryId;
    }
}
