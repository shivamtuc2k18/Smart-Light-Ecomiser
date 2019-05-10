package com.stlight.Model;

import java.util.List;

/**
 * Created by user on 2/10/2018.
 */

public class MessageScheduleData {

    private String phoneNumber;
    private String deviceName;
    private String deviceNumber;
    private String startDate;
    private String endDate;

    private List<TimeVo> timeSlotListData;
    private List<DayVo> dayVoList;

    public List<DayVo> getDayVoList() {
        return dayVoList;
    }

    public void setDayVoList(List<DayVo> dayVoList) {
        this.dayVoList = dayVoList;
    }

    public List<TimeVo> getTimeSlotListData() {
        return timeSlotListData;
    }

    public void setTimeSlotListData(List<TimeVo> timeSlotListData) {
        this.timeSlotListData = timeSlotListData;
    }


    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }




}
