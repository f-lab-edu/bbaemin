package org.bbaemin.store.domain;

import lombok.*;
import org.bbaemin.category.domain.CategoryEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @NotBlank(message = "매장명 입력은 필수입니다.")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "매장 설명 입력은 필수입니다.")
    @Column(name = "description", nullable = false)
    @Lob
    private String description;

    @NotBlank(message = "점주명 입력은 필수입니다.")
    @Column(name = "owner", nullable = false)
    private String owner;

    @NotBlank(message = "매장 주소 입력은 필수입니다.")
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "매장 우편번호 입력은 필수입니다.")
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @NotBlank(message = "점주 휴대폰 번호 입력은 필수입니다.")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "useYn")
    private Boolean useYn;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity storeCategory;

    public static StoreDto toDto(StoreEntity entity) {
        return StoreDto.builder()
                .name(entity.name)
                .description(entity.description)
                .owner(entity.owner)
                .address(entity.address)
                .zipCode(entity.zipCode)
                .phoneNumber(entity.phoneNumber)
                .useYN(entity.useYn)
                .categoryCode(entity.storeCategory.getCode())
                .categoryName(entity.storeCategory.getName())
                .categoryDescription(entity.storeCategory.getDescription())
                .build();
    }
}
