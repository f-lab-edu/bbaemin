package org.bbaemin.item.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.category.domain.CategoryEntity;
import org.bbaemin.category.repository.CategoryRepository;
import org.bbaemin.item.domain.ItemDTO;
import org.bbaemin.item.domain.ItemEntity;
import org.bbaemin.item.repository.ItemRepository;
import org.bbaemin.store.domain.StoreEntity;
import org.bbaemin.store.repository.StoreRepository;
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
@DisplayName("아이템 관련 Service Test")
class ItemServiceTest {

    private final EntityManager em;

    @Mock private ItemRepository mockItemRepository;
    @InjectMocks private ItemService mockItemService;

    @Mock private CategoryRepository mockCategoryRepository;
    @Mock private StoreRepository mockStoreRepository;

    private ItemEntity itemEntity;
    private CategoryEntity firstItemCategory;
    private CategoryEntity secondItemCategory;
    private CategoryEntity firstStoreCategory;
    private StoreEntity firstStoreEntity;
    private StoreEntity secondStoreEntity;

    @BeforeEach
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        // 카테고리 등록
        firstItemCategory = CategoryEntity.builder()
                .code(300)
                .name("과일")
                .description("과일")
                .useYn(true)
                .parent(null)
                .build();
        em.persist(firstItemCategory);

        secondItemCategory = CategoryEntity.builder()
                .code(400)
                .name("과자")
                .description("과자")
                .useYn(true)
                .parent(null)
                .build();
        em.persist(secondItemCategory);

        firstStoreCategory = CategoryEntity.builder()
                .code(200)
                .name("편의점")
                .description("편의점")
                .useYn(true)
                .parent(null)
                .build();
        em.persist(firstStoreCategory);

        // 매장 등록
        firstStoreEntity = StoreEntity.builder()
                .storeCategory(firstStoreCategory)
                .name("B마트 인천점")
                .description("B마트 인천점")
                .owner("점주")
                .address("인천광역시 계양구")
                .zipCode("123-456")
                .phoneNumber("01012345678")
                .useYn(true)
                .build();
        em.persist(firstStoreEntity);

        secondStoreEntity = StoreEntity.builder()
                .storeCategory(firstStoreCategory)
                .name("B마트 관악점")
                .description("B마트 관악점")
                .owner("점주")
                .address("서울특별시 관악구")
                .zipCode("456-789")
                .phoneNumber("01078912345")
                .useYn(true)
                .build();
        em.persist(secondStoreEntity);

