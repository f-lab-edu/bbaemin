package org.bbaemin.cart.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCartItemCountRequest {

    private Long cartItemId;
    private int orderCount;
}
