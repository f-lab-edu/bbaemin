package org.bbaemin.user.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    // 주문 진행중, 주문 완료, 주문 실패, 주문 취소 / 배달중, 배달완료, 배달취소
    PROCESSING_ORDER("주문 진행중"), COMPLETE_ORDER("주문 완료"), FAIL_ORDER("주문 실패"), CANCEL_ORDER("주문 취소");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }
}
