package com.spkt.nguyenducnguu.jobstore.Models;

public class WorkType {
    private String Name;

    public WorkType(String name) {
        Name = name;
    }

    public WorkType() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
