package org.bbaemin.store.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreDto {

    private String name;
    private String description;
    private String owner;
    private String address;
    private String zipCode;
    private String phoneNumber;
    private Boolean useYN;
    private Integer categoryCode;
    private String categoryName;
    private String categoryDescription;
}
