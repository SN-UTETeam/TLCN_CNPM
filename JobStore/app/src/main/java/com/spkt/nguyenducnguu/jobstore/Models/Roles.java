package com.spkt.nguyenducnguu.jobstore.Models;

public class Roles {
    private String Email;
    private int Role;

    public Roles() {
    }

    public Roles(String email, int role) {
        Email = email;
        Role = role;
    }

    public String getUserId() {
        return Email;
    }

    public void setUserId(String email) {
        Email = email;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }
}
