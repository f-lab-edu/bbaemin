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
    private CategoryRepository mockCategoryRepository;
    @InjectMocks
    private CategoryService mockCategoryService;
    private Category firstCategory;
    private Category secondCategory;

    @BeforeEach
    public void init() {
        firstCategory = Category.builder()
                .categoryId(1L)
                .code(100)
                .name("편의점")
                .description("최상위 편의점")
                .parent(null)
                .build();

        secondCategory = Category.builder()
                .categoryId(2L)
                .code(101)
                .name("인천 계양편의점")
                .description("인천 계양편의점")
                .parent(firstCategory)
                .build();
    }

    @Test
    @DisplayName("카테고리_리스트_조회")
    void 카테고리_리스트_조회() {
        // given
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(firstCategory);
        categoryList.add(secondCategory);

        // when
        when(mockCategoryRepository.findAll()).thenReturn(categoryList);
        List<Category> findCategoryList = mockCategoryService.listCategory();

        // then
        assertThat(findCategoryList.get(0).getCode()).isEqualTo(100);
        assertThat(findCategoryList.get(0).getName()).isEqualTo("편의점");
        assertThat(findCategoryList.get(0).getDescription()).isEqualTo("최상위 편의점");
        assertThat(findCategoryList.get(0).getParent()).isNull();

        assertThat(findCategoryList.get(1).getCode()).isEqualTo(101);
        assertThat(findCategoryList.get(1).getName()).isEqualTo("인천 계양편의점");
        assertThat(findCategoryList.get(1).getDescription()).isEqualTo("인천 계양편의점");
        assertThat(findCategoryList.get(1).getParent().getCode()).isEqualTo(firstCategory.getCode());

        // verify
        verify(mockCategoryRepository, times(1)).findAll();
        verify(mockCategoryRepository, atLeastOnce()).findAll();
        verifyNoMoreInteractions(mockCategoryRepository);

        InOrder inOrder = inOrder(mockCategoryRepository);
        inOrder.verify(mockCategoryRepository).findAll();
    }

    @Test
    @DisplayName("카테고리_상세_조회")
    void 카테고리_상세_조회() {
        // when
        when(mockCategoryRepository.findById(firstCategory.getCategoryId())).thenReturn(Optional.ofNullable(firstCategory));
        Category getCategory = mockCategoryService.getCategory(firstCategory.getCategoryId());

        // then
        assertThat(getCategory.getCode()).isEqualTo(100);
        assertThat(getCategory.getName()).isEqualTo("편의점");
        assertThat(getCategory.getDescription()).isEqualTo("최상위 편의점");

        // verify
        verify(mockCategoryRepository, times(1)).findById(firstCategory.getCategoryId());
        verify(mockCategoryRepository, atLeastOnce()).findById(firstCategory.getCategoryId());
        verifyNoMoreInteractions(mockCategoryRepository);

        InOrder inOrder = inOrder(mockCategoryRepository);
        inOrder.verify(mockCategoryRepository).findById(firstCategory.getCategoryId());
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
        when(mockCategoryRepository.save(parent)).thenReturn(parent);
        Category saveParent = mockCategoryService.createCategory(parent);

        // then
        assertThat(saveParent.getCode()).isEqualTo(200);
        assertThat(saveParent.getName()).isEqualTo("육류");
        assertThat(saveParent.getParent()).isNull();

        // verify
        verify(mockCategoryRepository, times(1)).save(parent);
        verify(mockCategoryRepository, atLeastOnce()).save(parent);
        verifyNoMoreInteractions(mockCategoryRepository);

        InOrder inOrder = inOrder(mockCategoryRepository);
        inOrder.verify(mockCategoryRepository).save(parent);

        // 자식 카테고리 등록
        // given
        Category child = Category.builder()
                .code(201)
                .name("돼지고기")
                .description("돼지고기")
                .parent(parent)
                .build();

        // when
        when(mockCategoryRepository.save(child)).thenReturn(child);
        Category saveChild = mockCategoryService.createCategory(child);

        // then
        assertThat(saveChild.getCode()).isEqualTo(201);
        assertThat(saveChild.getName()).isEqualTo("돼지고기");
        assertThat(saveChild.getDescription()).isEqualTo("돼지고기");
        assertThat(saveChild.getParent().getCode()).isEqualTo(200);

        // verify
        verify(mockCategoryRepository, times(1)).save(child);
        verify(mockCategoryRepository, atLeastOnce()).save(child);
        verifyNoMoreInteractions(mockCategoryRepository);

        inOrder.verify(mockCategoryRepository).save(child);
    }

    @Test
    @DisplayName("카테고리_수정")
    void 카테고리_수정() {
        // given
        Category category = Category.builder()
                .categoryId(firstCategory.getCategoryId())
                .code(firstCategory.getCode())
                .name(firstCategory.getName())
                .description("모든 편의점")
                .parent(null)
                .build();

        // when
        when(mockCategoryRepository.findById(category.getCategoryId())).thenReturn(Optional.of(category));
        Category updateCategory = mockCategoryService.updateCategory(category.getCategoryId(), category);

        // then
        assertThat(updateCategory.getCode()).isEqualTo(100);
        assertThat(updateCategory.getName()).isEqualTo("편의점");
        assertThat(updateCategory.getDescription()).isEqualTo("모든 편의점");

        // verify
        verify(mockCategoryRepository, times(1)).findById(category.getCategoryId());
        verify(mockCategoryRepository, atLeastOnce()).findById(category.getCategoryId());
        verifyNoMoreInteractions(mockCategoryRepository);

        InOrder inOrder = inOrder(mockCategoryRepository);
        inOrder.verify(mockCategoryRepository).findById(category.getCategoryId());
    }

    @Test
    @DisplayName("카테고리_삭제")
    void 카테고리_삭제() {
        Long deleteCategoryId = mockCategoryService.deleteCategory(firstCategory.getCategoryId());
        assertThat(deleteCategoryId).isEqualTo(firstCategory.getCategoryId());
    }
}
