package org.bbaemin.api.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bbaemin.api.cart.controller.CartController;
import org.bbaemin.api.cart.controller.request.CreateCartItemRequest;
import org.bbaemin.api.cart.controller.request.UpdateCartItemCountRequest;
import org.bbaemin.api.cart.service.CartItemService;
import org.bbaemin.api.cart.service.DeliveryFeeService;
import org.bbaemin.api.cart.vo.CartItem;
import org.bbaemin.api.item.vo.Item;
import org.bbaemin.api.user.vo.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartController.class,
        properties = {"spring.config.location=classpath:application-test.yml"})
class CartControllerTest {

    private final String BASE_URL = "/api/v1/cart";

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CartItemService cartItemService;
    @MockBean
    DeliveryFeeService deliveryFeeService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getCart() throws Exception {

        // given
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        User user = mock(User.class);
        CartItem cartItem1 = CartItem.builder()
                .cartItemId(1L)
                .item(item1)
                .itemName("item1")
                .itemDescription("item1_desc")
                .orderPrice(10000)
                .orderCount(2)
                .user(user)
                .build();
        CartItem cartItem2 = CartItem.builder()
                .cartItemId(2L)
                .item(item2)
                .itemName("item2")
                .itemDescription("item2_desc")
                .orderPrice(5000)
                .orderCount(2)
                .user(user)
                .build();
        List<CartItem> cartItemList = Arrays.asList(cartItem1, cartItem2);
        doReturn(cartItemList)
                .when(cartItemService).getCartItemListByUserId(1L);
        doReturn(3000)
                .when(deliveryFeeService).getDeliveryFee(cartItemList);
        // when
        mockMvc.perform(get(BASE_URL)
                        .param("userId", String.valueOf(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.cartItemList").value(Matchers.hasSize(2)))
                .andExpect(jsonPath("$.result.orderAmount").value(30000))
                .andExpect(jsonPath("$.result.deliveryFee").value(3000));
        // then
        verify(cartItemService).getCartItemListByUserId(1L);
        verify(deliveryFeeService).getDeliveryFee(cartItemList);
    }

    @Test
    void addItem() throws Exception {
        // given
        Item item2 = mock(Item.class);
        User user = mock(User.class);
        CartItem cartItem2 = CartItem.builder()
                .cartItemId(2L)
                .item(item2)
                .itemName("item2")
                .itemDescription("item2_desc")
                .orderPrice(5000)
                .orderCount(2)
                .user(user)
                .build();
        doReturn(cartItem2)
                .when(cartItemService).addItem(1L, 2L);

        // when
        CreateCartItemRequest createCartItemRequest = new CreateCartItemRequest(2L);
        mockMvc.perform(post(BASE_URL + "/items")
                        .param("userId", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCartItemRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CREATED"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.itemName").value("item2"))
                .andExpect(jsonPath("$.result.itemDescription").value("item2_desc"))
                .andExpect(jsonPath("$.result.orderPrice").value(5000))
                .andExpect(jsonPath("$.result.orderCount").value(2))
                .andExpect(jsonPath("$.result.totalOrderPrice").value(10000));
        // then
        verify(cartItemService).addItem(1L, 2L);
    }
/*
    @DisplayName("똑같은 아이템을 두 번 넣는 경우")
    @Test
    void add_existedItem() throws Exception {
        // given
        doThrow(new IllegalArgumentException("Duplicated"))
                .when(cartItemService).addItem(1L, 2L);

        // when
        CreateCartItemRequest createCartItemRequest = new CreateCartItemRequest(2L);
        mockMvc.perform(post(BASE_URL + "/items")
                        .param("userId", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCartItemRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("FAIL"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.httpStatus").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.result.cause").value("Duplicated"));
        // then
        verify(cartItemService).addItem(1L, 2L);
    }*/

    @Test
    void updateCount() throws Exception {
        // given
        Item item = mock(Item.class);
        User user = mock(User.class);
        CartItem cartItem = CartItem.builder()
                .cartItemId(1L)
                .item(item)
                .itemName("item1")
                .itemDescription("item1_desc")
                .orderPrice(10000)
                .orderCount(4)
                .user(user)
                .build();
        doReturn(cartItem)
                .when(cartItemService).updateCount(1L, 1L, 4);

        // when
        UpdateCartItemCountRequest updateCartItemCountRequest = new UpdateCartItemCountRequest(1L, 4);
        mockMvc.perform(patch(BASE_URL + "/items")
                        .param("userId", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCartItemCountRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.itemName").value("item1"))
                .andExpect(jsonPath("$.result.itemDescription").value("item1_desc"))
                .andExpect(jsonPath("$.result.orderPrice").value(10000))
                .andExpect(jsonPath("$.result.orderCount").value(4))
                .andExpect(jsonPath("$.result.totalOrderPrice").value(40000));
        // then
        verify(cartItemService).updateCount(1L, 1L, 4);
    }

    @Test
    void removeItem() throws Exception {
        // given
        doNothing()
                .when(cartItemService).removeItem(1L, 2L);

        // when
        mockMvc.perform(delete(BASE_URL + "/items/{cartItemId}", 2L)
                        .param("userId", String.valueOf(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").doesNotExist());
        // then
        verify(cartItemService).removeItem(1L, 2L);
    }

    @Test
    void removeItems() throws Exception {
        // given
        doNothing()
                .when(cartItemService).removeItems(1L, List.of(2L, 3L));

        // when
        mockMvc.perform(delete(BASE_URL + "/items")
                        .param("userId", String.valueOf(1L))
                        .param("cartItemIds", String.valueOf(2L), String.valueOf(3L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").doesNotExist());
        // then
        verify(cartItemService).removeItems(1L, List.of(2L, 3L));
    }

    @Test
    void clear() throws Exception {
        // given
        doNothing()
                .when(cartItemService).clear(1L);

        // when
        mockMvc.perform(delete(BASE_URL)
                        .param("userId", String.valueOf(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").doesNotExist());
        // then
        verify(cartItemService).clear(1L);
    }
}
