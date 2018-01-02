package com.spkt.nguyenducnguu.jobstore.Models;

import android.support.annotation.NonNull;

import java.util.Date;

public class Notification implements Comparable<Notification> {
    public static int NOT_SEEN = 0;
    public static int SEEN = 1;
    public static int NOTIFY = 2;
    private boolean Lock;
    private boolean Warning;
    private boolean Delete;
    private boolean NewWorkInfo;
    private boolean NewApply;
    private String Key;
    private String Ref;
    private String UserId;
    private String WorkInfoKey;
    private String Title;
    private String Content;
    private Long SendTime;
    private int Status;

    public Notification(boolean lock, boolean warning, boolean delete, boolean newWorkInfo, boolean newApply,
                        String key, String userId, String workInfoKey, String title, String content, Long sendTime,
                        int status) {
        Lock = lock;
        Warning = warning;
        Delete = delete;
        NewWorkInfo = newWorkInfo;
        NewApply = newApply;
        Key = key;
        UserId = userId;
        WorkInfoKey = workInfoKey;
        Title = title;
        Content = content;
        SendTime = sendTime;
        Status = status;
    }

    public Notification() {
        Lock = false;
        Warning = false;
        Delete = false;
        NewWorkInfo = false;
        NewApply = false;
        Key = UserId = WorkInfoKey = Title = Content = Ref = "";
        SendTime = (new Date()).getTime();
        Status = NOTIFY;
    }

    public boolean isSender(String userId) {
        if(UserId == null) return false;
        if(UserId.equals(userId)) return true;
        return false;
    }

    public boolean isLock() {
        return Lock;
    }

    public void setLock(boolean lock) {
        Lock = lock;
    }

    public boolean isWarning() {
        return Warning;
    }

    public void setWarning(boolean warning) {
        Warning = warning;
    }

    public boolean isDelete() {
        return Delete;
    }

    public void setDelete(boolean delete) {
        Delete = delete;
    }

    public boolean isNewWorkInfo() {
        return NewWorkInfo;
    }

    public void setNewWorkInfo(boolean newWorkInfo) {
        NewWorkInfo = newWorkInfo;
    }

    public boolean isNewApply() {
        return NewApply;
    }

    public void setNewApply(boolean newApply) {
        NewApply = newApply;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getRef() {
        return Ref;
    }

    public void setRef(String ref) {
        Ref = ref;
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
