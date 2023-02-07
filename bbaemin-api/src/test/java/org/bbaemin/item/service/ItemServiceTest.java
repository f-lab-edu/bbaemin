package org.bbaemin.item.service;

import org.bbaemin.item.controller.request.CreateItemRequest;
import org.bbaemin.item.controller.request.UpdateItemRequest;
import org.bbaemin.item.controller.response.ItemImageResponse;
import org.bbaemin.item.controller.response.ItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemService mockItemService;

    private static List<ItemResponse> itemList = new ArrayList<>();
    private static Long itemId = 0L;

    void createItem() {
        itemList.add(
                ItemResponse.builder()
                        .itemId(++itemId)
                        .category("과일")
                        .store("B마트 인천점")
                        .name("청동사과")
                        .description("맛있고 싱싱한 청동사과")
                        .price(2000)
                        .quantity(999)
                        .itemImageResponse(
                                Arrays.asList(
                                        ItemImageResponse.builder()
                                                .url("https://image.thumbnail.com")
                                                .type("thumb-nail")
                                                .build(),
                                        ItemImageResponse.builder()
                                                .url("https://image.detail.com")
                                                .type("detail")
                                                .build()
                                )
                        )
                        .build());

        itemList.add(
                ItemResponse.builder()
                        .itemId(++itemId)
                        .category("유제품")
                        .store("B마트 인천점")
                        .name("서울우유 저지방우유 1000ml")
                        .description("서울 우유 저지방우유 1000ml")
                        .price(5000)
                        .quantity(999)
                        .itemImageResponse(
                                Arrays.asList(
                                        ItemImageResponse.builder()
                                                .url("https://image.thumbnail.com")
                                                .type("thumbnail")
                                                .build(),
                                        ItemImageResponse.builder()
                                                .url("https://image.detail.com")
                                                .type("detail")
                                                .build()
                                )
                        )
                        .build()
        );
    }

    @BeforeEach
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        createItem();
    }

    @Test
    @DisplayName("아이템 리스트 조회")
    void 아이템리스트조회() {
        // when
        when(mockItemService.listItem()).thenReturn(itemList);
        List<ItemResponse> itemList = mockItemService.listItem();

        // then
        assertThat(itemList.get(0).getCategory()).isEqualTo("과일");
        assertThat(itemList.get(0).getName()).isEqualTo("청동사과");
        assertThat(itemList.get(0).getPrice()).isEqualTo(2000);

        assertThat(itemList.get(1).getCategory()).isEqualTo("유제품");
        assertThat(itemList.get(1).getName()).isEqualTo("서울우유 저지방우유 1000ml");
        assertThat(itemList.get(1).getPrice()).isEqualTo(5000);

        // verify
        verify(mockItemService, times(1)).listItem();
        verify(mockItemService, atLeastOnce()).listItem();
        verifyNoMoreInteractions(mockItemService);

        InOrder inOrder = inOrder(mockItemService);
        inOrder.verify(mockItemService).listItem();
    }

    @Test
    @DisplayName("아이템 상세 조회")
    void 아이템상세조회() {
        // when
        when(mockItemService.getItem(1L)).thenReturn(itemList.get(0));
        ItemResponse getItem = mockItemService.getItem(1L);

        // then
        assertThat(getItem.getCategory()).isEqualTo("과일");
        assertThat(getItem.getName()).isEqualTo("청동사과");
        assertThat(getItem.getPrice()).isEqualTo(2000);

        // verify
        verify(mockItemService, times(1)).getItem(1L);
        verify(mockItemService, atLeastOnce()).getItem(1L);
        verifyNoMoreInteractions(mockItemService);

        InOrder inOrder = inOrder(mockItemService);
        inOrder.verify(mockItemService).getItem(1L);
    }

    @Test
    @DisplayName("아이템 등록")
    void 아이템등록() {
        CreateItemRequest createItemRequest = CreateItemRequest.builder()
                .categoryId(1L)
                .storeId(1L)
                .name("달달한 초코칩")
                .description("순도 100% 초콜릿 초코칩")
                .price(3000)
                .quantity(999)
                .itemImageRequest(
                        Arrays.asList(
                                CreateItemRequest.ItemImageRequest.builder()
                                        .itemId(3L)
                                        .url("https://image.thumbnail.com")
                                        .type("thumbnail")
                                        .build(),
                                CreateItemRequest.ItemImageRequest.builder()
                                        .itemId(3L)
                                        .url("https://image.detail.com")
                                        .type("detail")
                                        .build()
                        ))
                .build();

        ItemResponse createResponse = ItemResponse.builder()
                .itemId(itemId)
                .category("과자")
                .store("B마트 계양점")
                .name(createItemRequest.getName())
                .description(createItemRequest.getDescription())
                .price(createItemRequest.getPrice())
                .quantity(createItemRequest.getQuantity())
                .itemImageResponse(
                        Arrays.asList(
                                ItemImageResponse.builder()
                                        .url("https://image.thumbnail.com")
                                        .type("thumbnail")
                                        .build(),
                                ItemImageResponse.builder()
                                        .url("https://image.detail.com")
                                        .type("detail")
                                        .build()
                        )
                )
                .build();

        // when
        when(mockItemService.createItem(createItemRequest)).thenReturn(createResponse);
        ItemResponse getItem = mockItemService.createItem(createItemRequest);

        // then
        assertThat(getItem.getName()).isEqualTo("달달한 초코칩");
        assertThat(getItem.getDescription()).isEqualTo("순도 100% 초콜릿 초코칩");
        assertThat(getItem.getPrice()).isEqualTo(3000);

        // verify
        verify(mockItemService, times(1)).createItem(createItemRequest);
        verify(mockItemService, atLeastOnce()).createItem(createItemRequest);
        verifyNoMoreInteractions(mockItemService);

        InOrder inOrder = inOrder(mockItemService);
        inOrder.verify(mockItemService).createItem(createItemRequest);
    }

    @Test
    @DisplayName("아이템 수정")
    void 아이템수정() {
        UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .categoryId(999L)
                .storeId(999L)
                .name("닭고기 100g")
                .description("닭고기 100g")
                .price(5000)
                .quantity(999)
                .build();

        ItemResponse updateResponse = ItemResponse.builder()
                .itemId(itemId)
                .category("과자")
                .store("B마트 계양점")
                .name(updateItemRequest.getName())
                .description(updateItemRequest.getDescription())
                .price(updateItemRequest.getPrice())
                .quantity(updateItemRequest.getQuantity())
                .itemImageResponse(
                        Arrays.asList(
                                ItemImageResponse.builder()
                                        .url("https://image.thumbnail.com")
                                        .type("thumbnail")
                                        .build(),
                                ItemImageResponse.builder()
                                        .url("https://image.detail.com")
                                        .type("detail")
                                        .build()
                        )
                )
                .build();

        // when
        when(mockItemService.updateItem(2L, updateItemRequest)).thenReturn(updateResponse);
        ItemResponse updateItem = mockItemService.updateItem(2L, updateItemRequest);

        // then
        assertThat(updateItem.getName()).isEqualTo("닭고기 100g");
        assertThat(updateItem.getDescription()).isEqualTo("닭고기 100g");
    }

    @Test
    @DisplayName("아이템 삭제")
    void 아이템삭제() {
        // when
        when(mockItemService.deleteItem(2L)).thenReturn(2L);
        Long deleteItemId = mockItemService.deleteItem(2L);

        // then
        assertThat(deleteItemId).isEqualTo(2L);

        // verify
        verify(mockItemService, times(1)).deleteItem(2L);
        verify(mockItemService, atLeastOnce()).deleteItem(2L);
        verifyNoMoreInteractions(mockItemService);

        InOrder inOrder = inOrder(mockItemService);
        inOrder.verify(mockItemService).deleteItem(2L);
    }
}
