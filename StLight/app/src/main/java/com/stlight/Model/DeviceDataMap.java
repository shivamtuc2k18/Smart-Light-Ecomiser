package com.stlight.Model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by user on 2/10/2018.
 */

public class DeviceDataMap implements Serializable{
    private LinkedHashMap<String, String> deviceData;

    public LinkedHashMap<String, String> getDeviceData() {
        return deviceData;
    }
    public void setDeviceData(LinkedHashMap<String, String> deviceData) {
        this.deviceData = deviceData;
    }


}
