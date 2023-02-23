package org.bbaemin.category.controller.request;

import lombok.Builder;
import lombok.Getter;
import org.bbaemin.category.domain.Category;

@Getter
public class CreateCategoryRequest {

    private Integer code;
    private String name;
    private String description;
    private boolean useYn;

    private Long parentId;    // 상위 카테고리
}
