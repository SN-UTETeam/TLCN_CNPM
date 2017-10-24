package com.spkt.nguyenducnguu.jobstore.Models;

public class WorkExp {
    private Long Id;
    private int YearNumber;
    private Long Begin;
    private Long Finish;
    private String Title;
    private String CompanyName;

    public WorkExp() {
    }

    public WorkExp(Long id, int yearNumber, Long begin, Long finish, String title, String companyName) {
        Id = id;
        YearNumber = yearNumber;
        Begin = begin;
        Finish = finish;
        Title = title;
        CompanyName = companyName;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public int getYearNumber() {
        return YearNumber;
    }

    public void setYearNumber(int yearNumber) {
        YearNumber = yearNumber;
    }

    public Long getBegin() {
        return Begin;
    }

    public void setBegin(Long begin) {
        Begin = begin;
    }

    public Long getFinish() {
        return Finish;
    }

    public void setFinish(Long finish) {
        Finish = finish;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
}
