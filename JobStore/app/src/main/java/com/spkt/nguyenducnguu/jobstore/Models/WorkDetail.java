package com.spkt.nguyenducnguu.jobstore.Models;

public class WorkDetail {
    private String WorkTypes;
    private String Carrers;
    private String Level;
    private String Experience;
    private String Title;
    private int Number;
    private String JobDescription;
    private String JobRequired;
    private String Welfare;
    private String Salary;

    public WorkDetail() {
    }

    public WorkDetail(String workTypes, String carrers, String level, String experience,
                      String title, int number, String jobDescription, String jobRequired,
                      String welfare, String salary) {
        WorkTypes = workTypes;
        Carrers = carrers;
        Level = level;
        Experience = experience;
        Title = title;
        Number = number;
        JobDescription = jobDescription;
        JobRequired = jobRequired;
        Welfare = welfare;
        Salary = salary;
    }

    public String getWorkTypes() {
        return WorkTypes;
    }

    public void setWorkTypes(String workTypes) {
        WorkTypes = workTypes;
    }

    public String getCarrers() {
        return Carrers;
    }

    public void setCarrers(String carrers) {
        Carrers = carrers;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getJobDescription() {
        return JobDescription;
    }

    public void setJobDescription(String jobDescription) {
        JobDescription = jobDescription;
    }

    public String getJobRequired() {
        return JobRequired;
    }

    public void setJobRequired(String jobRequired) {
        JobRequired = jobRequired;
    }

    public String getWelfare() {
        return Welfare;
    }

    public void setWelfare(String welfare) {
        Welfare = welfare;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }
}
