package com.example.myfood_tranphamminhduc.model;

public class Restaurant_TranPhamMinhDuc {

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private int resID;
    private String name;
    private String address;
    private String phone;
    private String image;

    public Restaurant_TranPhamMinhDuc(int resID, String name, String address, String phone, String image) {
        this.resID = resID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.image = image;
    }

}
