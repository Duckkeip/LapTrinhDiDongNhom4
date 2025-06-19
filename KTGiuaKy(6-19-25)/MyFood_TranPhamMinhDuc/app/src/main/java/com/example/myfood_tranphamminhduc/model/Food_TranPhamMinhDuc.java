package com.example.myfood_tranphamminhduc.model;

public class Food_TranPhamMinhDuc {
    private int foodID;
    private String name;
    private String type;
    private String description;
    private String image;
    private int resID;

    public Food_TranPhamMinhDuc(int foodID, String name, String type, String description, String image, int resID) {
        this.foodID = foodID;
        this.name = name;
        this.type = type;
        this.description = description;
        this.image = image;
        this.resID = resID;
    }

    // Getter
    public int getFoodID() {
        return foodID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getResID() {
        return resID;
    }

    // Setter (nếu cần)
    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }
}
