package org.bbaemin.api.order.service;

import org.bbaemin.api.cart.repository.CartItemRepository;
import org.bbaemin.api.cart.service.CartItemService;
import org.bbaemin.api.cart.service.DeliveryFeeService;
import org.bbaemin.api.cart.vo.CartItem;
import org.bbaemin.api.order.controller.response.OrderResponse;
import org.bbaemin.api.order.controller.response.OrderSummaryResponse;
import org.bbaemin.api.order.enums.OrderStatus;
import org.bbaemin.api.order.repository.OrderItemRepository;
import org.bbaemin.api.order.repository.OrderRepository;
import org.bbaemin.api.order.service.OrderService;
import org.bbaemin.api.order.vo.Order;
import org.bbaemin.api.order.vo.OrderItem;
import org.bbaemin.api.user.service.UserService;
import org.bbaemin.api.user.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bbaemin.api.order.enums.OrderStatus.COMPLETE_ORDER;
import static org.bbaemin.api.order.enums.PaymentMethod.CARD;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@Disabled
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
    @Autowired
    UserService userService;

    private User user;
    private Long userId;

    @BeforeEach
    void beforeEach() {
        user = userService.join(User.builder()
                .email("user@email.com")
                .nickname("user")
                .image(null)
                .password("password")
                .phoneNumber("010-1111-2222")
                .build());
        userId = user.getUserId();
    }

    @Test
    void getOrderListByUserId() {
        // given
        CartItem cartItem = cartItemService.addItem(userId, 1L);
        Order order = orderService.order(userId, Order.builder()
                        .user(user)
                        .orderDate(LocalDateTime.now())
                        .status(COMPLETE_ORDER)
                        .deliveryAddress("서울시 강동구")
                        .phoneNumber("010-1111-2222")
                        .email("user@email.com")
                        .messageToRider("감사합니다")
                        .paymentMethod(CARD)
                        .build(),
                // discountCouponIdList
                List.of(1L, 2L));
        // when
        List<Order> orderList = orderService.getOrderListByUserId(userId);
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
        CartItem cartItem = cartItemService.addItem(userId, 1L);
        Order order = orderService.order(userId, Order.builder()
                        .user(user)
                        .orderDate(LocalDateTime.now())
                        .status(COMPLETE_ORDER)
                        .deliveryAddress("서울시 강동구")
                        .phoneNumber("010-1111-2222")
                        .email("user@email.com")
                        .messageToRider("감사합니다")
                        .paymentMethod(CARD)
                        .build(),
                // discountCouponIdList
                List.of(1L, 2L));
        // when
        Order saved = orderService.getOrder(userId, order.getOrderId());
        // then
        List<OrderItem> orderItemList = orderService.getOrderItemListByOrder(saved);
        System.out.println(saved);
        System.out.println(new OrderSummaryResponse(saved));
        System.out.println(new OrderResponse(saved, orderItemList));
    }

    @Test
    void order() {
        // given
        CartItem cartItem = cartItemService.addItem(userId, 1L);
        // when
        Order order = orderService.order(userId, Order.builder()
                        .user(user)
                        .orderDate(LocalDateTime.now())
                        .status(COMPLETE_ORDER)
                        .deliveryAddress("서울시 강동구")
                        .phoneNumber("010-1111-2222")
                        .email("user@email.com")
                        .messageToRider("감사합니다")
                        .paymentMethod(CARD)
                        .build(),
                // discountCouponIdList
                List.of(1L, 2L));
        // then
        Order saved = orderRepository.findById(order.getOrderId()).orElseThrow(RuntimeException::new);
        List<Order> orderList = orderRepository.findByUser(user);
        assertAll(
                () -> assertThat(saved).isEqualTo(order),
                () -> assertThat(orderList.get(0)).isEqualTo(order),
                () -> assertThat(cartItemService.getCartItemListByUserId(userId)).isEmpty()
        );
    }

    @Test
    void deleteOrder() {
        // given
        CartItem cartItem = cartItemService.addItem(userId, 1L);
        Order order = orderService.order(userId, Order.builder()
                        .user(user)
                        .orderDate(LocalDateTime.now())
                        .status(COMPLETE_ORDER)
                        .deliveryAddress("서울시 강동구")
                        .phoneNumber("010-1111-2222")
                        .email("user@email.com")
                        .messageToRider("감사합니다")
                        .paymentMethod(CARD)
                        .build(),
                // discountCouponIdList
                List.of(1L, 2L));
        // when
        orderService.deleteOrder(userId, order.getOrderId());
        // then
        assertThat(orderRepository.findByUser(user)).isEmpty();
    }

    @Test
    void cancelOrder() {
        // given
        CartItem cartItem = cartItemService.addItem(userId, 1L);
        Order order = orderService.order(userId, Order.builder()
                        .user(user)
                        .orderDate(LocalDateTime.now())
                        .status(COMPLETE_ORDER)
                        .deliveryAddress("서울시 강동구")
                        .phoneNumber("010-1111-2222")
                        .email("user@email.com")
                        .messageToRider("감사합니다")
                        .paymentMethod(CARD)
                        .build(),
                // discountCouponIdList
                List.of(1L, 2L));
        // when
        orderService.cancelOrder(userId, order.getOrderId());
        // then
        Order canceled = orderRepository.findById(order.getOrderId()).orElseThrow(RuntimeException::new);
        assertThat(canceled.getStatus()).isEqualTo(OrderStatus.CANCEL_ORDER);
    }
}
