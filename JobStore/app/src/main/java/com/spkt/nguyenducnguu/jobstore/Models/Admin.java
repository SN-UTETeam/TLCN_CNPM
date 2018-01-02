package com.spkt.nguyenducnguu.jobstore.Models;

import java.util.HashMap;

public class Admin {
    private String Key;
    private String Email;
    private String FullName;
    private Long BirthDay;
    private int Gender;
    private String Phone;
    private String City;
    private Address Address;
    private String Avatar;
    private String CoverPhoto;
    private Long CreateAt;
    private String DeviceToken;
    private Long LastLogin;
    private int Status;
    private HashMap<String, Notification> Notifications;

    public Admin() {
        Notifications = new HashMap<>();
    }

    public Admin(String key, String email, String fullName, Long birthDay, int gender,
                 String phone, String city, com.spkt.nguyenducnguu.jobstore.Models.Address address,
                 String avatar, String coverPhoto, Long createAt, String deviceToken, Long lastLogin,
                 int status, HashMap<String, Notification> notifications) {
        Key = key;
        Email = email;
        FullName = fullName;
        BirthDay = birthDay;
        Gender = gender;
        Phone = phone;
        City = city;
        Address = address;
        Avatar = avatar;
        CoverPhoto = coverPhoto;
        CreateAt = createAt;
        DeviceToken = deviceToken;
        LastLogin = lastLogin;
        Status = status;
        Notifications = notifications;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public com.spkt.nguyenducnguu.jobstore.Models.Address getAddress() {
        return Address;
    }

    public void setAddress(com.spkt.nguyenducnguu.jobstore.Models.Address address) {
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

    public HashMap<String, Notification> getNotifications() {
        return Notifications;
    }

    public void setNotifications(HashMap<String, Notification> notifications) {
        Notifications = notifications;
    }
}
