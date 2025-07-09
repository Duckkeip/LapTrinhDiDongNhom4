package com.example.sqlite.model;
import com.google.gson.annotations.SerializedName;
public class Income {
    private int id;
    private double amount;
    private String note;
    private String date;
    private int categoryId;
    private String user;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
    @SerializedName("category_name")
    private String categoryName;  // tên danh mục (Lương, Ăn uống,...)
    @SerializedName("category_type")
    private String categoryType;  // 'income' hoặc 'expense'


    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
}
