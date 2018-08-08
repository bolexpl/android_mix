package com.example.bolek.testy.models;

public class FileItem {
    private String name;
    private String attr;
    private char type;

    public FileItem(String name, String attr, char t) {
        this.name = name;
        this.attr = attr;
        this.type = t;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
}
