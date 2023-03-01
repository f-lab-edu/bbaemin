package org.bbaemin.cart.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.cart.repository.CartItemRepository;
import org.bbaemin.cart.vo.CartItem;
import org.bbaemin.item.service.ItemService;
import org.bbaemin.item.vo.Item;
import org.bbaemin.user.service.UserService;
import org.bbaemin.user.vo.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ItemService itemService;
    private final UserService userService;

    public List<CartItem> getCartItemListByUserId(Long userId) {
        User user = userService.getUser(userId);
        return cartItemRepository.findByUser(user);
    }

    private CartItem getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NoSuchElementException("cartItemId : " + cartItemId));
    }

    public CartItem addItem(Long userId, Long itemId) {

        User user = userService.getUser(userId);
        Item item = itemService.getItem(itemId);

        Optional<CartItem> optional = cartItemRepository.findByUserAndItem(user, item);
        CartItem cartItem = null;
        if (optional.isPresent()) {
            cartItem = optional.get();
            cartItem.setOrderCount(cartItem.getOrderCount() + 1);
        } else {
            cartItem = CartItem.builder()
                    .item(item)
                    .itemName(item.getName())
                    .itemDescription(item.getDescription())
                    // TODO - 아이템별 할인
                    .orderPrice(item.getPrice())
                    .orderCount(1)
                    .user(user)
                    .build();
            cartItemRepository.save(cartItem);
        }
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

    public void removeItems(Long userId, List<Long> cartItemIdList) {
        cartItemRepository.deleteAllById(cartItemIdList);
    }

    public void clear(Long userId) {
        User user = userService.getUser(userId);
        cartItemRepository.deleteByUser(user);
    }
}
