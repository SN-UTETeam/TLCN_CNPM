package com.spkt.nguyenducnguu.jobstore.Models;

public class Roles {
    public static int RECRUITER = 0;
    public static int CANDIDATE = 1;
    public static int ADMIN = 2;

    private String Email;
    private int Role;

    public Roles() {
    }

    public Roles(String email, int role) {
        Email = email;
        Role = role;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }
}
