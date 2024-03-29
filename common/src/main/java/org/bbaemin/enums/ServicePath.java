package org.bbaemin.enums;

import lombok.Getter;

@Getter
public enum ServicePath {

    COUPON_SERVICE("/api/v1/coupons"),
    DELIVERY_SERVICE("/api/v1/delivery"),
    ITEM_SERVICE("/api/v1/items"),

    CART_SERVICE("/api/v1/cart"),
    ORDER_SERVICE("/api/v1/orders");

    private String path;

    ServicePath(String path) {
        this.path = path;
    }
}
