package com.spkt.nguyenducnguu.jobstore.Models;

public class WorkPlace {
    private String Name;

    public WorkPlace() {
    }

    public WorkPlace(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
