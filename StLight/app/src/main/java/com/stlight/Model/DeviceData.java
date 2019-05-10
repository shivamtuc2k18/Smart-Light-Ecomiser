package com.stlight.Model;


import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by user on 3/8/2018.
 */
@RealmClass
public class DeviceData extends RealmObject {
    private String phoneNumber;
    private String deviceNumber;
    private String deviceName;

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

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }




}
