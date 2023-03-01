package org.bbaemin.api.cart.service;

import org.bbaemin.api.cart.repository.CartItemRepository;
import org.bbaemin.api.cart.service.CartItemService;
import org.bbaemin.api.cart.vo.CartItem;
import org.bbaemin.api.item.service.ItemService;
import org.bbaemin.api.item.vo.Item;
import org.bbaemin.api.user.service.UserService;
import org.bbaemin.api.user.vo.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @InjectMocks
    CartItemService cartItemService;
    @Mock
    CartItemRepository cartItemRepository;
    @Mock
    ItemService itemService;
    @Mock
    UserService userService;

    @Test
    void getCartItemList() {
        User user = mock(User.class);
        doReturn(user)
                .when(userService).getUser(1L);
        CartItem cartItem = mock(CartItem.class);
        List<CartItem> cartItemList = List.of(cartItem);
        doReturn(cartItemList)
                .when(cartItemRepository).findByUser(user);

        assertEquals(cartItemList, cartItemService.getCartItemListByUserId(1L));
    }

    @Test
    void addItem() {
        // given
        User user = mock(User.class);
        doReturn(user)
                .when(userService).getUser(1L);
        Item item = mock(Item.class);
        doReturn(item)
                .when(itemService).getItem(2L);
        doReturn(Optional.empty())
                .when(cartItemRepository).findByUserAndItem(user, item);
        // when
        cartItemService.addItem(1L, 2L);
        // then
        verify(cartItemRepository).save(any(CartItem.class));
    }

    @DisplayName("똑같은 아이템을 두 번 넣는 경우")
    @Test
    void add_existedItem() {
        // given
        User user = mock(User.class);
        doReturn(user)
                .when(userService).getUser(1L);
        Item item = mock(Item.class);
        doReturn(item)
                .when(itemService).getItem(2L);
        CartItem cartItem = mock(CartItem.class);
        doReturn(1)
                .when(cartItem).getOrderCount();
        doReturn(Optional.of(cartItem))
                .when(cartItemRepository).findByUserAndItem(user, item);

        // when
        cartItemService.addItem(1L, 2L);
        // then
        verify(cartItem).setOrderCount(2);
    }

    @Test
    void updateCount() {
        CartItem cartItem = mock(CartItem.class);
        doReturn(Optional.of(cartItem))
                .when(cartItemRepository).findById(1L);

        cartItemService.updateCount(1L, 1L, 2);
        verify(cartItem).setOrderCount(2);
    }

    @Test
    void removeItem() {
        doNothing()
                .when(cartItemRepository).deleteById(2L);

        cartItemService.removeItem(1L, 2L);
        verify(cartItemRepository).deleteById(2L);
    }

    @Test
    void removeItems() {
        doNothing()
                .when(cartItemRepository).deleteAllById(List.of(1L, 2L));

        cartItemService.removeItems(1L, List.of(1L, 2L));
        verify(cartItemRepository).deleteAllById(List.of(1L, 2L));
    }

    @Test
    void clear() {
        User user = mock(User.class);
        doReturn(user)
                .when(userService).getUser(1L);
        doNothing()
                .when(cartItemRepository).deleteByUser(user);

        cartItemService.clear(1L);
        verify(cartItemRepository).deleteByUser(user);
    }
}
