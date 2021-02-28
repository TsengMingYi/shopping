package com.example.shopping.databaase;

import com.example.shopping.databaase.base.BaseEntity;

public class Product extends BaseEntity {
    private long price;
    private String name;
    private String description;
    private long inventory;

    public Product(String id, long price, String name, String description, long inventory) {
        super(id);
        this.price = price;
        this.name = name;
        this.description = description;
        this.inventory = inventory;
    }

    public String getDescription() {
        return description;
    }

    public long getInventory() {
        return inventory;
    }

    public long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
