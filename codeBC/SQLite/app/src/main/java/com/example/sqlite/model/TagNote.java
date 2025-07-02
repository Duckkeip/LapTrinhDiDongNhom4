package com.example.sqlite.model;

public class TagNote {
    private int id;
    private String tag;
    private String note;

    public TagNote() {}

    public TagNote(int id, String tag, String note) {
        this.id = id;
        this.tag = tag;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
// Getters và setters...
}
