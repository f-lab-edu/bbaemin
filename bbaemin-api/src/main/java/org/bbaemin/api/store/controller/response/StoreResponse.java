package org.bbaemin.api.store.controller.response;

import lombok.Getter;
import org.bbaemin.api.store.vo.Store;

@Getter
public class StoreResponse {

    private Integer CategoryCode;
    private String name;
    private String description;
    private String owner;
    private String address;
    private String zipCode;
    private String phoneNumber;
    private boolean useYn;


    public StoreResponse(Store store) {
        this.CategoryCode = store.getStoreCategory().getCode();
        this.name = store.getName();
        this.description = store.getDescription();
        this.owner = store.getOwner();
        this.address = store.getAddress();
        this.zipCode = store.getZipCode();
        this.phoneNumber = store.getPhoneNumber();
        this.useYn = store.isUseYn();
    }
}
