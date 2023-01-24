package org.bbaemin.category.request;

public class CreateCategoryRequest {

    private String categoryCode;
    private String categoryName;
    private String categoryDescription;

    private Long parentId;    // 상위 카테고리
}
