package org.bbaemin.order.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.service.CartItemService;
import org.bbaemin.cart.service.DeliveryFeeService;
import org.bbaemin.cart.vo.CartItem;
import org.bbaemin.item.service.ItemService;
import org.bbaemin.order.enums.OrderStatus;
import org.bbaemin.order.repository.OrderItemRepository;
import org.bbaemin.order.repository.OrderRepository;
import org.bbaemin.order.vo.Order;
import org.bbaemin.order.vo.OrderItem;
import org.bbaemin.user.service.UserService;
import org.bbaemin.user.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemService cartItemService;
    private final DeliveryFeeService deliveryFeeService;
    private final UserService userService;
    private final ItemService itemService;
    private final CouponService couponService;

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

    @Transactional
    public Order order(Long userId, Order order, List<Long> discountCouponIdList) {

        User user = userService.getUser(userId);
        order.setUser(user);

        List<CartItem> cartItemList = cartItemService.getCartItemListByUserId(userId);
        int orderAmount = cartItemList.stream().mapToInt(cartItem -> cartItem.getOrderPrice() * cartItem.getOrderCount()).sum();
        int deliveryFee = deliveryFeeService.getDeliveryFee(orderAmount);

        // TODO - 어떻게 테스트 하나요?
        List<OrderItem> orderItemList = cartItemList.stream()
                .map(cartItem -> OrderItem.builder()
                        // TODO - cartItem.getItem()으로 변경
                        .item(itemService.getItem(cartItem.getItemId()))
                        .itemName(cartItem.getItemName())
                        .itemDescription(cartItem.getItemDescription())
                        .orderPrice(cartItem.getOrderPrice())
                        .orderCount(cartItem.getOrderCount())
                        .build())
                .collect(Collectors.toList());
        order.setOrderAmount(orderAmount);
        order.setDeliveryFee(deliveryFee);

        int totalDiscountAmount = couponService.apply(orderAmount, discountCouponIdList);
        order.setPaymentAmount(orderAmount + deliveryFee - totalDiscountAmount);

        Order saved = orderRepository.save(order);
        orderItemList.forEach(orderItem -> {
            orderItem.setOrder(saved);
            orderItemRepository.save(orderItem);
        });

        cartItemService.clear(userId);
        return saved;
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
