package org.bbaemin.admin.category.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryResponse {

    private Integer code;
    private String name;
    private String description;
    private Integer parentCode;

    @Builder
    private CategoryResponse(Integer code, String name, String description, Integer parentCode) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.parentCode = parentCode;
    }
}
