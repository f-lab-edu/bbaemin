package org.bbaemin.admin.service.category.service;

import org.bbaemin.admin.category.service.CategoryService;
import org.bbaemin.admin.category.vo.Category;
import org.bbaemin.admin.category.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("카테고리 관련 Service Test")
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;
    private Category category1;
    private Category category2;

    @BeforeEach
    public void init() {
        category1 = Category.builder()
                .categoryId(1L)
                .code(100)
                .name("편의점")
                .description("최상위 편의점")
                .parent(null)
                .build();

        category2 = Category.builder()
                .categoryId(2L)
                .code(101)
                .name("인천 계양편의점")
                .description("인천 계양편의점")
                .parent(category1)
                .build();
    }

    @Test
    @DisplayName("카테고리_리스트_조회")
    void 카테고리_리스트_조회() {
        // given
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);

        // when
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<Category> findCategoryList = categoryService.listCategory();

        // then
        assertThat(findCategoryList.get(0).getCode()).isEqualTo(100);
        assertThat(findCategoryList.get(0).getName()).isEqualTo("편의점");
        assertThat(findCategoryList.get(0).getDescription()).isEqualTo("최상위 편의점");
        assertThat(findCategoryList.get(0).getParent()).isNull();

        assertThat(findCategoryList.get(1).getCode()).isEqualTo(101);
        assertThat(findCategoryList.get(1).getName()).isEqualTo("인천 계양편의점");
        assertThat(findCategoryList.get(1).getDescription()).isEqualTo("인천 계양편의점");
        assertThat(findCategoryList.get(1).getParent().getCode()).isEqualTo(category1.getCode());

        // verify
        verify(categoryRepository, times(1)).findAll();
        verify(categoryRepository, atLeastOnce()).findAll();
        verifyNoMoreInteractions(categoryRepository);

        InOrder inOrder = inOrder(categoryRepository);
        inOrder.verify(categoryRepository).findAll();
    }

    @Test
    @DisplayName("카테고리_상세_조회")
    void 카테고리_상세_조회() {
        // when
        when(categoryRepository.findById(category1.getCategoryId())).thenReturn(Optional.ofNullable(category1));
        Category getCategory = categoryService.getCategory(category1.getCategoryId());

        // then
        assertThat(getCategory.getCode()).isEqualTo(100);
        assertThat(getCategory.getName()).isEqualTo("편의점");
        assertThat(getCategory.getDescription()).isEqualTo("최상위 편의점");

        // verify
        verify(categoryRepository, times(1)).findById(category1.getCategoryId());
        verify(categoryRepository, atLeastOnce()).findById(category1.getCategoryId());
        verifyNoMoreInteractions(categoryRepository);

        InOrder inOrder = inOrder(categoryRepository);
        inOrder.verify(categoryRepository).findById(category1.getCategoryId());
    }

    @Test
    @DisplayName("카테고리_등록")
    void 카테고리_등록() {
        // 부모 카테고리 등록
        // given
        Category parent = Category.builder()
                .code(200)
                .name("육류")
                .description("육류")
                .parent(null)
                .build();

        // when
        when(categoryRepository.save(parent)).thenReturn(parent);
        Category saveParent = categoryService.createCategory(parent);

        // then
        assertThat(saveParent.getCode()).isEqualTo(200);
        assertThat(saveParent.getName()).isEqualTo("육류");
        assertThat(saveParent.getParent()).isNull();

        // verify
        verify(categoryRepository, times(1)).save(parent);
        verify(categoryRepository, atLeastOnce()).save(parent);
        verifyNoMoreInteractions(categoryRepository);

        InOrder inOrder = inOrder(categoryRepository);
        inOrder.verify(categoryRepository).save(parent);

        // 자식 카테고리 등록
        // given
        Category child = Category.builder()
                .code(201)
                .name("돼지고기")
                .description("돼지고기")
                .parent(parent)
                .build();

        // when
        when(categoryRepository.save(child)).thenReturn(child);
        Category saveChild = categoryService.createCategory(child);

        // then
        assertThat(saveChild.getCode()).isEqualTo(201);
        assertThat(saveChild.getName()).isEqualTo("돼지고기");
        assertThat(saveChild.getDescription()).isEqualTo("돼지고기");
        assertThat(saveChild.getParent().getCode()).isEqualTo(200);

        // verify
        verify(categoryRepository, times(1)).save(child);
        verify(categoryRepository, atLeastOnce()).save(child);
        verifyNoMoreInteractions(categoryRepository);

        inOrder.verify(categoryRepository).save(child);
    }

    @Test
    @DisplayName("카테고리_수정")
    void 카테고리_수정() {

        // given
        when(categoryRepository.findById(category1.getCategoryId())).thenReturn(Optional.of(category1));
        // when
        Category updatedCategory = categoryService.updateCategory(
                category1.getCategoryId(),
                category1.getCode(),
                category1.getName(),
                "모든 편의점",
                null);

        // then
        assertThat(updatedCategory.getCode()).isEqualTo(100);
        assertThat(updatedCategory.getName()).isEqualTo("편의점");
        assertThat(updatedCategory.getDescription()).isEqualTo("모든 편의점");

        // verify
        verify(categoryRepository, times(1)).findById(category1.getCategoryId());
        verify(categoryRepository, atLeastOnce()).findById(category1.getCategoryId());
        verifyNoMoreInteractions(categoryRepository);

        InOrder inOrder = inOrder(categoryRepository);
        inOrder.verify(categoryRepository).findById(category1.getCategoryId());
    }

    @Test
    @DisplayName("카테고리_삭제")
    void 카테고리_삭제() {
        Long deleteCategoryId = categoryService.deleteCategory(category1.getCategoryId());
        assertThat(deleteCategoryId).isEqualTo(category1.getCategoryId());
    }
}
