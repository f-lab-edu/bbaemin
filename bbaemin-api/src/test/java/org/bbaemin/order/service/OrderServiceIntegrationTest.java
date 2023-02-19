package org.bbaemin.order.service;

import org.bbaemin.cart.repository.CartItemRepository;
import org.bbaemin.cart.service.CartItemService;
import org.bbaemin.cart.service.DeliveryFeeService;
import org.bbaemin.cart.vo.CartItem;
import org.bbaemin.order.controller.response.OrderResponse;
import org.bbaemin.order.controller.response.OrderSummaryResponse;
import org.bbaemin.order.enums.OrderStatus;
import org.bbaemin.order.repository.OrderItemRepository;
import org.bbaemin.order.repository.OrderRepository;
import org.bbaemin.order.vo.Order;
import org.bbaemin.order.vo.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bbaemin.order.enums.OrderStatus.COMPLETE_ORDER;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest(properties = {"spring.config.location=classpath:application-test.yml"})
class OrderServiceIntegrationTest {

    @Autowired
    DeliveryFeeService deliveryFeeService;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderService orderService;

    @BeforeEach
    void init() {
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        cartItemRepository.clear();
    }

    @Test
    void getOrderListByUserId() {
        // given
        CartItem cartItem = cartItemService.addItem(1L, 1L);
        Order order = orderService.order(1L, Order.builder()
                        .userId(1L)
                        .orderDate(LocalDateTime.now())
                        .status(COMPLETE_ORDER)
                        .deliveryAddress("서울시 강동구")
                        .phoneNumber("010-1111-2222")
                        .email("user@email.com")
                        .messageToRider("감사합니다")
                        .paymentMethod("신용/체크카드")
                        .build(),
                // discountCouponIdList
                List.of(1L, 2L));
        // when
        List<Order> orderList = orderService.getOrderListByUserId(1L);
        // then
        assertThat(orderList.size()).isEqualTo(1);
        Order saved = orderList.get(0);
        List<OrderItem> orderItemList = orderService.getOrderItemListByOrder(saved);
        System.out.println(saved);
        System.out.println(new OrderSummaryResponse(saved));
        System.out.println(new OrderResponse(saved, orderItemList));
    }

    @Test
    void getOrder() {
        // given
        CartItem cartItem = cartItemService.addItem(1L, 1L);
        Order order = orderService.order(1L, Order.builder()
                        .userId(1L)
                        .orderDate(LocalDateTime.now())
                        .status(COMPLETE_ORDER)
                        .deliveryAddress("서울시 강동구")
                        .phoneNumber("010-1111-2222")
                        .email("user@email.com")
                        .messageToRider("감사합니다")
                        .paymentMethod("신용/체크카드")
                        .build(),
                // discountCouponIdList
                List.of(1L, 2L));
        // when
        Order saved = orderService.getOrder(1L, order.getOrderId());
        // then
        List<OrderItem> orderItemList = orderService.getOrderItemListByOrder(saved);
        System.out.println(saved);
        System.out.println(new OrderSummaryResponse(saved));
        System.out.println(new OrderResponse(saved, orderItemList));
    }

    @Test
    void order() {
        // given
        CartItem cartItem = cartItemService.addItem(1L, 1L);
        // when
        Order order = orderService.order(1L, Order.builder()
                        .userId(1L)
                        .orderDate(LocalDateTime.now())
                        .status(COMPLETE_ORDER)
                        .deliveryAddress("서울시 강동구")
                        .phoneNumber("010-1111-2222")
                        .email("user@email.com")
                        .messageToRider("감사합니다")
                        .paymentMethod("신용/체크카드")
                        .build(),
                // discountCouponIdList
                List.of(1L, 2L));
        // then
        Order saved = orderRepository.findById(order.getOrderId()).orElseThrow(RuntimeException::new);
        List<Order> orderList = orderRepository.findByUserId(1L);
        assertAll(
                () -> assertThat(saved).isEqualTo(order),
                () -> assertThat(orderList.get(0)).isEqualTo(order),
                () -> assertThat(cartItemService.getCartItemListByUserId(1L)).isEmpty()
        );
    }

    @Test
    void deleteOrder() {
        // given
        CartItem cartItem = cartItemService.addItem(1L, 1L);
        Order order = orderService.order(1L, Order.builder()
                        .userId(1L)
                        .orderDate(LocalDateTime.now())
                        .status(COMPLETE_ORDER)
                        .deliveryAddress("서울시 강동구")
                        .phoneNumber("010-1111-2222")
                        .email("user@email.com")
                        .messageToRider("감사합니다")
                        .paymentMethod("신용/체크카드")
                        .build(),
                // discountCouponIdList
                List.of(1L, 2L));
        // when
        orderService.deleteOrder(1L, order.getOrderId());
        // then
        assertThat(orderRepository.findByUserId(1L)).isEmpty();
    }

    @Test
    void cancelOrder() {
        // given
        CartItem cartItem = cartItemService.addItem(1L, 1L);
        Order order = orderService.order(1L, Order.builder()
                        .userId(1L)
                        .orderDate(LocalDateTime.now())
                        .status(COMPLETE_ORDER)
                        .deliveryAddress("서울시 강동구")
                        .phoneNumber("010-1111-2222")
                        .email("user@email.com")
                        .messageToRider("감사합니다")
                        .paymentMethod("신용/체크카드")
                        .build(),
                // discountCouponIdList
                List.of(1L, 2L));
        // when
        orderService.cancelOrder(1L, order.getOrderId());
        // then
        Order canceled = orderRepository.findById(order.getOrderId()).orElseThrow(RuntimeException::new);
        assertThat(canceled.getStatus()).isEqualTo(OrderStatus.CANCEL_ORDER);
    }
}
