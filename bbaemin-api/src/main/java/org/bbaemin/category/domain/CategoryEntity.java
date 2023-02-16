package org.bbaemin.category.domain;

import lombok.*;
import org.bbaemin.category.domain.CategoryDto.CategoryDtoBuilder;
import org.bbaemin.store.domain.StoreEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @NotNull(message = "코드값 입력은 필수입니다.")
    @Column(name = "code")
    private Integer code;

    @NotBlank(message = "카테고리명 입력은 필수입니다.")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "카테고리 상세 내용 입력은 필수입니다.")
    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "useYn")
    private Boolean useYn;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<CategoryEntity> children = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "storeCategory")
    private List<StoreEntity> storeList = new ArrayList<>();

    public static CategoryDto toDto(CategoryEntity entity) {
        CategoryDtoBuilder dtoBuilder = CategoryDto.builder()
                .code(entity.code)
                .name(entity.name)
                .description(entity.description);

        if (entity.parent != null) {
            dtoBuilder.parentCode(entity.parent.code)
                    .parentName(entity.parent.name)
                    .parentDescription(entity.parent.description);
        }

        return dtoBuilder.build();
    }
}
