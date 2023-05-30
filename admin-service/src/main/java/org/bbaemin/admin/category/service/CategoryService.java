package org.bbaemin.admin.category.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.admin.category.vo.Category;
import org.bbaemin.admin.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("categoryId : " + categoryId));
    }

    @Transactional
    public Category createCategory(Category category) {
        if (category.getParent() != null) {
            category.setParent(category.getParent());
            category.getChildren().add(category);
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long categoryId, Integer code, String name, String description, Long parentId) {

        Category updated = getCategory(categoryId);
        updated.setCode(code);
        updated.setName(name);
        updated.setDescription(description);

        if (!ObjectUtils.isEmpty(parentId)) {
            Category parent = getCategory(parentId);
            updated.setParent(parent);
        }

        return updated;
    }

    @Transactional
    public Long deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return categoryId;
    }
}
