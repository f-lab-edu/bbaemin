package org.bbaemin.category.service;

import org.bbaemin.category.controller.response.CategoryResponse;
import org.bbaemin.category.repository.CategoryRepository;
import org.bbaemin.category.vo.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private static final List<CategoryResponse> categoryList = new ArrayList<>();
    private static Long categoryId = 0L;

    void createCategory() {
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

    @BeforeEach
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        createCategory();
    }

    @Test
    @DisplayName("카테고리 리스트 조회")
    void 카테고리리스트조회() {
        // when
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryResponse> categoryList = categoryService.listCategory();

        // then
        assertThat(categoryList.get(0).getCode()).isEqualTo(100);
        assertThat(categoryList.get(0).getName()).isEqualTo("육류");
        assertThat(categoryList.get(0).getDescription()).isEqualTo("육류");
        assertThat(categoryList.get(0).getParent()).isNull();

        assertThat(categoryList.get(1).getCode()).isEqualTo(101);
        assertThat(categoryList.get(1).getName()).isEqualTo("돼지고기");
        assertThat(categoryList.get(1).getDescription()).isEqualTo("돼지고기");
        assertThat(categoryList.get(1).getParent()).isEqualTo(100);

        // verify
        verify(categoryRepository, times(1)).findAll();
        verify(categoryRepository, atLeastOnce()).findAll();
        verifyNoMoreInteractions(categoryRepository);

        InOrder inOrder = inOrder(categoryRepository);
        inOrder.verify(categoryRepository).findAll();
    }

    @Test
    @DisplayName("카테고리 상세 조회")
    void 카테고리상세조회() {
        // when
        when(categoryRepository.findById(1L)).thenReturn(categoryList.get(0));
        CategoryResponse getCategory = categoryService.getCategory(1L);

        // then
        assertThat(getCategory.getCode()).isEqualTo(100);
        assertThat(getCategory.getName()).isEqualTo("육류");
        assertThat(getCategory.getDescription()).isEqualTo("육류");

        // verify
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, atLeastOnce()).findById(1L);
        verifyNoMoreInteractions(categoryRepository);

        InOrder inOrder = inOrder(categoryRepository);
        inOrder.verify(categoryRepository).findById(1L);
    }

    @Test
    @DisplayName("카테고리 등록")
    void 카테고리등록() {
        Category category = Category.builder()
                .code(102)
                .name("닭고기")
                .description("닭고기")
                .parentId(1L)
                .build();

        CategoryResponse categoryResponse = CategoryResponse.builder()
                .categoryId(categoryId)
                .code(102)
                .name(category.getName())
                .description(category.getDescription())
                .parent(100)
                .build();

        // when
        when(categoryRepository.save(category)).thenReturn(categoryResponse);
        CategoryResponse getCategory = categoryService.createCategory(category);

        // then
        assertThat(getCategory.getCode()).isEqualTo(102);
        assertThat(getCategory.getName()).isEqualTo("닭고기");
        assertThat(getCategory.getDescription()).isEqualTo("닭고기");
        assertThat(getCategory.getParent()).isEqualTo(100);

        // verify
        verify(categoryRepository, times(1)).save(category);
        verify(categoryRepository, atLeastOnce()).save(category);
        verifyNoMoreInteractions(categoryRepository);

        InOrder inOrder = inOrder(categoryRepository);
        inOrder.verify(categoryRepository).save(category);
    }

    @Test
    @DisplayName("카테고리 수정")
    void 카테고리수정() {
        Category category = Category.builder()
                .code(102)
                .name("소고기")
                .description("소고기")
                .parentId(1L)
                .build();

        CategoryResponse updateResponse = CategoryResponse.builder()
                .categoryId(categoryId)
                .code(102)
                .name(category.getName())
                .description(category.getDescription())
                .parent(100)
                .build();

        // when
        when(categoryRepository.update(2L, category)).thenReturn(updateResponse);
        CategoryResponse updateCategory = categoryService.updateCategory(2L, category);

        // then
        assertThat(updateCategory.getCode()).isEqualTo(102);
        assertThat(updateCategory.getName()).isEqualTo("소고기");
        assertThat(updateCategory.getDescription()).isEqualTo("소고기");

        // verify
        verify(categoryRepository, times(1)).update(2L, category);
        verify(categoryRepository, atLeastOnce()).update(2L, category);
        verifyNoMoreInteractions(categoryRepository);

        InOrder inOrder = inOrder(categoryRepository);
        inOrder.verify(categoryRepository).update(2L, category);
    }

    @Test
    @DisplayName("카테고리 삭제")
    void 카테고리삭제() {
        // when
        when(categoryRepository.deleteById(2L)).thenReturn(2L);
        Long deleteCategoryId = categoryService.deleteCategory(2L);

        // then
        assertThat(deleteCategoryId).isEqualTo(2L);

        // verify
        verify(categoryRepository, times(1)).deleteById(2L);
        verify(categoryRepository, atLeastOnce()).deleteById(2L);
        verifyNoMoreInteractions(categoryRepository);

        InOrder inOrder = inOrder(categoryRepository);
        inOrder.verify(categoryRepository).deleteById(2L);
    }
}
