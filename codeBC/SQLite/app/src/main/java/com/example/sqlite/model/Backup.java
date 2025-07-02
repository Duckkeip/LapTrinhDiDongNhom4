package com.example.sqlite.model;

public class Backup {
    private int id;
    private String fileName;
    private String date;

    public Backup() {}

    public Backup(int id, String fileName, String date) {
        this.id = id;
        this.fileName = fileName;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
// Getters và setters...
}
