package org.bbaemin.category.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CategoryDto {

    private Integer code;
    private String name;
    private String description;
    private Integer parentCode;
    private String parentName;
    private String parentDescription;
    private Boolean useYn;
}
