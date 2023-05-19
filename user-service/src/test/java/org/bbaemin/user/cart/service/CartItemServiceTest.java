package org.bbaemin.user.cart.service;

import org.bbaemin.dto.response.ItemResponse;
import org.bbaemin.user.cart.repository.CartItemRepository;
import org.bbaemin.user.cart.vo.CartItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceTest {

    @Spy
    @InjectMocks
    CartItemService cartItemService;
    @Mock
    CartItemRepository cartItemRepository;

    private CartItem cartItem = CartItem.builder()
            .cartItemId(1L)
            .itemId(1L)
            .itemName("item_name")
            .itemDescription("item_desc")
            .orderPrice(10000)
            .orderCount(1)
            .userId(1L)
            .build();

    @Test
    void addItem_if_already_exists() {
        // given
        doReturn(Mono.just(cartItem))
                .when(cartItemRepository).findByUserIdAndItemId(1L, 1L);
        doReturn(Mono.just(cartItem))
                .when(cartItemRepository).save(cartItem);
        // when
        StepVerifier.create(cartItemService.addItem(1L, 1L))
                .expectNextMatches(c -> c.getOrderCount() == 2)
                .expectComplete()
                .verify();
        // then
        verify(cartItemRepository).save(cartItem);
    }

    @Test
    void addItem_if_not_exists() {
        // given
        doReturn(Mono.empty())
                .when(cartItemRepository).findByUserIdAndItemId(1L, 1L);

        ItemResponse itemResponse = ItemResponse.builder()
                .name("item_name")
                .description("item_desc")
                .price(10000)
                .build();
        doReturn(Mono.just(itemResponse))
                .when(cartItemService).getItem(1L);
        doReturn(Mono.just(cartItem))
                .when(cartItemRepository).save(any(CartItem.class));

        // when
        StepVerifier.create(cartItemService.addItem(1L, 1L))
                .expectNextMatches(c -> {
                    return c.getItemId().equals(1L)
                            && c.getItemName().equals(itemResponse.getName())
                            && c.getItemDescription().equals(itemResponse.getDescription())
                            && c.getOrderPrice() == 10000
                            && c.getOrderCount() == 1
                            && c.getUserId().equals(1L)
                            && c.getCartItemId() != null;
                })
                .expectComplete()
                .verify();
        // then
        verify(cartItemRepository).save(any(CartItem.class));
    }

    @Test
    void updateCount() {
        // given
        doReturn(Mono.just(cartItem))
                .when(cartItemRepository).findById(1L);
        doReturn(Mono.just(cartItem))
                .when(cartItemRepository).save(any(CartItem.class));
        // when
        // then
        StepVerifier.create(cartItemService.updateCount(1L, 1L, 2))
                .expectNextMatches(updated -> updated.getOrderCount() == 2)
                .expectComplete()
                .verify();
    }

    @Test
    void updateCount_if_not_exists() {
        // given
        doReturn(Mono.empty())
                .when(cartItemRepository).findById(1L);
        // when
        // then
        StepVerifier.create(cartItemService.getCartItem(1L))
                .expectErrorMatches(e -> e instanceof NoSuchElementException
                        && e.getMessage().equals("cartItemId : 1"))
                .verify();
    }
}
