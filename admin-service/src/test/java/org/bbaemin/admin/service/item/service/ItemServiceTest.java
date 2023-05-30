package org.bbaemin.admin.service.item.service;

import org.bbaemin.admin.category.service.CategoryService;
import org.bbaemin.admin.category.vo.Category;
import org.bbaemin.admin.delivery.service.StoreService;
import org.bbaemin.admin.delivery.vo.Store;
import org.bbaemin.admin.item.repository.ItemRepository;
import org.bbaemin.admin.item.service.ItemService;
import org.bbaemin.admin.item.vo.Item;
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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("아이템 관련 Service Test")
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private CategoryService mockCategoryService;
    @Mock
    private StoreService mockStoreService;
    @InjectMocks
    private ItemService itemService;

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
                .parent(null)
                .build();

        secondItemCategory = Category.builder()
                .categoryId(2L)
                .code(400)
                .name("과자")
                .description("과자")
                .parent(null)
                .build();

        firstStoreCategory = Category.builder()
                .categoryId(3L)
                .code(200)
                .name("편의점")
                .description("편의점")
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
                .phoneNumber("010-1234-5678")
                .build();

        secondStoreEntity = Store.builder()
                .storeId(2L)
                .storeCategory(firstStoreCategory)
                .name("B마트 관악점")
                .description("B마트 관악점")
                .owner("점주")
                .address("서울특별시 관악구")
                .zipCode("456-789")
                .phoneNumber("010-7891-2345")
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
        when(itemRepository.findAll()).thenReturn(itemList);
        List<Item> findItemList = itemService.listItem();

        // then
        assertThat(findItemList.get(0).getItemCategory().getName()).isEqualTo("과일");
        assertThat(findItemList.get(0).getItemStore().getName()).isEqualTo("B마트 인천점");
        assertThat(findItemList.get(0).getName()).isEqualTo("청동사과");
        assertThat(findItemList.get(0).getPrice()).isEqualTo(2000);

        // verify
        verify(itemRepository, times(1)).findAll();
        verify(itemRepository, atLeastOnce()).findAll();
        verifyNoMoreInteractions(itemRepository);

        InOrder inOrder = inOrder(itemRepository);
        inOrder.verify(itemRepository).findAll();
    }

    @Test
    @DisplayName("아이템_상세_조회")
    void 아이템_상세_조회() {
        // when
        when(itemRepository.findByItemId(item.getItemId())).thenReturn(Optional.ofNullable(item));
        Item getItem = itemService.getItem(item.getItemId());

        // then
        assertThat(getItem.getItemCategory().getName()).isEqualTo("과일");
        assertThat(getItem.getItemStore().getName()).isEqualTo("B마트 인천점");
        assertThat(getItem.getName()).isEqualTo("청동사과");
        assertThat(getItem.getPrice()).isEqualTo(2000);

        // verify
        verify(itemRepository, times(1)).findByItemId(item.getItemId());
        verify(itemRepository, atLeastOnce()).findByItemId(item.getItemId());
        verifyNoMoreInteractions(itemRepository);

        InOrder inOrder = inOrder(itemRepository);
        inOrder.verify(itemRepository).findByItemId(item.getItemId());
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
        when(itemRepository.save(item)).thenReturn(item);
        Item saveItem = itemService.createItem(item);

        // then
        assertThat(saveItem.getItemCategory().getName()).isEqualTo("과일");
        assertThat(saveItem.getItemStore().getName()).isEqualTo("B마트 인천점");
        assertThat(saveItem.getName()).isEqualTo("복숭아");
        assertThat(saveItem.getDescription()).isEqualTo("복숭아");
        assertThat(saveItem.getPrice()).isEqualTo(3000);

        // verify
        verify(itemRepository, times(1)).save(item);
        verify(itemRepository, atLeastOnce()).save(item);
        verifyNoMoreInteractions(itemRepository);

        InOrder inOrder = inOrder(itemRepository);
        inOrder.verify(itemRepository).save(item);
    }

    @Test
    @DisplayName("아이템_수정")
    void 아이템_수정() {

        // given
        when(itemRepository.findByItemId(item.getItemId())).thenReturn(Optional.of(item));
        // when
        Item updateItem = itemService.updateItem(item.getItemId(), "메론", "메론", 5000, 999, firstItemCategory.getCategoryId(), firstStoreEntity.getStoreId());

        // then
        assertThat(updateItem.getItemCategory().getName()).isEqualTo("과일");
        assertThat(updateItem.getItemStore().getName()).isEqualTo("B마트 인천점");
        assertThat(updateItem.getName()).isEqualTo("메론");
        assertThat(updateItem.getDescription()).isEqualTo("메론");

        // verify
        verify(itemRepository, times(1)).findByItemId(item.getItemId());
        verify(itemRepository, atLeastOnce()).findByItemId(item.getItemId());
        verifyNoMoreInteractions(itemRepository);

        InOrder inOrder = inOrder(itemRepository);
        inOrder.verify(itemRepository).findByItemId(item.getItemId());

        // given
        when(mockCategoryService.getCategory(secondItemCategory.getCategoryId())).thenReturn(secondItemCategory);
        when(mockStoreService.getStore(secondStoreEntity.getStoreId())).thenReturn(secondStoreEntity);
        when(itemRepository.findByItemId(item.getItemId())).thenReturn(Optional.ofNullable(item));
        // when
        Item secondUpdateItem = itemService.updateItem(item.getItemId(), "칙촉", "칙촉", 2500, 999, secondItemCategory.getCategoryId(), secondStoreEntity.getStoreId());

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
        Long deleteItemId = itemService.deleteItem(item.getItemId());
        assertThat(deleteItemId).isEqualTo(item.getItemId());
    }
}
