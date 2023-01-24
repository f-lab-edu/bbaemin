package org.bbaemin.category.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private String categoryCode;
    private String categoryName;
    private String categoryDescription;

    private String parentCategory;
}
