package org.bbaemin.category.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private String code;
    private String name;
    private String description;

    private String parentCategory;
}
