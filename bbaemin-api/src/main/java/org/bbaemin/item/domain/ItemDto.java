package org.bbaemin.item.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDto {

    // 아이템 관련
    private String name;
    private String description;
    private int price;
    private int quantity;

    // 카테고리 관련
    private String categoryName;
    private String categoryDescription;

    // 매장 관련
    private String storeName;
    private String storeDescription;
    private String storeOwner;
    private String address;
    private String zipCode;
    private String phoneNumber;
}
