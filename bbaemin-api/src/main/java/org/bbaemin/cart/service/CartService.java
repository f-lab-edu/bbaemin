package org.bbaemin.cart.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.controller.response.CartResponse;
import org.bbaemin.orderItem.service.OrderItemService;
import org.bbaemin.orderItem.vo.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final OrderItemService orderItemService;

    public CartResponse getCartResponse(Long userId) {
        List<OrderItem> orderItemList = orderItemService.getOrderItemList(userId);
        return new CartResponse(orderItemList);
    }

    public CartResponse addItem(Long userId, Long itemId) {
        orderItemService.createOrderItem(userId, itemId);
        return getCartResponse(userId);
    }

    public CartResponse plusCount(Long userId, Long orderItemId) {
        orderItemService.increaseOrderCount(userId, orderItemId);
        return getCartResponse(userId);
    }

    public CartResponse minusCount(Long userId, Long orderItemId) {
        orderItemService.decreaseOrderCount(userId, orderItemId);
        return getCartResponse(userId);
    }

    public CartResponse removeItem(Long userId, Long orderItemId) {
        orderItemService.deleteOrderItem(userId, orderItemId);
        return getCartResponse(userId);
    }

    public CartResponse removeItems(Long userId, List<Long> orderItemIds) {
        orderItemService.deleteOrderItems(userId, orderItemIds);
        return getCartResponse(userId);
    }

    public CartResponse removeAll(Long userId) {
        orderItemService.deleteAll(userId);
        return getCartResponse(userId);
    }
}
