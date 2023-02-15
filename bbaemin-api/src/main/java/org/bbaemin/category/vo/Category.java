package org.bbaemin.category.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Category {

    private Long categoryId;

    private Integer code;
    private String name;
    private String description;

    private Long parentId;
}
