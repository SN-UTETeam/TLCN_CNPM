package com.spkt.nguyenducnguu.jobstore.Models;

public class Level {
    private int Id;
    private String Name;

    public Level(int id, String name) {
        Id = id;
        Name = name;
    }

    public Level() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
