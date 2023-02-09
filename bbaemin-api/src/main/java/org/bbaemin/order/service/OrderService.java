package org.bbaemin.order.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.service.CartItemService;
import org.bbaemin.cart.service.DeliveryFeeService;
import org.bbaemin.cart.vo.CartItem;
import org.bbaemin.order.enums.OrderStatus;
import org.bbaemin.order.repository.OrderItemRepository;
import org.bbaemin.order.repository.OrderRepository;
import org.bbaemin.order.vo.Order;
import org.bbaemin.order.vo.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemService cartItemService;
    private final DeliveryFeeService deliveryFeeService;

    public List<Order> getOrderListByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrder(Long userId, Long orderId) {
        return orderRepository.findById(orderId);
    }

    public List<OrderItem> getOrderItemListByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public Order order(Long userId, Order order, List<Long> discountCouponIdList) {

        List<CartItem> cartItemList = cartItemService.getCartItemListByUserId(userId);
        int orderAmount = cartItemList.stream().mapToInt(cartItem -> cartItem.getOrderPrice() * cartItem.getOrderCount()).sum();
        int deliveryFee = deliveryFeeService.getDeliveryFee(orderAmount);

        List<OrderItem> orderItemList = cartItemList.stream()
                .map(cartItem -> OrderItem.builder()
                        .itemId(cartItem.getItemId())
                        .itemName(cartItem.getItemName())
                        .itemDescription(cartItem.getItemDescription())
                        .orderPrice(cartItem.getOrderPrice())
                        .orderCount(cartItem.getOrderCount())
                        .build())
                .collect(Collectors.toList());
        order.setOrderAmount(orderAmount);
        order.setDeliveryFee(deliveryFee);

        if (cartItemList.size() > 1) {
            order.setDescription(String.format("%s 외 %d개", cartItemList.get(0).getItemName(), cartItemList.size() - 1));
        } else {
            order.setDescription(cartItemList.get(0).getItemName());
        }

        // TODO - discountCouponIdList
        order.setPaymentAmount(orderAmount + deliveryFee);

        Order saved = orderRepository.insert(order);
        orderItemList.forEach(orderItem -> {
            orderItem.setOrderId(saved.getOrderId());
            orderItemRepository.insert(orderItem);
        });

        cartItemService.clear(userId);
        return saved;
    }

    public void deleteOrder(Long userId, Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order cancelOrder(Long userId, Long orderId) {
        // TODO - updateStatusCancel
        Order order = orderRepository.findById(orderId);
        order.setStatus(OrderStatus.CANCEL_ORDER);
        return orderRepository.update(order);
    }
}
