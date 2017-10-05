package com.spkt.nguyenducnguu.jobstore.Models;

public class Level {
    private String Name;

    public Level(String name) {
        Name = name;
    }

    public Level() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
