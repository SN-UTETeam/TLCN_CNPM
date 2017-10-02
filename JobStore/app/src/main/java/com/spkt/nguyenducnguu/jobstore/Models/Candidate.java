package com.spkt.nguyenducnguu.jobstore.Models;

import java.util.HashMap;

/**
 * Created by TranAnhSon on 9/30/2017.
 */

public class Candidate {

    private String Email;
    private String FullName;
    private Long Birthday;
    private int Gender;
    private String Phone;
    private String FacebookURL;
    private String Address;
    private String Description;
    private String Avatar;
    private String CoverPhoto;
    private Long CreateAt;
    private String DeviceToken;
    private Long LastLogin;
    private int Status;
    private CandidateDetail candidateDetail;
    private HashMap<String, Notification> Notifications;
    private HashMap<String, Follow> Follows;

    public Candidate() {
    }

    public Candidate(String email, String fullName, Long birthday, int gender, String phone, String facebookURL,
                     String address, String description, String avatar, String coverPhoto, Long createAt,
                     String deviceToken, Long lastLogin, int status, CandidateDetail candidateDetail,
                     HashMap<String, Notification> notifications, HashMap<String, Follow> follows) {
        Email = email;
        FullName = fullName;
        Birthday = birthday;
        Gender = gender;
        Phone = phone;
        FacebookURL = facebookURL;
        Address = address;
        Description = description;
        Avatar = avatar;
        CoverPhoto = coverPhoto;
        CreateAt = createAt;
        DeviceToken = deviceToken;
        LastLogin = lastLogin;
        Status = status;
        this.candidateDetail = candidateDetail;
        Notifications = notifications;
        Follows = follows;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
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

    public CandidateDetail getCandidateDetail() {
        return candidateDetail;
    }

    public void setCandidateDetail(CandidateDetail candidateDetail) {
        this.candidateDetail = candidateDetail;
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
