package org.bbaemin.api.order.service;

import org.bbaemin.api.cart.service.CartItemService;
import org.bbaemin.api.cart.service.DeliveryFeeService;
import org.bbaemin.api.cart.vo.CartItem;
import org.bbaemin.api.order.repository.OrderItemRepository;
import org.bbaemin.api.order.repository.OrderRepository;
import org.bbaemin.api.order.service.OrderService;
import org.bbaemin.api.order.vo.Order;
import org.bbaemin.api.order.vo.OrderItem;
import org.bbaemin.api.user.service.UserService;
import org.bbaemin.api.user.vo.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.bbaemin.api.order.enums.OrderStatus.CANCEL_ORDER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

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
    }
}
