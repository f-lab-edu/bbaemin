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

import java.util.Arrays;
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
    private CategoryService categoryService;
    @Mock
    private StoreService storeService;
    @InjectMocks
    private ItemService itemService;

    private Item item;
    private Category itemCategory1;
    private Category itemCategory2;
    private Category storeCategory;
    private Store store1;
    private Store store2;

    @BeforeEach
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        // 카테고리 등록
        itemCategory1 = Category.builder()
                .categoryId(1L)
                .code(300)
                .name("과일")
                .description("과일")
                .parent(null)
                .build();
        itemCategory2 = Category.builder()
                .categoryId(2L)
                .code(400)
                .name("과자")
                .description("과자")
                .parent(null)
                .build();
        storeCategory = Category.builder()
                .categoryId(3L)
                .code(200)
                .name("편의점")
                .description("편의점")
                .parent(null)
                .build();

        // 매장 등록
        store1 = Store.builder()
                .storeId(1L)
                .storeCategory(storeCategory)
                .name("B마트 인천점")
                .description("B마트 인천점")
                .owner("점주")
                .address("인천광역시 계양구")
                .zipCode("123-456")
                .phoneNumber("010-1234-5678")
                .build();

        store2 = Store.builder()
                .storeId(2L)
                .storeCategory(storeCategory)
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
                .itemCategory(itemCategory1)
                .itemStore(store1)
                .name("청동사과")
                .description("싱싱한 청동사과")
                .price(2000)
                .quantity(999)
                .build();
    }

    @Test
    @DisplayName("아이템_리스트_조회")
    void 아이템_리스트_조회() {

        // given
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item));
        // when
        List<Item> itemList = itemService.listItem();

        // then
        assertThat(itemList.get(0).getItemCategory().getName()).isEqualTo("과일");
        assertThat(itemList.get(0).getItemStore().getName()).isEqualTo("B마트 인천점");
        assertThat(itemList.get(0).getName()).isEqualTo("청동사과");
        assertThat(itemList.get(0).getPrice()).isEqualTo(2000);

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

        // given
        when(itemRepository.findByItemId(item.getItemId())).thenReturn(Optional.of(item));
        // when
        Item getById = itemService.getItem(item.getItemId());

        // then
        assertThat(getById.getItemCategory().getName()).isEqualTo("과일");
        assertThat(getById.getItemStore().getName()).isEqualTo("B마트 인천점");
        assertThat(getById.getName()).isEqualTo("청동사과");
        assertThat(getById.getPrice()).isEqualTo(2000);

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
                .itemCategory(itemCategory1)
                .itemStore(store1)
                .name("복숭아")
                .description("복숭아")
                .price(3000)
                .quantity(999)
                .build();
        when(itemRepository.save(item)).thenReturn(item);
        // when
        Item saved = itemService.createItem(item);

        // then
        assertThat(saved.getItemCategory().getName()).isEqualTo("과일");
        assertThat(saved.getItemStore().getName()).isEqualTo("B마트 인천점");
        assertThat(saved.getName()).isEqualTo("복숭아");
        assertThat(saved.getDescription()).isEqualTo("복숭아");
        assertThat(saved.getPrice()).isEqualTo(3000);

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
        Item updatedItem1 = itemService.updateItem(item.getItemId(), "메론", "메론", 5000, 999, itemCategory1.getCategoryId(), store1.getStoreId());

        // then
        assertThat(updatedItem1.getItemCategory().getName()).isEqualTo("과일");
        assertThat(updatedItem1.getItemStore().getName()).isEqualTo("B마트 인천점");
        assertThat(updatedItem1.getName()).isEqualTo("메론");
        assertThat(updatedItem1.getDescription()).isEqualTo("메론");

        // verify
        verify(itemRepository, times(1)).findByItemId(item.getItemId());
        verify(itemRepository, atLeastOnce()).findByItemId(item.getItemId());
        verifyNoMoreInteractions(itemRepository);

        InOrder inOrder = inOrder(itemRepository);
        inOrder.verify(itemRepository).findByItemId(item.getItemId());

        // given
        when(categoryService.getCategory(itemCategory2.getCategoryId())).thenReturn(itemCategory2);
        when(storeService.getStore(store2.getStoreId())).thenReturn(store2);
        when(itemRepository.findByItemId(item.getItemId())).thenReturn(Optional.ofNullable(item));
        // when
        Item updatedItem2 = itemService.updateItem(item.getItemId(), "칙촉", "칙촉", 2500, 999, itemCategory2.getCategoryId(), store2.getStoreId());

        // then
        assertThat(updatedItem2.getItemCategory().getName()).isEqualTo("과자");
        assertThat(updatedItem2.getItemStore().getName()).isEqualTo("B마트 관악점");
        assertThat(updatedItem2.getName()).isEqualTo("칙촉");
        assertThat(updatedItem2.getPrice()).isEqualTo(2500);
        assertThat(updatedItem2.getQuantity()).isEqualTo(999);
    }

    @Test
    @DisplayName("아이템_삭제")
    void 아이템_삭제() {
        Long deletedItemId = itemService.deleteItem(item.getItemId());
        assertThat(deletedItemId).isEqualTo(item.getItemId());
    }
}
