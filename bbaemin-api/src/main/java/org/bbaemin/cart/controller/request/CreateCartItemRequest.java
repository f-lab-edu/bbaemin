package org.bbaemin.cart.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateCartItemRequest {
    // TODO - CHECK : no-args constructor
    private final Long itemId;
}
