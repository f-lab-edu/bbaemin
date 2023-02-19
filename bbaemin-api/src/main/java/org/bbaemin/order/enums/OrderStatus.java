package org.bbaemin.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    // 주문완료, 주문취소 / 배달중, 배달완료, 배달취소
    COMPLETE_ORDER("주문완료"), CANCEL_ORDER("주문취소");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }
}
