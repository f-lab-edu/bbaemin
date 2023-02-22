package org.bbaemin.cart.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UpdateCartItemCountRequest {
    // TODO - CHECK : no-args constructor
    private final Long cartItemId;
    private final int orderCount;
}
