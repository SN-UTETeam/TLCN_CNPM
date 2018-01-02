package com.spkt.nguyenducnguu.jobstore.Models;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.HashMap;

public class WorkInfo implements Comparable<WorkInfo>{
    private String Key = "";
    private String UserId;
    private String CompanyName;
    private String TitlePost;
    private int Views;
    private Long PostTime;
    private Long ExpirationTime;
    private String WorkPlace;
    private WorkDetail WorkDetail;
    private HashMap<String, Apply> Applies;
    private HashMap<String, Share> Shares;
    private HashMap<String, Save> Saves;
    private int Status;

    public WorkInfo(){
        Applies = new HashMap<>();
        Shares = new HashMap<>();
        Saves = new HashMap<>();
    }

    public WorkInfo(String key, String userId, String companyName, String titlePost, int views, Long postTime,
                    Long expirationTime, String workPlace, com.spkt.nguyenducnguu.jobstore.Models.WorkDetail workDetail,
                    HashMap<String, Apply> applies, HashMap<String, Share> shares, HashMap<String, Save> saves, int status) {
        Key = key;
        UserId = userId;
        CompanyName = companyName;
        TitlePost = titlePost;
        Views = views;
        PostTime = postTime;
        ExpirationTime = expirationTime;
        WorkPlace = workPlace;
        WorkDetail = workDetail;
        Applies = applies;
        Shares = shares;
        Saves = saves;
        Status = status;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getTitlePost() {
        return TitlePost;
    }

    public void setTitlePost(String titlePost) {
        TitlePost = titlePost;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public Long getPostTime() {
        return PostTime;
    }

    public void setPostTime(Long postTime) {
        PostTime = postTime;
    }

    public Long getExpirationTime() {
        return ExpirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        ExpirationTime = expirationTime;
    }

    public String getWorkPlace() {
        return WorkPlace;
    }

    public void setWorkPlace(String workPlace) {
        WorkPlace = workPlace;
    }

    public com.spkt.nguyenducnguu.jobstore.Models.WorkDetail getWorkDetail() {
        return WorkDetail;
    }

    public void setWorkDetail(com.spkt.nguyenducnguu.jobstore.Models.WorkDetail workDetail) {
        WorkDetail = workDetail;
    }

    public HashMap<String, Apply> getApplies() {
        return Applies;
    }

    public void setApplies(HashMap<String, Apply> applies) {
        Applies = applies;
    }

    public HashMap<String, Share> getShares() {
        return Shares;
    }

    public void setShares(HashMap<String, Share> shares) {
        Shares = shares;
    }

    public HashMap<String, Save> getSaves() {
        return Saves;
    }

    public void setSaves(HashMap<String, Save> saves) {
        Saves = saves;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    @Override
    public int compareTo(@NonNull WorkInfo workInfo) {
        return workInfo.getExpirationTime().compareTo(this.ExpirationTime);
    }

    public boolean checkApply(String userId) {
        for (Apply ap : Applies.values())
        {
            if(ap.getUserId().equals(userId)) return true;
        }
        return false;
    }

    public boolean checkShare(String userId) {
        for (Share sh : Shares.values())
        {
            if(sh.getUserId().equals(userId)) return true;
        }
        return false;
    }

    public boolean checkSave(String userId) {
        for (Save sa : Saves.values())
        {
            if(sa.getUserId().equals(userId)) return true;
        }
        return false;
    }

    public boolean isExpired()
    {
        if((ExpirationTime - (new Date()).getTime()) <= 0) return true;
        return false;
    }
}
