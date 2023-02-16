package org.bbaemin.store.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.domain.CategoryEntity;
import org.bbaemin.category.repository.CategoryRepository;
import org.bbaemin.store.domain.StoreDto;
import org.bbaemin.store.domain.StoreEntity;
import org.bbaemin.store.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
@DisplayName("매장 관련 Service Test")
class StoreServiceTest {

    private final EntityManager em;

    @Mock private StoreRepository mockStoreRepository;
    @InjectMocks private StoreService mockStoreService;

    @Mock private CategoryRepository mockCategoryRepository;

    private StoreEntity storeEntity;
    private CategoryEntity firstCategory;
    private CategoryEntity secondCategory;

    @BeforeEach
    @EventListener
    public void init() {
        firstCategory = CategoryEntity.builder()
                .code(100)
                .name("편의점")
                .description("최상위 편의점")
                .useYn(true)
                .parent(null)
                .build();

        em.persist(firstCategory);

        secondCategory = CategoryEntity.builder()
                .code(200)
                .name("매장")
                .description("최상위 매장")
                .useYn(true)
                .parent(null)
                .build();

        em.persist(secondCategory);


        storeEntity = StoreEntity.builder()
                .storeCategory(firstCategory)
                .name("B마트 인천점")
                .description("B마트 인천점")
                .owner("점주")
                .address("인천광역시 계양구")
                .zipCode("123-456")
                .phoneNumber("01012345678")
                .useYn(true)
                .build();

        em.persist(storeEntity);
    }

    @Test
    @DisplayName("매장_리스트_조회_테스트")
    void 매장_리스트_조회_테스트() {
        // given
        List<StoreEntity> storeList = new ArrayList<>();
        storeList.add(storeEntity);

        // when
        when(mockStoreRepository.findAll()).thenReturn(storeList);
        List<StoreDto> findStoreList = mockStoreService.listStore();

        // then
        assertThat(findStoreList.size()).isEqualTo(1);
        assertThat(findStoreList.get(0).getCategoryCode()).isEqualTo(100);
        assertThat(findStoreList.get(0).getCategoryName()).isEqualTo("편의점");
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
        when(mockStoreRepository.findByStoreId(storeEntity.getStoreId())).thenReturn(Optional.ofNullable(storeEntity));
        StoreDto oneStore = mockStoreService.getStore(storeEntity.getStoreId());

        // then
        assertThat(oneStore.getCategoryCode()).isEqualTo(100);
        assertThat(oneStore.getCategoryName()).isEqualTo("편의점");
        assertThat(oneStore.getName()).isEqualTo("B마트 인천점");

        // verify
        verify(mockStoreRepository, times(1)).findByStoreId(storeEntity.getStoreId());
        verify(mockStoreRepository, atLeastOnce()).findByStoreId(storeEntity.getStoreId());
        verifyNoMoreInteractions(mockStoreRepository);

        InOrder inOrder = inOrder(mockStoreRepository);
        inOrder.verify(mockStoreRepository).findByStoreId(storeEntity.getStoreId());
    }

    @Test
    @DisplayName("매장_등록_예외_테스트")
    void 매장_등록_예외_테스트() {
        StoreEntity store = StoreEntity.builder()
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
        // given
        StoreEntity store = StoreEntity.builder()
                .storeCategory(firstCategory)
                .name("B마트 서울 관악점")
                .description("B마트 서울 관악점")
                .owner("점주")
                .address("서울특별시 관악구")
                .zipCode("123-456")
                .phoneNumber("01012345678")
                .build();

        // when
        when(mockCategoryRepository.findById(firstCategory.getCategoryId())).thenReturn(Optional.ofNullable(firstCategory));
        when(mockStoreRepository.save(store)).thenReturn(store);
        StoreDto saveStore = mockStoreService.createStore(store);

        // then
        assertThat(saveStore.getCategoryCode()).isEqualTo(100);
        assertThat(saveStore.getCategoryName()).isEqualTo("편의점");
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
        StoreEntity store = StoreEntity.builder()
                .storeCategory(firstCategory)
                .storeId(storeEntity.getStoreId())
                .name("B마트 서울 관악점")
                .description("B마트 서울 관악점")
                .owner("점주")
                .address("서울특별시 관악구")
                .zipCode("123-456")
                .phoneNumber("01012345678")
                .build();

        // when
        when(mockStoreRepository.findById(storeEntity.getStoreId())).thenReturn(Optional.of(storeEntity));
        when(mockStoreRepository.save(store)).thenReturn(store);
        StoreDto updateStore = mockStoreService.updateStore(storeEntity.getStoreId(), store);

        // then
        assertThat(updateStore.getCategoryCode()).isEqualTo(100);
        assertThat(updateStore.getCategoryName()).isEqualTo("편의점");
        assertThat(updateStore.getName()).isEqualTo("B마트 서울 관악점");

        // verify
        verify(mockStoreRepository, times(1)).findById(storeEntity.getStoreId());
        verify(mockStoreRepository, atLeastOnce()).findById(storeEntity.getStoreId());
        verifyNoMoreInteractions(mockStoreRepository);

        InOrder inOrder = inOrder(mockStoreRepository);
        inOrder.verify(mockStoreRepository).findById(storeEntity.getStoreId());
    }

    @Test
    @DisplayName("매장_다른_카테고리_수정_테스트")
    void 매장_다른_카테고리_수정_테스트() {
        StoreEntity store = StoreEntity.builder()
                .storeCategory(secondCategory)
                .storeId(storeEntity.getStoreId())
                .name("B마트 서울 관악점")
                .description("B마트 서울 관악점")
                .owner("점주")
                .address("서울특별시 관악구")
                .zipCode("123-456")
                .phoneNumber("01012345678")
                .build();

        // when
        when(mockCategoryRepository.findById(secondCategory.getCategoryId())).thenReturn(Optional.ofNullable(secondCategory));
        when(mockStoreRepository.findById(store.getStoreId())).thenReturn(Optional.of(store));
        when(mockStoreRepository.save(store)).thenReturn(store);
        StoreDto updateStore = mockStoreService.updateStore(storeEntity.getStoreId(), store);

        // then
        assertThat(updateStore.getCategoryCode()).isEqualTo(200);
        assertThat(updateStore.getCategoryName()).isEqualTo("매장");
        assertThat(updateStore.getName()).isEqualTo("B마트 서울 관악점");

        // verify
        verify(mockStoreRepository, times(1)).findById(storeEntity.getStoreId());
        verify(mockStoreRepository, atLeastOnce()).findById(storeEntity.getStoreId());
        verifyNoMoreInteractions(mockStoreRepository);

        InOrder inOrder = inOrder(mockStoreRepository);
        inOrder.verify(mockStoreRepository).findById(storeEntity.getStoreId());
    }

    @Test
    @DisplayName("매장_삭제_테스트")
    void 매장_삭제_테스트() {
        Long deleteStoreId = mockStoreService.deleteStore(storeEntity.getStoreId());
        assertThat(deleteStoreId).isEqualTo(storeEntity.getStoreId());
    }
}
