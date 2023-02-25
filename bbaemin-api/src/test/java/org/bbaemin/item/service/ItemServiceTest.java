package org.bbaemin.item.service;

import org.bbaemin.category.domain.Category;
import org.bbaemin.category.service.CategoryService;
import org.bbaemin.item.vo.Item;
import org.bbaemin.item.repository.ItemRepository;
import org.bbaemin.store.domain.Store;
import org.bbaemin.store.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("아이템 관련 Service Test")
class ItemServiceTest {


    @Mock private ItemRepository mockItemRepository;
    @InjectMocks private ItemService mockItemService;

    @Mock private CategoryService mockCategoryService;
    @Mock private StoreService mockStoreService;

    private Item item;
    private Category firstItemCategory;
    private Category secondItemCategory;
    private Category firstStoreCategory;
    private Store firstStoreEntity;
    private Store secondStoreEntity;

    @BeforeEach
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        // 카테고리 등록
        firstItemCategory = Category.builder()
                .categoryId(1L)
                .code(300)
                .name("과일")
                .description("과일")
                .useYn(true)
                .parent(null)
                .build();

        secondItemCategory = Category.builder()
                .categoryId(2L)
                .code(400)
                .name("과자")
                .description("과자")
                .useYn(true)
                .parent(null)
                .build();

        firstStoreCategory = Category.builder()
                .categoryId(3L)
                .code(200)
                .name("편의점")
                .description("편의점")
                .useYn(true)
                .parent(null)
                .build();

        // 매장 등록
        firstStoreEntity = Store.builder()
                .storeId(1L)
                .storeCategory(firstStoreCategory)
                .name("B마트 인천점")
                .description("B마트 인천점")
                .owner("점주")
                .address("인천광역시 계양구")
                .zipCode("123-456")
                .phoneNumber("01012345678")
                .useYn(true)
                .build();

        secondStoreEntity = Store.builder()
                .storeId(2L)
                .storeCategory(firstStoreCategory)
                .name("B마트 관악점")
                .description("B마트 관악점")
                .owner("점주")
                .address("서울특별시 관악구")
                .zipCode("456-789")
                .phoneNumber("01078912345")
                .useYn(true)
                .build();

        // 아이템 등록
        item = Item.builder()
                .itemId(1L)
                .itemCategory(firstItemCategory)
                .itemStore(firstStoreEntity)
                .name("청동사과")
                .description("싱싱한 청동사과")
                .price(2000)
                .quantity(999)
                .build();
    }

    @Test
    @DisplayName("아이템_리스트_조회")
    void 아이템_리스트_조회() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        // when
        when(mockItemRepository.findAll()).thenReturn(itemList);
        List<Item> findItemList = mockItemService.listItem();

        // then
        assertThat(findItemList.get(0).getItemCategory().getName()).isEqualTo("과일");
        assertThat(findItemList.get(0).getItemStore().getName()).isEqualTo("B마트 인천점");
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
        when(mockItemRepository.findByItemId(item.getItemId())).thenReturn(Optional.ofNullable(item));
        Item getItem = mockItemService.getItem(item.getItemId());

        // then
        assertThat(getItem.getItemCategory().getName()).isEqualTo("과일");
        assertThat(getItem.getItemStore().getName()).isEqualTo("B마트 인천점");
        assertThat(getItem.getName()).isEqualTo("청동사과");
        assertThat(getItem.getPrice()).isEqualTo(2000);

        // verify
        verify(mockItemRepository, times(1)).findByItemId(item.getItemId());
        verify(mockItemRepository, atLeastOnce()).findByItemId(item.getItemId());
        verifyNoMoreInteractions(mockItemRepository);

        InOrder inOrder = inOrder(mockItemRepository);
        inOrder.verify(mockItemRepository).findByItemId(item.getItemId());
    }

    @Test
    @DisplayName("아이템_등록")
    void 아이템_등록() {
        Item item = Item.builder()
                .itemCategory(firstItemCategory)
                .itemStore(firstStoreEntity)
                .name("복숭아")
                .description("복숭아")
                .price(3000)
                .quantity(999)
                .build();

        // when
        when(mockStoreService.getStore(firstStoreEntity.getStoreId())).thenReturn(firstStoreEntity);
        when(mockCategoryService.getCategory(firstItemCategory.getCategoryId())).thenReturn(firstItemCategory);
        when(mockItemRepository.save(item)).thenReturn(item);
        Item saveItem = mockItemService.createItem(item);

        // then
        assertThat(saveItem.getItemCategory().getName()).isEqualTo("과일");
        assertThat(saveItem.getItemStore().getName()).isEqualTo("B마트 인천점");
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
        Item firstItem = Item.builder()
                .itemId(item.getItemId())
                .itemCategory(firstItemCategory)
                .itemStore(firstStoreEntity)
                .name("메론")
                .description("메론")
                .price(5000)
                .quantity(999)
                .build();

        // when
        when(mockItemRepository.findByItemId(item.getItemId())).thenReturn(Optional.of(item));
        Item updateItem = mockItemService.updateItem(item.getItemId(), firstItem);

        // then
        assertThat(updateItem.getItemCategory().getName()).isEqualTo("과일");
        assertThat(updateItem.getItemStore().getName()).isEqualTo("B마트 인천점");
        assertThat(updateItem.getName()).isEqualTo("메론");
        assertThat(updateItem.getDescription()).isEqualTo("메론");

        // verify
        verify(mockItemRepository, times(1)).findByItemId(item.getItemId());
        verify(mockItemRepository, atLeastOnce()).findByItemId(item.getItemId());
        verifyNoMoreInteractions(mockItemRepository);

        InOrder inOrder = inOrder(mockItemRepository);
        inOrder.verify(mockItemRepository).findByItemId(item.getItemId());

        // 다른 매장 및 다른 카테고리
        Item secondItem = Item.builder()
                .itemId(item.getItemId())
                .itemCategory(secondItemCategory)
                .itemStore(secondStoreEntity)
                .name("칙촉")
                .description("칙촉")
                .price(2500)
                .quantity(999)
                .build();

        // when
        when(mockCategoryService.getCategory(secondItemCategory.getCategoryId())).thenReturn(secondItemCategory);
        when(mockStoreService.getStore(secondStoreEntity.getStoreId())).thenReturn(secondStoreEntity);
        when(mockItemRepository.findByItemId(item.getItemId())).thenReturn(Optional.ofNullable(item));
        Item secondUpdateItem = mockItemService.updateItem(item.getItemId(), secondItem);

        // then
        assertThat(secondUpdateItem.getItemCategory().getName()).isEqualTo("과자");
        assertThat(secondUpdateItem.getItemStore().getName()).isEqualTo("B마트 관악점");
        assertThat(secondUpdateItem.getName()).isEqualTo("칙촉");
        assertThat(secondUpdateItem.getPrice()).isEqualTo(2500);
        assertThat(secondUpdateItem.getQuantity()).isEqualTo(999);
    }

    @Test
    @DisplayName("아이템_삭제")
    void 아이템_삭제() {
        Long deleteItemId = mockItemService.deleteItem(item.getItemId());
        assertThat(deleteItemId).isEqualTo(item.getItemId());
    }
}
