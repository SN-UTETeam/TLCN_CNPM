package com.spkt.nguyenducnguu.jobstore.Models;

import java.util.ArrayList;

public class SearchWorkInfoSetting {
    public static final String WORKPLACES = "WorkPlaces";
    public static final String CAREERS = "Careers";
    public static final String WORKTYPES = "WorkTypes";
    public static final String SALARY = "Salary";
    public static final String EXPERIENCE = "Experience";
    public static final String LEVEL = "Level";

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

    public void setWorkPlaces(String workPlaces) {
        WorkPlaces.clear();
        String[] arrStr = workPlaces.split(", ");
        for (String str : arrStr) {
            if(str.trim().length() == 0) continue;
            WorkPlaces.add(str.trim());
        }
    }

    public ArrayList<String> getCareers() {
        return Careers;
    }

    public void setCareers(ArrayList<String> careers) {
        Careers = careers;
    }

    public void setCareers(String careers) {
        Careers.clear();
        String[] arrStr = careers.split(", ");
        for (String str : arrStr) {
            if(str.trim().length() == 0) continue;
            Careers.add(str.trim());
        }
    }

    public ArrayList<String> getWorkTypes() {
        return WorkTypes;
    }

    public void setWorkTypes(ArrayList<String> workTypes) {
        WorkTypes = workTypes;
    }

    public void setWorkTypes(String workTypes) {
        WorkTypes.clear();
        String[] arrStr = workTypes.split(", ");
        for (String str : arrStr) {
            if(str.trim().length() == 0) continue;
            WorkTypes.add(str.trim());
        }
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
