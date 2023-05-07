package org.bbaemin.api.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.api.cart.service.CartItemService;
import org.bbaemin.api.cart.service.DeliveryFeeService;
import org.bbaemin.api.cart.vo.CartItem;
import org.bbaemin.api.order.enums.OrderStatus;
import org.bbaemin.api.order.kafka.OrderCompletedEventProducer;
import org.bbaemin.api.order.kafka.message.OrderCompletedMessage;
import org.bbaemin.api.order.kafka.message.OrderMessage;
import org.bbaemin.api.order.repository.OrderItemRepository;
import org.bbaemin.api.order.repository.OrderRepository;
import org.bbaemin.api.order.vo.Order;
import org.bbaemin.api.order.vo.OrderItem;
import org.bbaemin.api.user.service.UserService;
import org.bbaemin.api.user.vo.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final CartItemService cartItemService;
    private final DeliveryFeeService deliveryFeeService;
    private final UserService userService;
    private final CouponService couponService;

    private final OrderCompletedEventProducer orderCompletedEventProducer;

    public List<Order> getOrderListByUserId(Long userId) {
        User user = userService.getUser(userId);
        return orderRepository.findByUser(user);
    }

    public Order getOrder(Long userId, Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("orderId : " + orderId));
    }

    public List<OrderItem> getOrderItemListByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    public OrderItem getOrderItem(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NoSuchElementException("orderItemId : " + orderItemId));
    }

    private List<OrderItem> getOrderItemList(Long userId) {
        List<CartItem> cartItemList = cartItemService.getCartItemListByUserId(userId);
        return cartItemList.stream()
                .map(cartItem -> OrderItem.builder()
                        .item(cartItem.getItem())
                        .itemName(cartItem.getItemName())
                        .itemDescription(cartItem.getItemDescription())
                        .orderPrice(cartItem.getOrderPrice())
                        .orderCount(cartItem.getOrderCount())
                        .build())
                .collect(Collectors.toList());
    }

    private List<OrderItem> setOrder(Order order, Long userId, List<Long> discountCouponIdList) {
        
        // 주문자
        User user = userService.getUser(userId);
        order.setUser(user);

        // 주문 총액, 배달료
        List<OrderItem> orderItemList = getOrderItemList(userId);
        int orderAmount = orderItemList.stream()
                .mapToInt(orderItem -> orderItem.getOrderPrice() * orderItem.getOrderCount()).sum();
        int deliveryFee = deliveryFeeService.getDeliveryFee(orderAmount);
        order.setOrderAmount(orderAmount);
        order.setDeliveryFee(deliveryFee);

        // 할인 금액
        int totalDiscountAmount = couponService.apply(orderAmount, discountCouponIdList);
        order.setPaymentAmount(orderAmount + deliveryFee - totalDiscountAmount);

        return orderItemList;
    }

    @Transactional
    @KafkaListener(
            topics = "order-message",
            groupId = "order-message-consumers",
            containerFactory = "orderMessageConcurrentKafkaListenerContainerFactory"
    )
    public void order(@Payload OrderMessage orderMessage) {

        log.info("**************** OrderEventListener ****************");
        log.info(">>>>> 주문 메세지 수신 : {}", orderMessage);
        log.info("****************************************************");

        Long userId = orderMessage.getUserId();
        Order order = orderMessage.getOrder();
        List<Long> discountCouponIdList = orderMessage.getDiscountCouponIdList();

        List<OrderItem> orderItemList = setOrder(order, userId, discountCouponIdList);
        Order saved = orderRepository.save(order);
        orderItemList.forEach(orderItem -> {
            orderItem.setOrder(saved);
            orderItemRepository.save(orderItem);
        });

        cartItemService.clear(userId);

        // 주문 완료 메세지
        OrderCompletedMessage orderCompletedMessage = OrderCompletedMessage.builder()
                .txId(UUID.randomUUID().toString())
                .version("1")
                .completedAt(LocalDateTime.now().toString())
                .orderId(saved.getOrderId())
                .build();
        orderCompletedEventProducer.send(orderCompletedMessage);
    }

    @Transactional
    public void deleteOrder(Long userId, Long orderId) {
        Order order = getOrder(userId, orderId);
        orderItemRepository.deleteByOrder(order);
        orderRepository.delete(order);
    }

    @Transactional
    public Order cancelOrder(Long userId, Long orderId) {
        // TODO - vs updateStatusCancel
        Order order = getOrder(userId, orderId);
        order.setStatus(OrderStatus.CANCEL_ORDER);
        return order;
    }
}
