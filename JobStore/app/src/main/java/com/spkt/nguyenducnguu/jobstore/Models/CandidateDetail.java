package com.spkt.nguyenducnguu.jobstore.Models;

import java.util.List;

/**
 * Created by TranAnhSon on 9/30/2017.
 */

public class CandidateDetail {

    private String Title;
    private Experience experience;
    private Salary salary;
    private Level level;
    private WorkExp workExp;
    private WorkPlace workPlace;
    private WorkType workType;
    private List<Career> Careers;

    public CandidateDetail() {
    }

    public CandidateDetail(String title, Experience experience, Salary salary, Level level, WorkExp workExp, WorkPlace workPlace, WorkType workType, List<Career> careers) {
        Title = title;
        this.experience = experience;
        this.salary = salary;
        this.level = level;
        this.workExp = workExp;
        this.workPlace = workPlace;
        this.workType = workType;
        Careers = careers;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public WorkExp getWorkExp() {
        return workExp;
    }

    public void setWorkExp(WorkExp workExp) {
        this.workExp = workExp;
    }

    public WorkPlace getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(WorkPlace workPlace) {
        this.workPlace = workPlace;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public List<Career> getCareers() {
        return Careers;
    }

    public void setCareers(List<Career> careers) {
        Careers = careers;
    }
}
