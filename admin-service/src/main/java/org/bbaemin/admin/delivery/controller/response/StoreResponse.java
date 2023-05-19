package org.bbaemin.admin.delivery.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreResponse {

    private Integer categoryCode;
    private String name;
    private String description;
    private String owner;
    private String address;
    private String zipCode;
    private String phoneNumber;

    @Builder
    private StoreResponse(Integer categoryCode, String name, String description, String owner, String address, String zipCode, String phoneNumber) {
        this.categoryCode = categoryCode;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }
}
