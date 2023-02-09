package org.bbaemin.cart.repository;

import org.bbaemin.cart.vo.CartItem;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class CartItemRepository {

    private static final Map<Long, CartItem> map = new ConcurrentHashMap<>();
    private static Long id = 0L;

    public static void clear() {
        map.clear();
    }

    public CartItem findById(Long cartItemId) {
        return map.get(cartItemId);
    }

    public List<CartItem> findByUserId(Long userId) {
        return map.values().stream()
                .filter(cartItem -> cartItem.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public CartItem insert(CartItem cartItem) {
        Long cartItemId = ++id;
        cartItem.setCartItemId(cartItemId);
        map.put(cartItemId, cartItem);
        return cartItem;
    }

    public CartItem update(CartItem cartItem) {
        map.put(cartItem.getCartItemId(), cartItem);
        return cartItem;
    }

    public void deleteById(Long cartItemId) {
        map.remove(cartItemId);
    }

    public void deleteByIds(List<Long> cartItemIds) {
        cartItemIds.forEach(map::remove);
    }

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
