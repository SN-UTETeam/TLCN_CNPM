package com.spkt.nguyenducnguu.jobstore.Models;

import java.util.Date;

public class Block {
    private boolean Permanent;
    private int Time;
    private Long FinishDate;
    private String Content;

    public Block() {
        Permanent = false;
        Time = -1;
        FinishDate = null;
        Content = "";
    }

    public Block(boolean permanent, int time, Long finishDate, String content) {
        Permanent = permanent;
        Time = time;
        FinishDate = finishDate;
        Content = content;
    }

    public boolean isPermanent() {
        return Permanent;
    }

    public void setPermanent(boolean permanent) {
        Permanent = permanent;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public Long getFinishDate() {
        return FinishDate;
    }

    public void setFinishDate(Long finishDate) {
        FinishDate = finishDate;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
    public boolean isActive()
    {
        if(Permanent) return false;
        if((FinishDate - System.currentTimeMillis()) <= 0) return true;
        return false;
    }

    public static String getDistanceTime(long createdTime) {
        long distance=(createdTime) - (System.currentTimeMillis());
        String str = "";
        final long SEC=1000;
        final long MIN=60*SEC;
        final long HOUR=60*MIN;
        final long DAY=24*HOUR;

        if (distance/DAY>0){
            str += distance/DAY + " ngày ";
            distance = distance - (distance/DAY)*DAY;
        }
        if (distance/HOUR>0){
            str += distance/HOUR + " giờ ";
            distance = distance - (distance/HOUR)*HOUR;
        }
        if(distance/MIN>0){
            str += distance/MIN + " phút ";
        }
        return " " + str.trim();
    }
}