        // 아이템 등록
        itemEntity = ItemEntity.builder()
                .itemCategory(firstItemCategory)
                .itemStore(firstStoreEntity)
                .name("청동사과")
                .description("싱싱한 청동사과")
                .price(2000)
                .quantity(999)
                .build();
        em.persist(itemEntity);
    }

    @Test
    @DisplayName("아이템_리스트_조회")
    void 아이템_리스트_조회() {
        List<ItemEntity> itemList = new ArrayList<>();
        itemList.add(itemEntity);
        // when
        when(mockItemRepository.findAll()).thenReturn(itemList);
        List<ItemDTO> findItemList = mockItemService.listItem();

        // then
        assertThat(findItemList.get(0).getCategoryName()).isEqualTo("과일");
        assertThat(findItemList.get(0).getStoreName()).isEqualTo("B마트 인천점");
        assertThat(findItemList.get(0).getName()).isEqualTo("청동사과");
        assertThat(findItemList.get(0).getPrice()).isEqualTo(2000);

        // verify
        verify(mockItemRepository, times(1)).findAll();
        verify(mockItemRepository, atLeastOnce()).findAll();
        verifyNoMoreInteractions(mockItemRepository);

        InOrder inOrder = inOrder(mockItemRepository);
        inOrder.verify(mockItemRepository).findAll();
    }

    @Test
    @DisplayName("아이템_상세_조회")
    void 아이템_상세_조회() {
        // when
        when(mockItemRepository.findByItemId(itemEntity.getItemId())).thenReturn(Optional.ofNullable(itemEntity));
        ItemDTO getItem = mockItemService.getItem(itemEntity.getItemId());

        // then
        assertThat(getItem.getCategoryName()).isEqualTo("과일");
        assertThat(getItem.getStoreName()).isEqualTo("B마트 인천점");
        assertThat(getItem.getName()).isEqualTo("청동사과");
        assertThat(getItem.getPrice()).isEqualTo(2000);

        // verify
        verify(mockItemRepository, times(1)).findByItemId(itemEntity.getItemId());
        verify(mockItemRepository, atLeastOnce()).findByItemId(itemEntity.getItemId());
        verifyNoMoreInteractions(mockItemRepository);

        InOrder inOrder = inOrder(mockItemRepository);
        inOrder.verify(mockItemRepository).findByItemId(itemEntity.getItemId());
    }

    @Test
    @DisplayName("아이템_등록")
    void 아이템_등록() {
        ItemEntity item = ItemEntity.builder()
                .itemCategory(firstItemCategory)
                .itemStore(firstStoreEntity)
                .name("복숭아")
                .description("복숭아")
                .price(3000)
                .quantity(999)
                .build();

        // when
        when(mockStoreRepository.findByStoreId(firstStoreEntity.getStoreId())).thenReturn(Optional.ofNullable(firstStoreEntity));
        when(mockCategoryRepository.findById(firstItemCategory.getCategoryId())).thenReturn(Optional.ofNullable(firstItemCategory));
        when(mockItemRepository.save(item)).thenReturn(item);
        ItemDTO saveItem = mockItemService.createItem(item);

        // then
        assertThat(saveItem.getCategoryName()).isEqualTo("과일");
        assertThat(saveItem.getStoreName()).isEqualTo("B마트 인천점");
        assertThat(saveItem.getName()).isEqualTo("복숭아");
        assertThat(saveItem.getDescription()).isEqualTo("복숭아");
        assertThat(saveItem.getPrice()).isEqualTo(3000);

        // verify
        verify(mockItemRepository, times(1)).save(item);
        verify(mockItemRepository, atLeastOnce()).save(item);
        verifyNoMoreInteractions(mockItemRepository);

        InOrder inOrder = inOrder(mockItemRepository);
        inOrder.verify(mockItemRepository).save(item);
    }

    @Test
    @DisplayName("아이템_수정")
    void 아이템_수정() {
        // 동일 매장 및 동일 카테고리
        ItemEntity firstItem = ItemEntity.builder()
                .itemId(itemEntity.getItemId())
                .itemCategory(firstItemCategory)
                .itemStore(firstStoreEntity)
                .name("메론")
                .description("메론")
                .price(5000)
                .quantity(999)
                .build();

        // when
        when(mockItemRepository.findByItemId(itemEntity.getItemId())).thenReturn(Optional.of(itemEntity));
        when(mockItemRepository.save(firstItem)).thenReturn(firstItem);
        ItemDTO updateItem = mockItemService.updateItem(itemEntity.getItemId(), firstItem);

        // then
        assertThat(updateItem.getCategoryName()).isEqualTo("과일");
        assertThat(updateItem.getStoreName()).isEqualTo("B마트 인천점");
        assertThat(updateItem.getName()).isEqualTo("메론");
        assertThat(updateItem.getDescription()).isEqualTo("메론");

        // verify
        verify(mockItemRepository, times(1)).findByItemId(itemEntity.getItemId());
        verify(mockItemRepository, atLeastOnce()).findByItemId(itemEntity.getItemId());
        verifyNoMoreInteractions(mockItemRepository);

        InOrder inOrder = inOrder(mockItemRepository);
        inOrder.verify(mockItemRepository).findByItemId(itemEntity.getItemId());

        // 다른 매장 및 다른 카테고리
        ItemEntity secondItem = ItemEntity.builder()
                .itemId(itemEntity.getItemId())
                .itemCategory(secondItemCategory)
                .itemStore(secondStoreEntity)
                .name("칙촉")
                .description("칙촉")
                .price(2500)
                .quantity(999)
                .build();

        // when
        when(mockCategoryRepository.findById(secondItemCategory.getCategoryId())).thenReturn(Optional.ofNullable(secondItemCategory));
        when(mockStoreRepository.findByStoreId(secondStoreEntity.getStoreId())).thenReturn(Optional.ofNullable(secondStoreEntity));
        when(mockItemRepository.findByItemId(itemEntity.getItemId())).thenReturn(Optional.ofNullable(itemEntity));
        ItemDTO secondUpdateItem = mockItemService.updateItem(itemEntity.getItemId(), secondItem);

        // then
        assertThat(secondUpdateItem.getCategoryName()).isEqualTo("과자");
        assertThat(secondUpdateItem.getStoreName()).isEqualTo("B마트 관악점");
        assertThat(secondUpdateItem.getName()).isEqualTo("칙촉");
        assertThat(secondUpdateItem.getPrice()).isEqualTo(2500);
        assertThat(secondUpdateItem.getQuantity()).isEqualTo(999);
    }

    @Test
    @DisplayName("아이템_삭제")
    void 아이템_삭제() {
        Long deleteItemId = mockItemService.deleteItem(itemEntity.getItemId());
        assertThat(deleteItemId).isEqualTo(itemEntity.getItemId());
    }
}
