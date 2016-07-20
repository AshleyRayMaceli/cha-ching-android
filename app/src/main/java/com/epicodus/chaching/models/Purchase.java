package com.epicodus.chaching.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Purchase {
    String name;
    double cost;
    String pushId;
    String category;

    public Purchase() {}

    public Purchase(String name, double cost, String category) {
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

    public String getCategory() {
        return category;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
