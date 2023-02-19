package org.bbaemin.cart.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.repository.CartItemRepository;
import org.bbaemin.cart.vo.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public List<CartItem> getCartItemListByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    private CartItem getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NoSuchElementException("cartItemId : " + cartItemId));
    }

    // TODO - 만약 똑같은 아이템을 두 번 넣으면 어떻게 되나요?
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
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    public CartItem updateCount(Long userId, Long cartItemId, int orderCount) {
        CartItem cartItem = getCartItem(cartItemId);
        cartItem.setOrderCount(orderCount);
        return cartItem;
    }

    public void removeItem(Long userId, Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void removeItems(Long userId, List<Long> cartItemIds) {
        cartItemRepository.deleteAllById(cartItemIds);
    }

    public void clear(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
