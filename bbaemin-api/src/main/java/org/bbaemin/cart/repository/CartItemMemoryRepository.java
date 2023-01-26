package org.bbaemin.cart.repository;

import org.bbaemin.cart.vo.CartItem;
import org.bbaemin.order.vo.Item_;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class CartItemMemoryRepository implements CartItemRepository {

    private static final Map<Long, CartItem> map = new ConcurrentHashMap<>();
    private static Long id = 0L;

    static {
        map.put(1L, CartItem.builder()
                        .cartItemId(1L)
                        .item(new Item_(1L, "item1", "description1", 3000))
                        .orderCount(1)
                        .userId(1L)
                        .build());
        map.put(2L, CartItem.builder()
                        .cartItemId(2L)
                        .item(new Item_(2L, "item2", "description2", 5000))
                        .orderCount(1)
                        .userId(1L)
                        .build());
    }

    public static void clear() {
        map.clear();
    }

    @Override
    public CartItem findById(Long cartItemId) {
        return map.get(cartItemId);
    }

    @Override
    public List<CartItem> findByUserId(Long userId) {
        return map.values().stream()
                .filter(cartItem -> cartItem.getUserId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public CartItem insert(CartItem cartItem) {
        Long cartItemId = ++id;
        cartItem.setCartItemId(cartItemId);
        map.put(cartItemId, cartItem);
        return cartItem;
    }

    @Override
    public CartItem update(CartItem cartItem) {
        map.put(cartItem.getCartItemId(), cartItem);
        return cartItem;
    }

    @Override
    public void deleteById(Long cartItemId) {
        map.remove(cartItemId);
    }

    @Override
    public void deleteByIds(List<Long> cartItemIds) {
        cartItemIds.forEach(map::remove);
    }

    @Override
    public void deleteByUserId(Long userId) {
        Iterator<Map.Entry<Long, CartItem>> iterator = map.entrySet().stream().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, CartItem> next = iterator.next();
            if (next.getValue().getUserId().equals(userId)) {
                map.remove(next.getKey());
            }
        }
    }
}
