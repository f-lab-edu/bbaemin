package org.bbaemin.api.category.controller.response;

import lombok.Getter;
import org.bbaemin.api.category.vo.Category;

@Getter
public class CategoryResponse {

    private Integer code;
    private String name;
    private String description;
    private boolean useYn;

    private Integer parentCode;

    public CategoryResponse(Category category) {
        this.code = category.getCode();
        this.name = category.getName();
        this.description = category.getDescription();
        this.useYn = category.isUseYn();
        this.parentCode = category.getParent().getCode();
    }
}
