package com.spkt.nguyenducnguu.jobstore.Models;

import java.util.HashMap;

public class CandidateDetail {

    private String Tag;
    private String WorkPlaces;
    private String WorkTypes;
    private String Careers;
    private String Level;
    private String Experience;
    private String Salary;
    private HashMap<String, WorkExp> WorkExps;
    private HashMap<String, Diploma> Diplomas;
    private String CV;

    public CandidateDetail() {
        WorkExps = new HashMap<>();
        Diplomas = new HashMap<>();
    }

    public CandidateDetail(String tag, String workPlaces, String workTypes, String careers,
                           String level, String experience, String salary,
                           HashMap<String, WorkExp> workExps, HashMap<String, Diploma> diplomas, String CV) {
        Tag = tag;
        WorkPlaces = workPlaces;
        WorkTypes = workTypes;
        Careers = careers;
        Level = level;
        Experience = experience;
        Salary = salary;
        WorkExps = workExps;
        Diplomas = diplomas;
        this.CV = CV;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getWorkPlaces() {
        return WorkPlaces;
    }

    public void setWorkPlaces(String workPlaces) {
        WorkPlaces = workPlaces;
    }

    public String getWorkTypes() {
        return WorkTypes;
    }

    public void setWorkTypes(String workTypes) {
        WorkTypes = workTypes;
    }

    public String getCareers() {
        return Careers;
    }

    public void setCareers(String careers) {
        Careers = careers;
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

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public HashMap<String, WorkExp> getWorkExps() {
        return WorkExps;
    }

    public void setWorkExps(HashMap<String, WorkExp> workExps) {
        WorkExps = workExps;
    }

    public HashMap<String, Diploma> getDiplomas() {
        return Diplomas;
    }

    public void setDiplomas(HashMap<String, Diploma> diplomas) {
        Diplomas = diplomas;
    }

    public String getCV() {
        return CV;
    }

    public void setCV(String CV) {
        this.CV = CV;
    }
}
