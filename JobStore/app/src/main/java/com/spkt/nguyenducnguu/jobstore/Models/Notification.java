package com.spkt.nguyenducnguu.jobstore.Models;

public class Notification {
    private String Title;
    private String Content;
    private Long SendTime;
    private int Status;

    public Notification(String title, String content, Long sendTime, int status) {
        Title = title;
        Content = content;
        SendTime = sendTime;
        Status = status;
    }

    public Notification() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Long getSendTime() {
        return SendTime;
    }

    public void setSendTime(Long sendTime) {
        SendTime = sendTime;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
