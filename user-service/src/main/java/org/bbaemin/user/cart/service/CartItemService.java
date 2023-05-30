package org.bbaemin.user.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.dto.response.ItemResponse;
import org.bbaemin.user.cart.repository.CartItemRepository;
import org.bbaemin.user.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import static org.bbaemin.enums.ServicePath.ITEM_SERVICE;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemService {

    @Value("${service.admin}")
    private String admin;

    private final CartItemRepository cartItemRepository;

    private WebClient client = WebClient.create();

    public Flux<CartItem> getCartItemListByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public Mono<CartItem> getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("cartItemId : " + cartItemId)));
    }

    Mono<ItemResponse> getItem(Long itemId) {
        return client.get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder.path(admin)
                            .path(ITEM_SERVICE.getPath())
                            .path("/{itemId}")
                            .build(itemId);
                    log.info("uri : {}", uri);
                    return uri;
                })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ItemResponse.class);
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
                .map(cartItem -> {
                    cartItem.setOrderCount(cartItem.getOrderCount() + 1);
                    return cartItem;
                })
                .flatMap(cartItemRepository::save);
    }

    public Mono<CartItem> updateCount(Long userId, Long cartItemId, int orderCount) {
        return getCartItem(cartItemId)
                .map(cartItem -> {
                    cartItem.setOrderCount(orderCount);
                    return cartItem;
                })
                .flatMap(cartItemRepository::save);
    }

    public Mono<Void> removeItem(Long userId, Long cartItemId) {
        return cartItemRepository.deleteById(cartItemId);
    }

    public Mono<Void> removeItems(Long userId, List<Long> cartItemIdList) {
        return cartItemRepository.deleteAllById(cartItemIdList);
    }

    public Mono<Void> clear(Long userId) {
        return cartItemRepository.deleteByUserId(userId);
    }
}
