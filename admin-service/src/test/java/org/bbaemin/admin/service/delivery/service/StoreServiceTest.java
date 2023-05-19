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
    private StoreRepository mockStoreRepository;
    @InjectMocks
    private StoreService mockStoreService;

    @Mock
    private CategoryService mockCategoryService;

    private Store store;
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
                .code(200)
                .name("매장")
                .description("최상위 매장")
                .parent(null)
                .build();

        store = Store.builder()
                .storeId(1L)
                .storeCategory(firstCategory)
                .name("B마트 인천점")
                .description("B마트 인천점")
                .owner("점주")
                .address("인천광역시 계양구")
                .zipCode("123-456")
                .phoneNumber("01012345678")
                .build();
    }


    @Test
    @DisplayName("매장_리스트_조회_테스트")
    void 매장_리스트_조회_테스트() {
        // given
        List<Store> storeList = new ArrayList<>();
        storeList.add(store);

        // when
        when(mockStoreRepository.findAll()).thenReturn(storeList);
        List<Store> findStoreList = mockStoreService.listStore();

        // then
        assertThat(findStoreList.size()).isEqualTo(1);
        assertThat(findStoreList.get(0).getStoreCategory().getCode()).isEqualTo(100);
        assertThat(findStoreList.get(0).getStoreCategory().getName()).isEqualTo("편의점");
        assertThat(findStoreList.get(0).getName()).isEqualTo("B마트 인천점");
        assertThat(findStoreList.get(0).getOwner()).isEqualTo("점주");

        // verify
        verify(mockStoreRepository, times(1)).findAll();
        verify(mockStoreRepository, atLeastOnce()).findAll();
        verifyNoMoreInteractions(mockStoreRepository);

        InOrder inOrder = inOrder(mockStoreRepository);
        inOrder.verify(mockStoreRepository).findAll();
    }

    @Test
    @DisplayName("매장_상세_조회_테스트")
    void 매장_상세_조회_테스트() {
        // when
        when(mockStoreRepository.findById(store.getStoreId())).thenReturn(Optional.ofNullable(store));
        Store oneStore = mockStoreService.getStore(store.getStoreId());

        // then
        assertThat(oneStore.getStoreCategory().getCode()).isEqualTo(100);
        assertThat(oneStore.getStoreCategory().getName()).isEqualTo("편의점");
        assertThat(oneStore.getName()).isEqualTo("B마트 인천점");

        // verify
        verify(mockStoreRepository, times(1)).findById(store.getStoreId());
        verify(mockStoreRepository, atLeastOnce()).findById(store.getStoreId());
        verifyNoMoreInteractions(mockStoreRepository);

        InOrder inOrder = inOrder(mockStoreRepository);
        inOrder.verify(mockStoreRepository).findById(store.getStoreId());
    }

    @Test
    @DisplayName("매장_등록_예외_테스트")
    void 매장_등록_예외_테스트() {
        Store store = Store.builder()
                .name("B마트 서울 관악점")
                .description("B마트 서울 관악점")
                .address("서울특별시 관악구")
                .zipCode("123-456")
                .phoneNumber("01012345678")
                .build();

        assertThatThrownBy(() -> mockStoreService.createStore(store))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("매장_등록_테스트")
    void 매장_등록_테스트() {
        when(mockCategoryService.getCategory(firstCategory.getCategoryId())).thenReturn(firstCategory);
        Category saveCategory = mockCategoryService.getCategory(firstCategory.getCategoryId());

        // given
        Store store = Store.builder()
                .storeCategory(saveCategory)
                .name("B마트 서울 관악점")
                .description("B마트 서울 관악점")
                .owner("점주")
                .address("서울특별시 관악구")
                .zipCode("123-456")
                .phoneNumber("01012345678")
                .build();

        // when
        when(mockStoreRepository.save(store)).thenReturn(store);
        Store saveStore = mockStoreService.createStore(store);

        // then
        assertThat(saveStore.getStoreCategory().getCode()).isEqualTo(100);
        assertThat(saveStore.getStoreCategory().getName()).isEqualTo("편의점");
        assertThat(saveStore.getName()).isEqualTo("B마트 서울 관악점");

        // verify
        verify(mockStoreRepository, times(1)).save(store);
        verify(mockStoreRepository, atLeastOnce()).save(store);
        verifyNoMoreInteractions(mockStoreRepository);

        InOrder inOrder = inOrder(mockStoreRepository);
        inOrder.verify(mockStoreRepository).save(store);
    }

    @Test
    @DisplayName("매장_동일_카테고리_수정_테스트")
    void 매장_동일_카테고리_수정_테스트() {
        Store store = Store.builder()
                .storeCategory(firstCategory)
                .storeId(this.store.getStoreId())
                .name("B마트 서울 관악점")
                .description("B마트 서울 관악점")
                .owner("점주")
                .address("서울특별시 관악구")
                .zipCode("123-456")
                .phoneNumber("01012345678")
                .build();

        // when
        when(mockStoreRepository.findById(this.store.getStoreId())).thenReturn(Optional.of(this.store));
        Store updateStore = mockStoreService.updateStore(this.store.getStoreId(), store);

        // then
        assertThat(updateStore.getStoreCategory().getCode()).isEqualTo(100);
        assertThat(updateStore.getStoreCategory().getName()).isEqualTo("편의점");
        assertThat(updateStore.getName()).isEqualTo("B마트 서울 관악점");

        // verify
        verify(mockStoreRepository, times(1)).findById(this.store.getStoreId());
        verify(mockStoreRepository, atLeastOnce()).findById(this.store.getStoreId());
        verifyNoMoreInteractions(mockStoreRepository);

        InOrder inOrder = inOrder(mockStoreRepository);
        inOrder.verify(mockStoreRepository).findById(this.store.getStoreId());
    }

    @Test
    @DisplayName("매장_다른_카테고리_수정_테스트")
    void 매장_다른_카테고리_수정_테스트() {
        when(mockCategoryService.getCategory(secondCategory.getCategoryId())).thenReturn(secondCategory);
        Category updateCategory = mockCategoryService.getCategory(secondCategory.getCategoryId());

        Store store = Store.builder()
                .storeCategory(updateCategory)
                .storeId(this.store.getStoreId())
                .name("B마트 서울 관악점")
                .description("B마트 서울 관악점")
                .owner("점주")
                .address("서울특별시 관악구")
                .zipCode("123-456")
                .phoneNumber("01012345678")
                .build();

        // when
        when(mockStoreRepository.findById(store.getStoreId())).thenReturn(Optional.of(store));
        Store updateStore = mockStoreService.updateStore(this.store.getStoreId(), store);

        // then
        assertThat(updateStore.getStoreCategory().getCode()).isEqualTo(200);
        assertThat(updateStore.getStoreCategory().getName()).isEqualTo("매장");
        assertThat(updateStore.getName()).isEqualTo("B마트 서울 관악점");

        // verify
        verify(mockStoreRepository, times(1)).findById(this.store.getStoreId());
        verify(mockStoreRepository, atLeastOnce()).findById(this.store.getStoreId());
        verifyNoMoreInteractions(mockStoreRepository);

        InOrder inOrder = inOrder(mockStoreRepository);
        inOrder.verify(mockStoreRepository).findById(this.store.getStoreId());
    }

    @Test
    @DisplayName("매장_삭제_테스트")
    void 매장_삭제_테스트() {
        Long deleteStoreId = mockStoreService.deleteStore(store.getStoreId());
        assertThat(deleteStoreId).isEqualTo(store.getStoreId());
    }
}
