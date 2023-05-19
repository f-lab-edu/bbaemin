package org.bbaemin.user.order.service;

import org.bbaemin.user.cart.service.CartItemService;
import org.bbaemin.user.cart.vo.CartItem;
import org.bbaemin.user.order.enums.OrderStatus;
import org.bbaemin.user.order.repository.OrderItemRepository;
import org.bbaemin.user.order.repository.OrderRepository;
import org.bbaemin.user.order.vo.Order;
import org.bbaemin.user.order.vo.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.bbaemin.user.order.enums.OrderStatus.COMPLETE_ORDER;
import static org.bbaemin.user.order.enums.PaymentMethod.CARD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Spy
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

    private Order order = Order.builder()
            .orderId(1L)
            .userId(1L)
            .orderDate(LocalDateTime.of(2023, 2, 23, 10, 20, 35))
            .status(COMPLETE_ORDER)
            .orderAmount(20000)
            .deliveryFee(3000)
            .paymentAmount(20000)
            .paymentMethod(CARD)
            .deliveryAddress("서울시 강동구")
            .phoneNumber("010-1234-5678")
            .email("user@email.com")
            .messageToRider("감사합니다!")
            .build();

    private OrderItem orderItem = OrderItem.builder()
            .orderItemId(1L)
            .orderId(1L)
            .itemId(1L)
            .itemName("item_name")
            .itemDescription("item_desc")
            .orderPrice(10000)
            .orderCount(2)
            .build();

    @Test
    void getOrderListByUserId() {
        doReturn(Flux.just(order))
                .when(orderRepository).findByUserId(1L);
        Flux<Order> orderFlux = orderService.getOrderListByUserId(1L);
        StepVerifier.create(orderFlux.log())
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void getOrder() {
        doReturn(Mono.just(order))
                .when(orderRepository).findById(1L);
        StepVerifier.create(orderService.getOrder(1L, 1L))
                .expectNextMatches(o -> o.getEmail().equals("user@email.com"))
                .expectComplete()
                .verify();
    }

    @Test
    void getOrder_when_not_exist() {
        doReturn(Mono.empty())
                .when(orderRepository).findById(1L);
        StepVerifier.create(orderService.getOrder(1L, 1L))
                .expectErrorMatches(e -> e instanceof NoSuchElementException
                        && e.getMessage().equals("orderId : 1"))
                .verify();
    }

    @Test
    void getOrderItemListByOrderId() {
        doReturn(Flux.just(mock(OrderItem.class), mock(OrderItem.class), mock(OrderItem.class)))
                .when(orderItemRepository).findByOrderId(1L);
        StepVerifier.create(orderService.getOrderItemListByOrderId(1L))
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    void getOrderItem() {
        doReturn(Mono.just(orderItem))
                .when(orderItemRepository).findById(1L);
        StepVerifier.create(orderService.getOrderItem(1L))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void getOrderItem_when_not_exist() {
        doReturn(Mono.empty())
                .when(orderItemRepository).findById(1L);
        StepVerifier.create(orderService.getOrderItem(1L))
                .expectErrorMatches(e -> e instanceof NoSuchElementException
                        && e.getMessage().equals("orderItemId : 1"))
                .verify();
    }

    // TODO - CHECK
    @Test
    void order() {
        CartItem cartItem1 = CartItem.builder()
                .cartItemId(1L)
                .itemId(1L)
                .itemName("item1")
                .itemDescription("item1_desc")
                .orderPrice(10000)
                .orderCount(2)
                .userId(1L)
                .build();
        CartItem cartItem2 = CartItem.builder()
                .cartItemId(2L)
                .itemId(2L)
                .itemName("item2")
                .itemDescription("item2_desc")
                .orderPrice(5000)
                .orderCount(2)
                .userId(1L)
                .build();

        Order order = Order.builder()
                .orderDate(LocalDateTime.of(2023, 2, 23, 10, 20, 35))
                .status(COMPLETE_ORDER)
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1234-5678")
                .email("user@email.com")
                .messageToRider("감사합니다!")
                .paymentMethod(CARD)
                .build();

        List<Long> discountCouponIdList = Arrays.asList(1L, 2L);

        // given
        doReturn(Flux.just(cartItem1, cartItem2))
                .when(cartItemService).getCartItemListByUserId(1L);
        doReturn(Mono.just(3000))
                .when(deliveryFeeService).getDeliveryFee(30000);
        doReturn(Mono.just(5000))
                .when(orderService).applyCouponList(30000, discountCouponIdList);
        doReturn(Mono.just(order))
                .when(orderRepository).save(order);

        StepVerifier.create(orderService.order(1L, order, discountCouponIdList))
                .expectNextMatches(o ->
                        o.getOrderAmount() == 30000
                        && o.getDeliveryFee() == 3000
                        && o.getPaymentAmount() == 28000
                        && o.getUserId().equals(1L))
                .expectComplete()
                .verify();
    }

    @Test
    void deleteOrder() {

        // given
        doReturn(Mono.empty())
                .when(orderItemRepository).deleteByOrderId(1L);
        doReturn(Mono.empty())
                .when(orderRepository).deleteById(1L);

        // when
        StepVerifier.create(orderService.deleteOrder(1L, 1L))
                .expectComplete()
                .verify();

    }

    @Test
    void cancelOrder() {
        // given
        doReturn(Mono.just(order))
                .when(orderRepository).findById(1L);
        doReturn(Mono.just(order))
                .when(orderRepository).save(any(Order.class));

        // when
        // then
        StepVerifier.create(orderService.cancelOrder(1L, 1L))
                .expectNextMatches(canceled ->
                        canceled.getStatus().equals(OrderStatus.CANCEL_ORDER.name()))
                .expectComplete()
                .verify();
    }

    @Test
    void cancelOrder_if_not_exists() {
        // given
        doReturn(Mono.error(new NoSuchElementException("orderId : " + 1L)))
                .when(orderRepository).findById(1L);

        // when
        // then
        StepVerifier.create(orderService.cancelOrder(1L, 1L))
                .expectErrorMatches(th -> th instanceof NoSuchElementException)
                .verify();
        verify(orderRepository, times(0)).save(any(Order.class));
    }
}
