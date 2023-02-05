package org.bbaemin.cart.controller.request;

import lombok.Getter;

@Getter
public class UpdateCartItemCountRequest {

    private Long cartItemId;
    private int orderCount;
}
