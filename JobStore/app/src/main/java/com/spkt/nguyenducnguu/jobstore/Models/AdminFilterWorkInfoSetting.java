package com.spkt.nguyenducnguu.jobstore.Models;

import android.util.Log;

import java.util.ArrayList;

public class AdminFilterWorkInfoSetting {
    public final int ALL = 0;
    public final int UNEXPIRED = 1;
    public final int EXPIRED = 2;
    public static final String WORKPLACES = "WorkPlaces";
    public static final String CAREERS = "Careers";
    public static final String WORKTYPES = "WorkTypes";

    private String Query;
    private int Status;
    private ArrayList<String> WorkPlaces;
    private ArrayList<String> Careers;
    private ArrayList<String> WorkTypes;
    private Long From;
    private Long To;

    public AdminFilterWorkInfoSetting() {
        this.Query = "";
        this.Status = ALL;
        this.WorkPlaces = new ArrayList<>();
        this.Careers = new ArrayList<>();
        this.WorkTypes = new ArrayList<>();
        this.From = null;
        this.To = null;
    }

    public boolean checkWorkPlaces(String str) {
        if(WorkPlaces.size() == 0 || str.isEmpty() || str.trim().length() == 0) return true;
        String[] arr = str.split(",");
        for (String s : arr) {
            if(WorkPlaces.indexOf(s.trim()) != -1) return true;
        }
        return false;
    }

    public boolean checkCareers(String str) {
        if(Careers.size() == 0 || str.isEmpty() || str.trim().length() == 0) return true;
        String[] arr = str.split(",");
        for (String s : arr) {
            if(Careers.indexOf(s.trim()) != -1) return true;
        }
        return false;
    }

    public boolean checkWorkTypes(String str) {
        if(WorkTypes.size() == 0 || str.isEmpty() || str.trim().length() == 0) return true;
        String[] arr = str.split(",");
        for (String s : arr) {
            if(WorkTypes.indexOf(s.trim()) != -1) return true;
        }
        return false;
    }

    public boolean checkDate(Long postDate) {
        if((From == null && To == null) || postDate == null) return true;
        if(From == null) return (To - postDate) >= 0 ? true : false;
        if(To == null) return (From - postDate) <= 0 ? true : false;
        return ((To - postDate) >= 0) && ((From - postDate) <= 0);
    }

    public boolean isAll() {
        return this.Status == ALL ? true : false;
    }

    public boolean isUnexpired() {
        return this.Status == UNEXPIRED ? true : false;
    }

    public boolean isExpired() {
        return this.Status == EXPIRED ? true : false;
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public ArrayList<String> getWorkPlaces() {
        return WorkPlaces;
    }

    public String[] getWorkPlacesArray() {
        String[] workPlacesArr = new String[WorkPlaces.size()];
        workPlacesArr = WorkPlaces.toArray(workPlacesArr);
        return workPlacesArr;
    }

    public void setWorkPlaces(String workPlaces) {
        WorkPlaces.clear();
        String[] arrStr = workPlaces.split(",");
        for (String str : arrStr) {
            if(str.trim().length() == 0) continue;
            WorkPlaces.add(str.trim());
        }
    }

    public ArrayList<String> getCareers() {
        return Careers;
    }

    public String[] getCareersArray() {
        String[] careersArr = new String[Careers.size()];
        careersArr = Careers.toArray(careersArr);
        return careersArr;
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

    public String[] getWorkTypesArray() {
        String[] workTypesArr = new String[WorkTypes.size()];
        workTypesArr = WorkTypes.toArray(workTypesArr);
        return workTypesArr;
    }

    public void setWorkTypes(String workTypes) {
        WorkTypes.clear();
        String[] arrStr = workTypes.split(", ");
        for (String str : arrStr) {
            if(str.trim().length() == 0) continue;
            WorkTypes.add(str.trim());
        }
    }

    public Long getFrom() {
        return From;
    }

    public void setFrom(Long from) {
        From = from;
    }

    public Long getTo() {
        return To;
    }

    public void setTo(Long to) {
        To = to;
    }
}
