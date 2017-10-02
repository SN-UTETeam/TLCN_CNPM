package com.spkt.nguyenducnguu.jobstore.Models;

/**
 * Created by TranAnhSon on 9/30/2017.
 */

public class Diploma {
    private String Name;
    private Long Begin;
    private Long Finish;
    private String Rating;

    public Diploma() {
    }

    public Diploma(String name, Long begin, Long finish, String rating) {
        Name = name;
        Begin = begin;
        Finish = finish;
        Rating = rating;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }
}
