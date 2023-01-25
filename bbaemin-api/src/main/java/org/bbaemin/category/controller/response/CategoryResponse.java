package org.bbaemin.category.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private String code;
    private String name;
    private String description;

    private String parent;
}
