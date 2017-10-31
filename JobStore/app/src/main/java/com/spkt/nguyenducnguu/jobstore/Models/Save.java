package com.spkt.nguyenducnguu.jobstore.Models;

public class Save {
    private String UserId;
    private Long Time;

    public Save() {
    }

    public Save(String userId, Long time) {
        UserId = userId;
        Time = time;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public Long getTime() {
        return Time;
    }

    public void setTime(Long time) {
        Time = time;
    }
}
