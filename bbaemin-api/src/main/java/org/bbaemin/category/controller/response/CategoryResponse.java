package org.bbaemin.category.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private Long categoryId;

    private Integer code;
    private String name;
    private String description;

    private Integer parent;
}
