package com.spkt.nguyenducnguu.jobstore.Models;

public class Follow {
    private String Email;

    public Follow(String email) {
        Email = email;
    }

    public Follow() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
