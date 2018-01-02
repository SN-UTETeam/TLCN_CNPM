package com.spkt.nguyenducnguu.jobstore.Models;

import java.util.HashMap;

public class Recruiter {
    private String Key;
    private String Tags;
    private String Email;
    private String FullName;
    private Long BirthDay;
    private int Gender;
    private String CompanyName;
    private String Description;
    private String Phone;
    private String Website;
    private Address Address;
    private String Avatar;
    private String CoverPhoto;
    private Long CreateAt;
    private String DeviceToken;
    private Long LastLogin;
    private int Status;
    private Block Blocked;
    private HashMap<String, Notification> Notifications;
    private HashMap<String, Follow> Follows;

    public Recruiter(String tags, String email, String fullName, Long birthDay, int gender, String companyName,
                     String description, String phone, String website, Address address, String avatar,
                     String coverPhoto, Long createAt, String deviceToken, Long lastLogin, int status,
                     HashMap<String, Notification> notifications, HashMap<String, Follow> follows) {
        Tags = tags;
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
        Notifications = new HashMap<>();
        Follows = new HashMap<>();
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public void addTags(String tags) {
        if(Tags == null || Tags.isEmpty() || Tags.trim().length() == 0)
            Tags += tags.trim();
        else
        {
            String[] arr = Tags.split(",");
            for(String str : arr)
                if(str.toUpperCase().equals(tags.toUpperCase())) return;
            Tags += "," + tags.trim();
        }
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

    public Address getAddress() {
        return Address;
    }

    public void setAddress(Address address) {
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

    public Block getBlocked() {
        return Blocked;
    }

    public void setBlocked(Block blocked) {
        Blocked = blocked;
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

    public boolean checkFollow(String userId)
    {
        for (Follow p : Follows.values())
        {
            if(p.getUserId().equals(userId)) return true;
        }
        return false;
    }
}
