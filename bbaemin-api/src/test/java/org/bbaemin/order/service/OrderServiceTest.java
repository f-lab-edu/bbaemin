package org.bbaemin.order.service;

import org.bbaemin.cart.service.CartItemService;
import org.bbaemin.cart.service.DeliveryFeeService;
import org.bbaemin.cart.vo.CartItem;
import org.bbaemin.order.enums.OrderStatus;
import org.bbaemin.order.repository.OrderItemRepository;
import org.bbaemin.order.repository.OrderRepository;
import org.bbaemin.order.vo.Order;
import org.bbaemin.order.vo.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Test
    void getOrderListByUserId() {
        List<Order> orderList = List.of(mock(Order.class));
        doReturn(orderList)
                .when(orderRepository).findByUserId(1L);

        assertEquals(orderList, orderService.getOrderListByUserId(1L));
    }

    @Test
    void getOrder() {
        Order order = mock(Order.class);
        doReturn(order)
                .when(orderRepository).findById(1L);

        assertEquals(order, orderService.getOrder(null, 1L));
    }

    @Test
    void order() {
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
        doReturn(1L).when(saved).getOrderId();
        doReturn(saved).when(orderRepository).insert(any(Order.class));
        doNothing().when(cartItemService).clear(1L);

        Order order = mock(Order.class);
        orderService.order(1L, order, Collections.emptyList());

        verify(cartItemService).getCartItemListByUserId(1L);
        verify(deliveryFeeService).getDeliveryFee(10000);
        verify(order).setOrderAmount(10000);
        verify(order).setDeliveryFee(3000);
        verify(order).setPaymentAmount(13000);
        verify(orderRepository).insert(order);
        verify(orderItemRepository, times(2)).insert(any(OrderItem.class));
        verify(cartItemService).clear(1L);
    }

    @Test
    void deleteOrder() {
        doNothing().when(orderRepository).deleteById(anyLong());

        orderService.deleteOrder(1L, 1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void cancelOrder() {
        Order order = mock(Order.class);
        doReturn(order)
                .when(orderRepository).findById(1L);
        doReturn(order).when(orderRepository).update(order);

        orderService.cancelOrder(1L, 1L);

        verify(order).setStatus(OrderStatus.CANCEL_ORDER);
        verify(orderRepository, times(1)).update(order);
    }
}
