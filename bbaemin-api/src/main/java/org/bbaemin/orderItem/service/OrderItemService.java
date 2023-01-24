package org.bbaemin.orderItem.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.orderItem.repository.OrderItemRepository;
import org.bbaemin.orderItem.vo.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> getOrderItemList(Long userId) {
        return orderItemRepository.findByUserId(userId);
    }

    public void createOrderItem(Long userId, Long itemId) {
        // TODO - itemService
//        Item item = itemRepository.findById(itemId);
        OrderItem orderItem = OrderItem.builder()
//                .itemName(item.getName())
//                .itemDescription(item.getDescription())
//                .orderPrice(item.getPrice())
//                .orderCount(1)
//                .totalOrderPrice(item.getPrice())
                .userId(userId)
                .build();
        orderItemRepository.insert(orderItem);
    }

    public void increaseOrderCount(Long userId, Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId);
        orderItem.addCount();
        orderItemRepository.update(orderItemId, orderItem);
    }

    public void decreaseOrderCount(Long userId, Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId);
        orderItem.minusCount();
        orderItemRepository.update(orderItemId, orderItem);
    }

    public void deleteOrderItem(Long userId, Long orderItemId) {
        orderItemRepository.delete(orderItemId);
    }

    public void deleteOrderItems(Long userId, List<Long> orderItemIds) {
        orderItemRepository.delete(orderItemIds);
    }

    public void deleteAll(Long userId) {
        orderItemRepository.deleteByUserId(userId);
    }
}
