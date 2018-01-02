package com.spkt.nguyenducnguu.jobstore.Models;

import com.spkt.nguyenducnguu.jobstore.Const.AccountType;
import com.spkt.nguyenducnguu.jobstore.Const.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AdminFilterAccountSetting {
    public static final String ALL = "All";
    public static final String ACTIVE = "Active";
    public static final String BLOCKED = "Blocked";
    public static final String PENDING = "Pending";
    public static final String BEGINDATE = "BeginDate";
    public static final String FINISHDATE = "FinishDate";

    private boolean All;
    private String Query;
    private boolean Active;
    private boolean Blocked;
    private boolean Pending;
    private Long BeginDate;
    private Long FinishDate;

    public AdminFilterAccountSetting() {
        All = true;
        Query = "";
        Active = true;
        Blocked = true;
        Pending = true;
        BeginDate = null;
        FinishDate = null;
    }

    public boolean checkStatus(int status)
    {
        if(Active && Status.ACTIVE == status) return true;
        if(Blocked && Status.BLOCKED == status) return true;
        if(Pending && Status.PENDING == status) return true;
        return false;
    }
    public boolean checkDate(Long date)
    {
        if((BeginDate == null && FinishDate == null) || date == null) return true;
        if(BeginDate == null) return (FinishDate - date) >= 0 ? true : false;
        if(FinishDate == null) return (BeginDate - date) <= 0 ? true : false;
        return ((FinishDate - date) >= 0) && ((BeginDate - date) <= 0);
    }

    public boolean isAll() {
        return All;
    }

    public void setAll(boolean all) {
        All = all;
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public boolean isBlocked() {
        return Blocked;
    }

    public void setBlocked(boolean blocked) {
        Blocked = blocked;
    }

    public boolean isPending() {
        return Pending;
    }

    public void setPending(boolean pending) {
        Pending = pending;
    }

    public Long getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(Long beginDate) {
        BeginDate = beginDate;
    }

    public Long getFinishDate() {
        return FinishDate;
    }

    public void setFinishDate(Long finishDate) {
        FinishDate = finishDate;
    }
}
