package org.bbaemin.user.cart.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class _CartItemServiceTest {
/*
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
        Mockito.doReturn(item)
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
        Mockito.doReturn(item)
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
    }*/
}
