package com.spkt.nguyenducnguu.jobstore.Models;

public class Apply {
    private String Email;
    private Long Time;

    public Apply() {
    }

    public Apply(String email, Long time) {
        Email = email;
        Time = time;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Long getTime() {
        return Time;
    }

    public void setTime(Long time) {
        Time = time;
    }
}
