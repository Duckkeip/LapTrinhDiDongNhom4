package com.example.sqlite.model;

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

    private String categoryName;  // tên danh mục (Lương, Ăn uống,...)
    private String categoryType;  // 'income' hoặc 'expense'

    public Income() {}

    public Income(int id, double amount, String note, String date, int categoryId, String user) {
        this.id = id;
        this.amount = amount;
        this.note = note;
        this.date = date;
        this.categoryId = categoryId;
        this.user = user;
    }

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
