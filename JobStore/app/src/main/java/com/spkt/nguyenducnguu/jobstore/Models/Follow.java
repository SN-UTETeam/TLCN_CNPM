package com.spkt.nguyenducnguu.jobstore.Models;

public class Follow {
    private String UserId;

    public Follow(String userId) {
        UserId = userId;
    }

    public Follow() {
    }

    public String getEmail() {
        return UserId;
    }

    public void setEmail(String userId) {
        UserId = userId;
    }
}
