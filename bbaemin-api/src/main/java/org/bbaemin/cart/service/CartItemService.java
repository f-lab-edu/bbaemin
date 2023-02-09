package org.bbaemin.cart.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.repository.CartItemRepository;
import org.bbaemin.cart.vo.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public List<CartItem> getCartItemListByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartItem addItem(Long userId, Long itemId) {
        CartItem cartItem = CartItem.builder()
                .itemId(itemId)

                // TODO - itemRepository 조회
                // Cart에 담았던 당시의 Item 정보
                .itemName("name")
                .itemDescription("description")
                .orderPrice(10000)

                .userId(userId)
                .orderCount(1)
                .build();
        cartItemRepository.insert(cartItem);
        return cartItem;
    }

    public CartItem updateCount(Long userId, Long cartItemId, int orderCount) {
        CartItem cartItem = cartItemRepository.findById(cartItemId);
        cartItem.setOrderCount(orderCount);
        cartItemRepository.update(cartItem);
        return cartItem;
    }

    public void removeItem(Long userId, Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void removeItems(Long userId, List<Long> cartItemIds) {
        cartItemRepository.deleteByIds(cartItemIds);
    }

    public void clear(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
