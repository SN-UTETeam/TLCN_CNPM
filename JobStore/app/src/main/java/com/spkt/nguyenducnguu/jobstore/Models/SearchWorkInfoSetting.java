package com.spkt.nguyenducnguu.jobstore.Models;

import java.util.ArrayList;

public class SearchWorkInfoSetting {
    private String Query;
    private ArrayList<String> WorkPlaces;
    private ArrayList<String> Careers;
    private ArrayList<String> WorkTypes;
    private String Salary;
    private String Experience;
    private String Level;

    public SearchWorkInfoSetting() {
        this.Query = "";
        this.WorkPlaces = new ArrayList<>();
        this.Careers = new ArrayList<>();
        this.WorkTypes = new ArrayList<>();
        Salary = "";
        Experience = "";
        Level = "";
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }

    public ArrayList<String> getWorkPlaces() {
        return WorkPlaces;
    }

    public void setWorkPlaces(ArrayList<String> workPlaces) {
        WorkPlaces = workPlaces;
    }

    public ArrayList<String> getCareers() {
        return Careers;
    }

    public void setCareers(ArrayList<String> careers) {
        Careers = careers;
    }

    public ArrayList<String> getWorkTypes() {
        return WorkTypes;
    }

    public void setWorkTypes(ArrayList<String> workTypes) {
        WorkTypes = workTypes;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }
}
