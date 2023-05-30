package org.bbaemin.admin.service.delivery.service;

import org.bbaemin.admin.category.vo.Category;
import org.bbaemin.admin.category.service.CategoryService;
import org.bbaemin.admin.delivery.service.StoreService;
import org.bbaemin.admin.delivery.vo.Store;
import org.bbaemin.admin.delivery.repository.StoreRepository;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("매장 관련 Service Test")
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private StoreService storeService;

    private Store store;
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
                .code(200)
                .name("매장")
                .description("최상위 매장")
                .parent(null)
                .build();

        store = Store.builder()
                .storeId(1L)
                .storeCategory(category1)
                .name("B마트 인천점")
                .description("B마트 인천점")
                .owner("점주")
                .address("인천광역시 계양구")
                .zipCode("123-456")
                .phoneNumber("010-1234-5678")
                .build();
    }


    @Test
    @DisplayName("매장_리스트_조회_테스트")
    void 매장_리스트_조회_테스트() {

        // given
        List<Store> storeList = new ArrayList<>();
        storeList.add(store);
        when(storeRepository.findAll()).thenReturn(storeList);

        // when
        List<Store> findStoreList = storeService.listStore();

        // then
        assertThat(findStoreList.size()).isEqualTo(1);
        assertThat(findStoreList.get(0).getStoreCategory().getCode()).isEqualTo(100);
        assertThat(findStoreList.get(0).getStoreCategory().getName()).isEqualTo("편의점");
        assertThat(findStoreList.get(0).getName()).isEqualTo("B마트 인천점");
        assertThat(findStoreList.get(0).getOwner()).isEqualTo("점주");

        // verify
        verify(storeRepository, times(1)).findAll();
        verify(storeRepository, atLeastOnce()).findAll();
        verifyNoMoreInteractions(storeRepository);

        InOrder inOrder = inOrder(storeRepository);
        inOrder.verify(storeRepository).findAll();
    }

    @Test
    @DisplayName("매장_상세_조회_테스트")
    void 매장_상세_조회_테스트() {

        // given
        Long storeId = store.getStoreId();
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        // when
        Store getById = storeService.getStore(store.getStoreId());
        // then
        assertThat(getById.getStoreCategory().getCode()).isEqualTo(100);
        assertThat(getById.getStoreCategory().getName()).isEqualTo("편의점");
        assertThat(getById.getName()).isEqualTo("B마트 인천점");

        // verify
        verify(storeRepository, times(1)).findById(store.getStoreId());
        verify(storeRepository, atLeastOnce()).findById(store.getStoreId());
        verifyNoMoreInteractions(storeRepository);

        InOrder inOrder = inOrder(storeRepository);
        inOrder.verify(storeRepository).findById(store.getStoreId());
    }

    @Test
    @DisplayName("매장_등록_테스트")
    void 매장_등록_테스트() {

        // given
        when(categoryService.getCategory(category1.getCategoryId())).thenReturn(category1);
        Category storeCategory = categoryService.getCategory(category1.getCategoryId());
        Store store = Store.builder()
                .storeCategory(storeCategory)
                .name("B마트 서울 관악점")
                .description("B마트 서울 관악점")
                .owner("점주")
                .address("서울특별시 관악구")
                .zipCode("123-456")
                .phoneNumber("010-1234-5678")
                .build();
        when(storeRepository.save(store)).thenReturn(store);

        // when
        Store saved = storeService.createStore(store);

        // then
        assertThat(saved.getStoreCategory().getCode()).isEqualTo(100);
        assertThat(saved.getStoreCategory().getName()).isEqualTo("편의점");
        assertThat(saved.getName()).isEqualTo("B마트 서울 관악점");

        // verify
        verify(storeRepository, times(1)).save(store);
        verify(storeRepository, atLeastOnce()).save(store);
        verifyNoMoreInteractions(storeRepository);

        InOrder inOrder = inOrder(storeRepository);
        inOrder.verify(storeRepository).save(store);
    }

    @Test
    @DisplayName("매장_동일_카테고리_수정_테스트")
    void 매장_동일_카테고리_수정_테스트() {

        // given
        Long storeId = store.getStoreId();
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        // when
        Store updatedStore = storeService.updateStore(storeId,
                "B마트 서울 관악점",
                "B마트 서울 관악점",
                "점주",
                "서울특별시 관악구",
                "123-456",
                "010-1234-5678",
                category1.getCategoryId());

        // then
        assertThat(updatedStore.getStoreCategory().getCode()).isEqualTo(100);
        assertThat(updatedStore.getStoreCategory().getName()).isEqualTo("편의점");
        assertThat(updatedStore.getName()).isEqualTo("B마트 서울 관악점");

        // verify
        verify(storeRepository, times(1)).findById(storeId);
        verify(storeRepository, atLeastOnce()).findById(storeId);
        verifyNoMoreInteractions(storeRepository);

        InOrder inOrder = inOrder(storeRepository);
        inOrder.verify(storeRepository).findById(storeId);
    }

    @Test
    @DisplayName("매장_다른_카테고리_수정_테스트")
    void 매장_다른_카테고리_수정_테스트() {

        // given
        Long storeId = store.getStoreId();
        when(categoryService.getCategory(category2.getCategoryId())).thenReturn(category2);
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        // when
        Store updatedStore = storeService.updateStore(storeId,
                "B마트 서울 관악점",
                "B마트 서울 관악점",
                "점주",
                "서울특별시 관악구",
                "123-456",
                "010-1234-5678",
                category2.getCategoryId());

        // then
        assertThat(updatedStore.getStoreCategory().getCode()).isEqualTo(200);
        assertThat(updatedStore.getStoreCategory().getName()).isEqualTo("매장");
        assertThat(updatedStore.getName()).isEqualTo("B마트 서울 관악점");

        // verify
        verify(storeRepository, times(1)).findById(storeId);
        verify(storeRepository, atLeastOnce()).findById(storeId);
        verifyNoMoreInteractions(storeRepository);

        InOrder inOrder = inOrder(storeRepository);
        inOrder.verify(storeRepository).findById(storeId);
    }

    @Test
    @DisplayName("매장_삭제_테스트")
    void 매장_삭제_테스트() {
        Long deletedStoreId = storeService.deleteStore(store.getStoreId());
        assertThat(deletedStoreId).isEqualTo(store.getStoreId());
    }
}
