package com.spkt.nguyenducnguu.jobstore.Models;

import android.support.annotation.NonNull;

public class Notification implements Comparable<Notification> {
    public static int NOT_SEEN = 0;
    public static int SEEN = 1;
    public static int NOTIFY = 2;
    private String Key;
    private String UserId;
    private String WorkInfoKey;
    private String Title;
    private String Content;
    private Long SendTime;
    private int Status;

    public Notification(String userId, String workInfoKey, String title, String content, Long sendTime, int status) {
        UserId = userId;
        WorkInfoKey = workInfoKey;
        Title = title;
        Content = content;
        SendTime = sendTime;
        Status = status;
    }

    public Notification() {
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getWorkInfoKey() {
        return WorkInfoKey;
    }

    public void setWorkInfoKey(String workInfoKey) {
        WorkInfoKey = workInfoKey;
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

    @Override
    public int compareTo(@NonNull Notification o) {
        return o.getSendTime().compareTo(this.getSendTime());
    }
}
