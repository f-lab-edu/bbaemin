package org.bbaemin.cart.service;

import org.bbaemin.cart.repository.CartItemRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class CartItemServiceTest {

    @InjectMocks
    CartItemService cartItemService;
    @Mock
    CartItemRepository cartItemRepository;

}
