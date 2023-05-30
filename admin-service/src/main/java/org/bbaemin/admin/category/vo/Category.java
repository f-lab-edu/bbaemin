package org.bbaemin.admin.category.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bbaemin.admin.delivery.vo.Store;
import org.bbaemin.admin.item.vo.Item;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "code", nullable = false, unique = true)
    private Integer code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    @Lob
    private String description;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Category> children = new ArrayList<>();

    @OneToMany(mappedBy = "storeCategory")
    private List<Store> storeList = new ArrayList<>();

    @OneToMany(mappedBy = "itemCategory")
    private List<Item> itemList = new ArrayList<>();

    @Builder
    private Category(Long categoryId, Integer code, String name, String description, Category parent, List<Category> children, List<Store> storeList, List<Item> itemList) {
        this.categoryId = categoryId;
        this.code = code;
        this.name = name;
        this.description = description;
        if (!Objects.isNull(parent)) {
            this.parent = parent;
            parent.getChildren().add(this);
        }
        this.children = children;
        this.storeList = storeList;
        this.itemList = itemList;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setParent(Category parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }
}
