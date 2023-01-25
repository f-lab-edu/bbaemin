package org.bbaemin.category.controller.request;

public class CreateCategoryRequest {

    private String code;
    private String name;
    private String description;

    private Long parentId;    // 상위 카테고리
}
