package org.bbaemin.api.store.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateStoreRequest {

    @NotBlank(message = "매장명은 필수입니다.")
    private String name;

    @NotBlank(message = "매장 상세내용은 필수입니다.")
    private String description;

    @NotBlank(message = "매장 점주는 필수입니다.")
    private String owner;

    @NotBlank(message = "매장 주소는 필수입니다.")
    private String address;

    @NotBlank(message = "매장 우편번호는 필수입니다.")
    private String zipCode;

    @NotBlank(message = "매장 전화번호는 필수입니다.")
    private String phoneNumber;
    private boolean useYn;

    @NotBlank(message = "매장 카테고리는 필수입니다.")
    private Long categoryId;
}
