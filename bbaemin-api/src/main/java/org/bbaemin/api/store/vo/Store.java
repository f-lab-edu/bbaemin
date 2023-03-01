package org.bbaemin.api.store.vo;

import lombok.*;
import org.bbaemin.api.category.vo.Category;
import org.bbaemin.api.item.vo.Item;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
public class Store {

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
    private boolean useYn;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category storeCategory;

    @Builder.Default
    @OneToMany(mappedBy = "itemStore")
    private List<Item> itemList = new ArrayList<>();
}
