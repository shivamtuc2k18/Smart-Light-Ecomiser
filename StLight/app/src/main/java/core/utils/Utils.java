package core.utils;

import com.stlight.Model.DeviceData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by user on 2/13/2018.
 */

public class Utils {

    public  static boolean Valid(String s){
        if(s!=null && s.length()>0)
            return true;
        else
            return false;


    }

    public static ArrayList getDeviceDataList(RealmResults<DeviceData> deviceDataObject){
        ArrayList  deviceDataList = new ArrayList();
        for (int i = 0; i < deviceDataObject.size(); i++) {
            deviceDataList.add(deviceDataObject.get(i).getDeviceNumber());

        }
        deviceDataList.add(0, "Device Name");
        deviceDataList.add(1, "All Devices");
        return deviceDataList;
    }

    public static ArrayList<String> getSimDataList(RealmResults<DeviceData>  deviceDataObject){
        ArrayList<String> deviceList=new ArrayList<>();
        for (int i = 0; i < deviceDataObject.size(); i++) {
            deviceList.add(deviceDataObject.get(i).getPhoneNumber());

        }
        return deviceList;
    }



    public static ArrayList<String> getDeviceNumberList(RealmResults<DeviceData>  deviceDataObject){
         ArrayList<String> deviceList=new ArrayList<>();
        for (int i = 0; i < deviceDataObject.size(); i++) {
            deviceList.add(deviceDataObject.get(i).getDeviceNumber());

        }
        return deviceList;
    }



    public static ArrayList<String> getDeviceNameList(RealmResults<DeviceData>  deviceDataObject){
        ArrayList<String> deviceList=new ArrayList<>();
        for (int i = 0; i < deviceDataObject.size(); i++) {
            deviceList.add(deviceDataObject.get(i).getDeviceName());

        }
        return deviceList;
    }
}
