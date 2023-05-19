package org.bbaemin.user.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.dto.response.ItemResponse;
import org.bbaemin.user.cart.repository.CartItemRepository;
import org.bbaemin.user.cart.vo.CartItem;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    private WebClient client = WebClient.create();

    public Flux<CartItem> getCartItemListByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId)
                .log();
    }

    public Mono<CartItem> getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("cartItemId : " + cartItemId)))
                .log();
    }

    public Mono<ItemResponse> getItem(Long itemId) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        // TODO - property
                        .host("http://localhost:8080")
                        .path("/api/v1/items/{itemId}").build(itemId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ItemResponse.class)
                .log();
    }

    public Mono<CartItem> addItem(Long userId, Long itemId) {
        return cartItemRepository.findByUserIdAndItemId(userId, itemId)
                .switchIfEmpty(getItem(itemId).map(
                        item -> CartItem.builder()
                                .itemId(itemId)
                                .itemName(item.getName())
                                .itemDescription(item.getDescription())
                                .orderPrice(item.getPrice())
                                .orderCount(1)
                                .userId(userId)
                                .build())
                )
                .log()
                .map(cartItem -> {
                    cartItem.setOrderCount(cartItem.getOrderCount() + 1);
                    return cartItem;
                })
                .flatMap(cartItemRepository::save)
                .log();
    }

    public Mono<CartItem> updateCount(Long userId, Long cartItemId, int orderCount) {
        return getCartItem(cartItemId)
                .map(cartItem -> {
                    cartItem.setOrderCount(orderCount);
                    return cartItem;
                })
                .log()
                .flatMap(cartItemRepository::save)
                .log();
    }

    public Mono<Void> removeItem(Long userId, Long cartItemId) {
        return cartItemRepository.deleteById(cartItemId)
                .log();
    }

    public Mono<Void> removeItems(Long userId, List<Long> cartItemIdList) {
        return cartItemRepository.deleteAllById(cartItemIdList)
                .log();
    }

    public Mono<Void> clear(Long userId) {
        return cartItemRepository.deleteByUserId(userId)
                .log();
    }
}
