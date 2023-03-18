package org.bbaemin.api.order.service;

import org.bbaemin.api.cart.repository.CartItemRepository;
import org.bbaemin.api.cart.service.CartItemService;
import org.bbaemin.api.cart.service.DeliveryFeeService;
import org.bbaemin.api.cart.vo.CartItem;
import org.bbaemin.api.item.repository.ItemRepository;
import org.bbaemin.api.item.vo.Item;
import org.bbaemin.api.order.controller.response.OrderResponse;
import org.bbaemin.api.order.controller.response.OrderSummaryResponse;
import org.bbaemin.api.order.enums.OrderStatus;
import org.bbaemin.api.order.repository.CouponRepository;
import org.bbaemin.api.order.repository.OrderItemRepository;
import org.bbaemin.api.order.repository.OrderRepository;
import org.bbaemin.api.order.repository.UserCouponRepository;
import org.bbaemin.api.order.vo.Coupon;
import org.bbaemin.api.order.vo.Order;
import org.bbaemin.api.order.vo.OrderItem;
import org.bbaemin.api.order.vo.UserCoupon;
import org.bbaemin.api.user.service.UserService;
import org.bbaemin.api.user.vo.User;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    UserCouponRepository userCouponRepository;

    private User user;
    private Long userId;
    private Item item;
    private Coupon coupon;
    private UserCoupon userCoupon;
    private UserCoupon userCoupon2;

    @BeforeEach
    void beforeEach() {
        // user 등록
        user = userService.join(User.builder()
                .email("user@email.com")
                .nickname("user")
                .image(null)
                .password("password")
                .phoneNumber("010-1111-2222")
                .build());
        userId = user.getUserId();

        // item 등록
        item = itemRepository.save(Item.builder()
                .name("청동사과")
                .description("싱싱한 청동사과")
                .price(2000)
                .quantity(999)
                .build());

        // coupon 등록
        coupon = Coupon.builder()
                .code("event")
                .name("이벤트 쿠폰")
                .minimumOrderAmount(1)
                .discountAmount(5000)
                .expireDate(LocalDateTime.of(2099, 5, 10, 23, 59, 59))
                .build();
        couponRepository.save(coupon);

        // userCoupon 등록
        userCoupon = UserCoupon.builder()
                .user(user)
                .coupon(coupon)
                .build();
        userCouponRepository.save(userCoupon);

        userCoupon2 = UserCoupon.builder()
                .user(user)
                .coupon(coupon)
                .build();
        userCouponRepository.save(userCoupon2);
    }

    @Test
    void getOrderListByUserId() {
        // given
        CartItem cartItem = cartItemService.addItem(userId, item.getItemId());
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
                List.of(userCoupon.getUserCouponId(), userCoupon2.getUserCouponId()));
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
        CartItem cartItem = cartItemService.addItem(userId, item.getItemId());
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
                List.of(userCoupon.getUserCouponId(), userCoupon2.getUserCouponId()));
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
        CartItem cartItem = cartItemService.addItem(userId, item.getItemId());
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
                List.of(userCoupon.getUserCouponId(), userCoupon2.getUserCouponId()));
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
        CartItem cartItem = cartItemService.addItem(userId, item.getItemId());
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
                List.of(userCoupon.getUserCouponId(), userCoupon2.getUserCouponId()));
        // when
        orderService.deleteOrder(userId, order.getOrderId());
        // then
        assertThat(orderRepository.findByUser(user)).isEmpty();
    }

    @Test
    void cancelOrder() {
        // given
        CartItem cartItem = cartItemService.addItem(userId, item.getItemId());
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
                List.of(userCoupon.getUserCouponId(), userCoupon2.getUserCouponId()));
        // when
        orderService.cancelOrder(userId, order.getOrderId());
        // then
        Order canceled = orderRepository.findById(order.getOrderId()).orElseThrow(RuntimeException::new);
        assertThat(canceled.getStatus()).isEqualTo(OrderStatus.CANCEL_ORDER);
    }
}
