package org.bbaemin.order.service;

import org.bbaemin.cart.repository.CartItemRepository;
import org.bbaemin.cart.service.CartService;
import org.bbaemin.cart.vo.CartItem;
import org.bbaemin.order.controller.response.OrderResponse;
import org.bbaemin.order.controller.response.OrderSummaryResponse;
import org.bbaemin.order.enums.OrderStatus;
import org.bbaemin.order.repository.OrderItemRepository;
import org.bbaemin.order.repository.OrderRepository;
import org.bbaemin.order.vo.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bbaemin.order.enums.OrderStatus.COMPLETE_ORDER;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderServiceIntegrationTest {

    CartItemRepository cartItemRepository = new CartItemRepository();
    CartService cartService = new CartService(cartItemRepository);

    OrderRepository orderRepository = new OrderRepository();
    OrderItemRepository orderItemRepository = new OrderItemRepository();
    OrderService orderService = new OrderService(orderRepository, orderItemRepository, cartService);

    @BeforeEach
    void init() {
        OrderRepository.clear();
        CartItemRepository.clear();
    }

    @Test
    void getOrderListByUserId() {
        // given
        CartItem cartItem = cartService.addItem(1L, 1L);
        Order order = orderService.order(1L, Order.builder()
                .userId(1L)
                .orderDate(LocalDateTime.now())
                .status(COMPLETE_ORDER)
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1111-2222")
                .email("user@email.com")
                .messageToRider("감사합니다")
                .discountCouponIdList(List.of(1L, 2L))
                .paymentMethod("신용/체크카드")
                .build());
        // when
        List<Order> orderList = orderService.getOrderListByUserId(1L);
        // then
        assertThat(orderList.size()).isEqualTo(1);
        Order saved = orderList.get(0);
        System.out.println(saved);
        System.out.println(new OrderSummaryResponse(saved));
        System.out.println(new OrderResponse(saved));
    }

    @Test
    void getOrder() {
        // given
        CartItem cartItem = cartService.addItem(1L, 1L);
        Order order = orderService.order(1L, Order.builder()
                .userId(1L)
                .orderDate(LocalDateTime.now())
                .status(COMPLETE_ORDER)
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1111-2222")
                .email("user@email.com")
                .messageToRider("감사합니다")
                .discountCouponIdList(List.of(1L, 2L))
                .paymentMethod("신용/체크카드")
                .build());
        // when
        Order saved = orderService.getOrder(1L, order.getOrderId());
        // then
        System.out.println(saved);
        System.out.println(new OrderSummaryResponse(saved));
        System.out.println(new OrderResponse(saved));
    }

    @Test
    void order() {
        // given
        CartItem cartItem = cartService.addItem(1L, 1L);
        // when
        Order order = orderService.order(1L, Order.builder()
                .userId(1L)
                .orderDate(LocalDateTime.now())
                .status(COMPLETE_ORDER)
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1111-2222")
                .email("user@email.com")
                .messageToRider("감사합니다")
                .discountCouponIdList(List.of(1L, 2L))
                .paymentMethod("신용/체크카드")
                .build());
        // then
        Order saved = orderRepository.findById(order.getOrderId());
        List<Order> orderList = orderRepository.findByUserId(1L);
        assertAll(
                () -> assertThat(saved).isEqualTo(order),
                () -> assertThat(orderList.get(0)).isEqualTo(order),

                () -> assertThat(cartService.getCart(1L).getCartItemList()).isEmpty()
        );
    }

    @Test
    void deleteOrder() {
        // given
        CartItem cartItem = cartService.addItem(1L, 1L);
        Order order = orderService.order(1L, Order.builder()
                .userId(1L)
                .orderDate(LocalDateTime.now())
                .status(COMPLETE_ORDER)
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1111-2222")
                .email("user@email.com")
                .messageToRider("감사합니다")
                .discountCouponIdList(List.of(1L, 2L))
                .paymentMethod("신용/체크카드")
                .build());
        // when
        orderService.deleteOrder(1L, order.getOrderId());
        // then
        assertThat(orderRepository.findByUserId(1L)).isEmpty();
    }

    @Test
    void cancelOrder() {
        // given
        CartItem cartItem = cartService.addItem(1L, 1L);
        Order order = orderService.order(1L, Order.builder()
                .userId(1L)
                .orderDate(LocalDateTime.now())
                .status(COMPLETE_ORDER)
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1111-2222")
                .email("user@email.com")
                .messageToRider("감사합니다")
                .discountCouponIdList(List.of(1L, 2L))
                .paymentMethod("신용/체크카드")
                .build());
        // when
        orderService.cancelOrder(1L, order.getOrderId());
        // then
        Order canceled = orderRepository.findById(order.getOrderId());
        assertThat(canceled.getStatus()).isEqualTo(OrderStatus.CANCEL_ORDER);
    }
}
