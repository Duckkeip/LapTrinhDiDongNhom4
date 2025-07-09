package com.example.sqlite.model;

public class Category {
    private int id;
    private String name;
    private String type;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Category(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
    @Override
    public String toString() {
        return name;
    }
}