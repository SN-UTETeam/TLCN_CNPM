package com.spkt.nguyenducnguu.jobstore.Models;

import android.support.annotation.NonNull;

import java.util.HashMap;

public class WorkInfo implements Comparable<WorkInfo>{
    private String Key = "";
    private String Email;
    private String CompanyName;
    private String TitlePost;
    private int Views;
    private Long PostTime;
    private Long ExpirationTime;
    private String WorkPlace;
    private WorkDetail WorkDetail;
    private HashMap<String, Apply> Applies;
    private HashMap<String, Share> Shares;
    private int Status;

    public WorkInfo(){
        Applies = new HashMap<>();
        Shares = new HashMap<>();
    }

    public WorkInfo(String email, String companyName, String titlePost, int views, Long postTime,
                    Long expirationTime, String workPlace,
                    com.spkt.nguyenducnguu.jobstore.Models.WorkDetail workDetail,
                    HashMap<String, Apply> applies, HashMap<String, Share> shares, int status) {
        Email = email;
        CompanyName = companyName;
        TitlePost = titlePost;
        Views = views;
        PostTime = postTime;
        ExpirationTime = expirationTime;
        WorkPlace = workPlace;
        WorkDetail = workDetail;
        Applies = applies;
        Shares = shares;
        Status = status;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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
}
