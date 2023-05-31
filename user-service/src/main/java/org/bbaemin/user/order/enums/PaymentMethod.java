package org.bbaemin.user.order.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {

    CARD("신용/체크카드"),
    PHONE("휴대폰결제"),
    NAVER("네이버페이"),
    KAKAO("카카오페이");

    private String name;

    PaymentMethod(String name) {
        this.name = name;
    }
}
