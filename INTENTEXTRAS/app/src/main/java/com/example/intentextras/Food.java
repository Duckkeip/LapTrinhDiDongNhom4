package com.example.intentextras;

public class Food {
    private String name;
    private int imageResId;
    private String description;
    private double price;

    public Food(String name, int imageResId, String description, double price){
        this.name = name;
        this.imageResId = imageResId;
        this.description = description;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription(){
        return description;
    }

    public double getPrice(){
        return price;
    }
}
