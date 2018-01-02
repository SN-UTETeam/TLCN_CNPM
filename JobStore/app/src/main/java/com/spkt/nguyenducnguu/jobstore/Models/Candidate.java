package com.spkt.nguyenducnguu.jobstore.Models;

import java.util.HashMap;

public class Candidate {
    private String Key;
    private String Email;
    private String FullName;
    private Long Birthday;
    private int Gender;
    private String Description;
    private String Phone;
    private String FacebookURL;
    private Address Address;
    private String Avatar;
    private String CoverPhoto;
    private Long CreateAt;
    private String DeviceToken;
    private Long LastLogin;
    private int Status;
    private Block Blocked;
    private CandidateDetail CandidateDetail;
    private HashMap<String, Notification> Notifications;
    private HashMap<String, Follow> Follows;

    public Candidate() {
        Notifications = new HashMap<>();
        Follows = new HashMap<>();
    }

    public Candidate(String email, String fullName, Long birthday, int gender, String phone, String facebookURL,
                     Address address, String description, String avatar, String coverPhoto, Long createAt,
                     String deviceToken, Long lastLogin, int status, CandidateDetail candidateDetail,
                     HashMap<String, Notification> notifications, HashMap<String, Follow> follows) {
        this.Email = email;
        this.FullName = fullName;
        this.Birthday = birthday;
        this.Gender = gender;
        this.Phone = phone;
        this.FacebookURL = facebookURL;
        this.Address = address;
        this.Description = description;
        this.Avatar = avatar;
        this.CoverPhoto = coverPhoto;
        this.CreateAt = createAt;
        this.DeviceToken = deviceToken;
        this.LastLogin = lastLogin;
        this.Status = status;
        this.CandidateDetail = candidateDetail;
        this.Notifications = notifications;
        this.Follows = follows;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public Long getBirthday() {
        return Birthday;
    }

    public void setBirthday(Long birthday) {
        Birthday = birthday;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFacebookURL() {
        return FacebookURL;
    }

    public void setFacebookURL(String facebookURL) {
        FacebookURL = facebookURL;
    }

    public com.spkt.nguyenducnguu.jobstore.Models.Address getAddress() {
        return Address;
    }

    public void setAddress(com.spkt.nguyenducnguu.jobstore.Models.Address address) {
        Address = address;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getCoverPhoto() {
        return CoverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        CoverPhoto = coverPhoto;
    }

    public Long getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(Long createAt) {
        CreateAt = createAt;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    public Long getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        LastLogin = lastLogin;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public Block getBlocked() {
        return Blocked;
    }

    public void setBlocked(Block blocked) {
        Blocked = blocked;
    }

    public com.spkt.nguyenducnguu.jobstore.Models.CandidateDetail getCandidateDetail() {
        return CandidateDetail;
    }

    public void setCandidateDetail(com.spkt.nguyenducnguu.jobstore.Models.CandidateDetail candidateDetail) {
        CandidateDetail = candidateDetail;
    }

    public HashMap<String, Notification> getNotifications() {
        return Notifications;
    }

    public void setNotifications(HashMap<String, Notification> notifications) {
        Notifications = notifications;
    }

    public HashMap<String, Follow> getFollows() {
        return Follows;
    }

    public void setFollows(HashMap<String, Follow> follows) {
        Follows = follows;
    }
}
