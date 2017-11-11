package com.spkt.nguyenducnguu.jobstore.Models;

public class Follow {
    private String UserId;

    public Follow(String userId) {
        UserId = userId;
    }

    public Follow() {
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
