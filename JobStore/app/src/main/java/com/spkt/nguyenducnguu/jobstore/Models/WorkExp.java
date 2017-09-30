package com.spkt.nguyenducnguu.jobstore.Models;

/**
 * Created by TranAnhSon on 9/30/2017.
 */

public class WorkExp {
    private int YearNumber;
    private String Fields;
    private String Title;
    private String CompanyName;

    public WorkExp() {
    }

    public WorkExp(int yearNumber, String fields, String title, String companyName) {
        YearNumber = yearNumber;
        Fields = fields;
        Title = title;
        CompanyName = companyName;
    }

    public int getYearNumber() {
        return YearNumber;
    }

    public void setYearNumber(int yearNumber) {
        YearNumber = yearNumber;
    }

    public String getFields() {
        return Fields;
    }

    public void setFields(String fields) {
        Fields = fields;
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
