package org.bbaemin.category.request;

import java.time.LocalDateTime;

public class CreateCategoryRequest {

    private String categoryCode;
    private String categoryName;
    private String categoryDescription;

    private Long topId;       // 최상위 카테고리
    private Long parentId;    // 상위 카테고리

    private LocalDateTime createdAt;
    private String createdBy;

    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
