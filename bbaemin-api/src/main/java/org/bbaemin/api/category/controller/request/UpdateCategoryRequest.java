package org.bbaemin.api.category.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateCategoryRequest {

    @NotBlank(message = "코드값은 필수입니다.")
    private Integer code;

    @NotBlank(message = "코드명은 필수입니다.")
    private String name;

    @NotBlank(message = "코드 상세내용은 필수입니다.")
    private String description;
    private boolean useYn;

    private Long parentId;    // 상위 카테고리
}
