package com.stlight.Model;

/**
 * Created by user on 2/11/2018.
 */

public class TimeVo {
    private String startTime="";
    private String endTime="";
    private String dimValue="";
    private String dayList="";

    public String getDimValue() {
        return dimValue;
    }

    public void setDimValue(String dimValue) {
        this.dimValue = dimValue;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDayList() {
        return dayList;
    }

    public void setDayList(String dayList) {
        this.dayList = dayList;
    }


}
