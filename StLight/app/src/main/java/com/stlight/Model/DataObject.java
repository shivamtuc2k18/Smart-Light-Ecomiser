package com.stlight.Model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class DataObject extends RealmObject {
    private String mExpected;
    private String mActual;
    private String mSavings;
    private String mDateAndTime;
    private Date date;
    private String dateInMills;
    private String Id;
    private boolean IsActive;
    private int active;
    private int inActive;
    private String deviceNumber;
    //Nikhil's Code
    private String mDName;
    private String mTLO;
    private String mMT;
    //----------------

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }



    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getInActive() {
        return inActive;
    }

    public void setInActive(int inActive) {
        this.inActive = inActive;
    }


    public boolean getIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean active) {
        IsActive = active;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


    public String getmExpected() {
        return mExpected;
    }

    public void setmExpected(String mExpected) {
        this.mExpected = mExpected;
    }

    public String getmActual() {
        return mActual;
    }

    public void setmActual(String mActual) {
        this.mActual = mActual;
    }

    public String getmSavings() {
        return mSavings;
    }

    public void setmSavings(String mSavings) {
        this.mSavings = mSavings;
    }

    //Nikhil's code
    public String getmDName() {
        return mDName;
    }

    public void setmDName(String mDName) {
        this.mDName = mDName;
    }

    public String getmTLO() {
        return mTLO;
    }

    public void setmTLO(String mTLO) {
        this.mTLO = mTLO;
    }

    public String getmMT() {
        return mMT;
    }

    public void setmMT(String mMT) {
        this.mMT = mMT;
    }
//---------------

    public String getmDateAndTime() {
        return mDateAndTime;
    }

    public void setmDateAndTime(String mDateAndTime) {
        this.mDateAndTime = mDateAndTime;
    }
    public String getDateInMills() {
        return dateInMills;
    }

    public void setDateInMills(String dateInMills) {
        this.dateInMills = dateInMills;
    }


}