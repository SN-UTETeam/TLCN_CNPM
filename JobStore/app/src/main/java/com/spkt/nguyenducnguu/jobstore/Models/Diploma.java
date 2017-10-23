package com.spkt.nguyenducnguu.jobstore.Models;

public class Diploma {
    private Long Id;
    private String Name;
    private String IssuedBy;
    private Long IssuedDate;
    private float Scores;
    private String Ranking;

    public Diploma() {
    }

    public Diploma(Long id, String name, String issuedBy, Long issuedDate, float scores, String ranking) {
        Id = id;
        Name = name;
        IssuedBy = issuedBy;
        IssuedDate = issuedDate;
        Scores = scores;
        Ranking = ranking;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIssuedBy() {
        return IssuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        IssuedBy = issuedBy;
    }

    public Long getIssuedDate() {
        return IssuedDate;
    }

    public void setIssuedDate(Long issuedDate) {
        IssuedDate = issuedDate;
    }

    public float getScores() {
        return Scores;
    }

    public void setScores(float scores) {
        Scores = scores;
    }

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String ranking) {
        Ranking = ranking;
    }
}
