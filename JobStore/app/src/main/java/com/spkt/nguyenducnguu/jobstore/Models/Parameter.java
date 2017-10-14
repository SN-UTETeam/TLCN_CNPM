package com.spkt.nguyenducnguu.jobstore.Models;

public class Parameter {
    String Name;
    String Value;

    public Parameter() {
    }

    public Parameter(String name, String value) {
        Name = name;
        Value = value;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
