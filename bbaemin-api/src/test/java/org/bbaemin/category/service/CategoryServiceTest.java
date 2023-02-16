package org.bbaemin.category.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.domain.CategoryDto;
import org.bbaemin.category.domain.CategoryEntity;
import org.bbaemin.category.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
@DisplayName("카테고리 관련 Service Test")
class CategoryServiceTest {

    private final EntityManager em;

    @Mock
    private CategoryRepository mockCategoryRepository;
    @InjectMocks
    private CategoryService mockCategoryService;

    private CategoryEntity firstCategory;
    private CategoryEntity secondCategory;

    @BeforeEach
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        firstCategory = CategoryEntity.builder()
                .code(100)
                .name("편의점")
                .description("최상위 편의점")
                .parent(null)
                .useYn(true)
                .build();

        em.persist(firstCategory);

        secondCategory = CategoryEntity.builder()
                .code(101)
                .name("인천 계양편의점")
                .description("인천 계양편의점")
                .parent(firstCategory)
                .useYn(true)
                .build();

        em.persist(secondCategory);
    }

    @Test
    @DisplayName("카테고리_리스트_조회")
    void 카테고리_리스트_조회() {
        // given
        List<CategoryEntity> categoryList = new ArrayList<>();
        categoryList.add(firstCategory);
        categoryList.add(secondCategory);

        // when
        when(mockCategoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryDto> findCategoryList = mockCategoryService.listCategory();

        // then
        assertThat(findCategoryList.get(0).getCode()).isEqualTo(100);
        assertThat(findCategoryList.get(0).getName()).isEqualTo("편의점");
        assertThat(findCategoryList.get(0).getDescription()).isEqualTo("최상위 편의점");
        assertThat(findCategoryList.get(0).getParentCode()).isNull();

        assertThat(findCategoryList.get(1).getCode()).isEqualTo(101);
        assertThat(findCategoryList.get(1).getName()).isEqualTo("인천 계양편의점");
        assertThat(findCategoryList.get(1).getDescription()).isEqualTo("인천 계양편의점");
        assertThat(findCategoryList.get(1).getParentCode()).isEqualTo(firstCategory.getCode());

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
        CategoryDto getCategory = mockCategoryService.getCategory(firstCategory.getCategoryId());

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
        CategoryEntity parent = CategoryEntity.builder()
                .code(200)
                .name("육류")
                .description("육류")
                .parent(null)
                .build();

        // when
        when(mockCategoryRepository.save(parent)).thenReturn(parent);
        CategoryDto saveParent = mockCategoryService.createCategory(parent);

        // then
        assertThat(saveParent.getCode()).isEqualTo(200);
        assertThat(saveParent.getName()).isEqualTo("육류");
        assertThat(saveParent.getParentCode()).isNull();

        // verify
        verify(mockCategoryRepository, times(1)).save(parent);
        verify(mockCategoryRepository, atLeastOnce()).save(parent);
        verifyNoMoreInteractions(mockCategoryRepository);

        InOrder inOrder = inOrder(mockCategoryRepository);
        inOrder.verify(mockCategoryRepository).save(parent);

        // 자식 카테고리 등록
        // given
        CategoryEntity child = CategoryEntity.builder()
                .code(201)
                .name("돼지고기")
                .description("돼지고기")
                .parent(parent)
                .build();

        // when
        when(mockCategoryRepository.save(child)).thenReturn(child);
        CategoryDto saveChild = mockCategoryService.createCategory(child);

        // then
        assertThat(saveChild.getCode()).isEqualTo(201);
        assertThat(saveChild.getName()).isEqualTo("돼지고기");
        assertThat(saveChild.getDescription()).isEqualTo("돼지고기");
        assertThat(saveChild.getParentCode()).isEqualTo(200);

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
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryId(firstCategory.getCategoryId())
                .code(firstCategory.getCode())
                .name(firstCategory.getName())
                .description("모든 편의점")
                .parent(null)
                .build();

        // when
        when(mockCategoryRepository.findById(categoryEntity.getCategoryId())).thenReturn(Optional.of(categoryEntity));
        when(mockCategoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        CategoryDto updateCategory = mockCategoryService.updateCategory(categoryEntity.getCategoryId(), categoryEntity);

        // then
        assertThat(updateCategory.getCode()).isEqualTo(100);
        assertThat(updateCategory.getName()).isEqualTo("편의점");
        assertThat(updateCategory.getDescription()).isEqualTo("모든 편의점");

        // verify
        verify(mockCategoryRepository, times(1)).findById(categoryEntity.getCategoryId());
        verify(mockCategoryRepository, atLeastOnce()).findById(categoryEntity.getCategoryId());
        verifyNoMoreInteractions(mockCategoryRepository);

        InOrder inOrder = inOrder(mockCategoryRepository);
        inOrder.verify(mockCategoryRepository).findById(categoryEntity.getCategoryId());
    }

    @Test
    @DisplayName("카테고리_삭제")
    void 카테고리_삭제() {
        Long deleteCategoryId = mockCategoryService.deleteCategory(firstCategory.getCategoryId());
        assertThat(deleteCategoryId).isEqualTo(firstCategory.getCategoryId());
    }
}
