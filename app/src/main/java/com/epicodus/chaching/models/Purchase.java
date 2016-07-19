package com.epicodus.chaching.models;

public class Purchase {
    String name;
    double cost;
    Category category;

    public Purchase() {}

    public Purchase(String name, double cost, Category category) {
        this.name = name;
        this.cost = cost;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public Category getCategory() {
        return category;
    }
}
