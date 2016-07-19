package com.epicodus.chaching.models;

public class Category {
    String name;
    String pushId;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
