package org.bbaemin.order.vo;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Item_ {

    private Long itemId;
    private String name;
    private String description;

    private int price;

    public Item_(Long itemId, String name, String description, int price) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
