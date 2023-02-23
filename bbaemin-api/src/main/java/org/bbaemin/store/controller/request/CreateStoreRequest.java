package org.bbaemin.store.controller.request;

import lombok.Getter;

@Getter
public class CreateStoreRequest {

    private String name;
    private String description;
    private String owner;
    private String address;
    private String zipCode;
    private String phoneNumber;
    private boolean useYn;

    private Long categoryId;
}
