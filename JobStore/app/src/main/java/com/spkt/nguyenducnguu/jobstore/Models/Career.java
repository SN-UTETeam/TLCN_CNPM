package com.spkt.nguyenducnguu.jobstore.Models;

public class Career {
    private String Name;

    public Career(String name) {
        Name = name;
    }

    public Career() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
