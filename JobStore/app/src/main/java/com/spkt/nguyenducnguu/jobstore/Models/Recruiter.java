package com.spkt.nguyenducnguu.jobstore.Models;

import java.util.ArrayList;
import java.util.List;

public class Recruiter {
    private String Email;
    private String FullName;
    private Long BirthDay;
    private int Gender;
    private String CompanyName;
    private String Description;
    private String Phone;
    private String Website;
    private String Address;
    private String Avatar;
    private String CoverPhoto;
    private Long CreateAt;
    private String DeviceToken;
    private Long LastLogin;
    private int Status;
    List<Notification> Notifications;
    List<Follow> Follows;

    public Recruiter(String email, String fullName, Long birthDay, int gender, String companyName,
                     String description, String phone, String website, String address, String avatar,
                     String coverPhoto, Long createAt, String deviceToken, Long lastLogin, int status,
                     List<Notification> notifications, List<Follow> follows) {
        Email = email;
        FullName = fullName;
        BirthDay = birthDay;
        Gender = gender;
        CompanyName = companyName;
        Description = description;
        Phone = phone;
        Website = website;
        Address = address;
        Avatar = avatar;
        CoverPhoto = coverPhoto;
        CreateAt = createAt;
        DeviceToken = deviceToken;
        LastLogin = lastLogin;
        Status = status;
        Notifications = notifications;
        Follows = follows;
    }

    public Recruiter() {
        Notifications = new ArrayList<Notification>();
        Follows = new ArrayList<Follow>();
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

    public Long getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(Long birthDay) {
        BirthDay = birthDay;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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

    public List<Notification> getNotifications() {
        return Notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        Notifications = notifications;
    }

    public List<Follow> getFollows() {
        return Follows;
    }

    public void setFollows(List<Follow> follows) {
        Follows = follows;
    }
}
