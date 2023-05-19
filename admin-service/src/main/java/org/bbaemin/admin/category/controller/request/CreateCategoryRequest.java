package org.bbaemin.admin.category.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateCategoryRequest {

    @NotBlank(message = "코드값은 필수입니다.")
    private Integer code;

    @NotBlank(message = "코드명은 필수입니다.")
    private String name;

    @NotBlank(message = "코드 상세내용은 필수입니다.")
    private String description;

    private Long parentId;    // 상위 카테고리
}
