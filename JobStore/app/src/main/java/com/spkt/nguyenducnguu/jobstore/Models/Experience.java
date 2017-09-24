package com.spkt.nguyenducnguu.jobstore.Models;

public class Experience {
    private int Id;
    private String Name;

    public Experience() {
    }

    public Experience(int id, String name) {
        Id = id;
        Name = name;
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
