package org.bbaemin.category.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCategoryRequest {

    private Integer code;
    private String name;
    private String description;

    private Long parentId;    // 상위 카테고리
}
