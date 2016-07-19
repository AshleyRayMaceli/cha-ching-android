package com.epicodus.chaching.models;

import java.util.ArrayList;
import java.util.List;

public class Category {
    String name;
    List<Purchase> purchases = new ArrayList<>();

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }
}
