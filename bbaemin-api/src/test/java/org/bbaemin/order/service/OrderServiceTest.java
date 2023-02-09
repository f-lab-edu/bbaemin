package org.bbaemin.order.service;

import org.bbaemin.cart.service.CartItemService;
import org.bbaemin.order.enums.OrderStatus;
import org.bbaemin.order.repository.OrderItemRepository;
import org.bbaemin.order.repository.OrderRepository;
import org.bbaemin.order.vo.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void getOrderListByUserId() {
        List<Order> orderList = List.of(mock(Order.class));
        // TODO - CHECK : Unfinished stubbing detected here
        doReturn(orderList).when(orderRepository.findByUserId(1L));

        assertEquals(orderList, orderService.getOrderListByUserId(1L));
    }

    @Test
    void getOrder() {
        Order order = mock(Order.class);
        // TODO - CHECK : Unfinished stubbing detected here
        doReturn(order).when(orderRepository.findById(1L));

        assertEquals(order, orderService.getOrder(null, 1L));
    }

    @Test
    void order() {

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
