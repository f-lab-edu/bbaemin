package org.bbaemin.admin.delivery.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bbaemin.admin.category.vo.Category;
import org.bbaemin.admin.item.vo.Item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    @Lob
    private String description;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category storeCategory;

    @OneToMany(mappedBy = "itemStore")
    private List<Item> itemList = new ArrayList<>();

    @Builder
    private Store(Long storeId, String name, String description, String owner, String address, String zipCode, String phoneNumber, Category storeCategory, List<Item> itemList) {
        this.storeId = storeId;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        if (!Objects.isNull(storeCategory)) {
            this.storeCategory = storeCategory;
            storeCategory.addStore(this);
        }
        this.itemList = itemList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStoreCategory(Category storeCategory) {
        this.storeCategory = storeCategory;
        // 매장-카테고리 연관관계 설정
        storeCategory.addStore(this);
    }

    public void addItem(Item item) {
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
        this.itemList.add(item);
    }
}
