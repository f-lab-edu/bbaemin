package org.bbaemin.user.order.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class _OrderServiceTest {
/*
    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderItemRepository orderItemRepository;
    @Mock
    CartItemService cartItemService;
    @Mock
    DeliveryFeeService deliveryFeeService;
    @Mock
    UserService userService;

    @Test
    void getOrderListByUserId() {
        User user = mock(User.class);
        doReturn(user)
                .when(userService).getUser(1L);
        List<Order> orderList = List.of(mock(Order.class));
        doReturn(orderList)
                .when(orderRepository).findByUser(user);

        assertEquals(orderList, orderService.getOrderListByUserId(1L));
    }

    @Test
    void getOrder() {
        Order order = mock(Order.class);
        doReturn(Optional.of(order))
                .when(orderRepository).findById(1L);

        assertEquals(order, orderService.getOrder(null, 1L));
    }

    // TODO - TEST
    @Test
    @Disabled
    void order() {
        fail();

        CartItem cartItem1 = mock(CartItem.class);
        doReturn(1000)
                .when(cartItem1).getOrderPrice();
        doReturn(5)
                .when(cartItem1).getOrderCount();

        CartItem cartItem2 = mock(CartItem.class);
        doReturn(500)
                .when(cartItem2).getOrderPrice();
        doReturn(10)
                .when(cartItem2).getOrderCount();

        doReturn(List.of(cartItem1, cartItem2))
                .when(cartItemService).getCartItemListByUserId(1L);
        doReturn(3000)
                .when(deliveryFeeService).getDeliveryFee(10000);

        Order saved = mock(Order.class);
        doReturn(saved)
                .when(orderRepository).save(any(Order.class));
        doNothing()
                .when(cartItemService).clear(1L);

        Order order = mock(Order.class);
        orderService.order(1L, order, Collections.emptyList());

        verify(cartItemService).getCartItemListByUserId(1L);
        verify(deliveryFeeService).getDeliveryFee(10000);
        verify(order).setOrderAmount(10000);
        verify(order).setDeliveryFee(3000);
        verify(order).setPaymentAmount(13000);
        verify(orderRepository).save(order);
        verify(orderItemRepository, times(2)).save(any(OrderItem.class));
        verify(cartItemService).clear(1L);
    }

    @Test
    void deleteOrder() {
        Order order = mock(Order.class);
        doReturn(Optional.of(order))
                .when(orderRepository).findById(1L);
        doNothing()
                .when(orderItemRepository).deleteByOrder(order);
        doNothing()
                .when(orderRepository).delete(order);

        orderService.deleteOrder(1L, 1L);
        verify(orderItemRepository, times(1)).deleteByOrder(order);
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void cancelOrder() {
        Order order = mock(Order.class);
        doReturn(Optional.of(order))
                .when(orderRepository).findById(1L);

        orderService.cancelOrder(1L, 1L);

        verify(order).setStatus(CANCEL_ORDER);
    }*/
}
