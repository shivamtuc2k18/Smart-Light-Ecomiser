package core;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.stlight.Model.DataObject;
import com.stlight.Model.DeviceData;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmMigration;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm instance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from DataObject.class
    public void clearAll() {

        realm.beginTransaction();
        //realm.clear(DataObject.class);
        realm.commitTransaction();
    }

    //find all objects in the DeviceData.class
    public RealmResults<DeviceData> getDeviceDataObject() {

        return realm.where(DeviceData.class).findAll();
    }

    //find all objects in the DataObject.class
    public RealmResults<DataObject> getDataObject() {

        return realm.where(DataObject.class).findAll();
    }

    public RealmResults<DataObject>  getAllDeviceNumber(String deviceNumber){
        return realm.where(DataObject.class).equalTo("deviceNumber", deviceNumber).findAll();
    }


    public RealmResults<DeviceData>  getAllScheduleDeviceNumber(String deviceNumber){
        return realm.where(DeviceData.class).equalTo("deviceNumber", deviceNumber).findAll();
    }

    public RealmResults<DeviceData>  getAllSimNumber(String phoneNumber){
        return realm.where(DeviceData.class).equalTo("phoneNumber", phoneNumber).findAll();
    }

    public RealmResults<DeviceData>  getAllDeviceName(String deviceName){
        return realm.where(DeviceData.class).equalTo("deviceName", deviceName).findAll();
    }

    public RealmResults<DataObject> between(Date fDate,Date tDate){
        return realm.where(DataObject.class).between("date", fDate,tDate).findAll();
    }

    public RealmResults<DataObject>  today(String fDate){
        return realm.where(DataObject.class).equalTo("mDateAndTime", fDate).findAll();
    }

    public RealmResults<DataObject>  deviceNumber(String number){
        return realm.where(DataObject.class).equalTo("deviceNumber", number).findAll();
    }

    //query a single item with the given id
    public DataObject getDataObject(String id) {

        return realm.where(DataObject.class).equalTo("Id", id).findFirst();
    }

    //find all objects in the DeviceData.class
    public DeviceData getDeviceDataID(String id) {

        return realm.where(DeviceData.class).equalTo("phoneNumber", id).findFirst();
    }



    //check if DataObject.class is empty
   /* public boolean hasDataObject() {

        return !realm.allObjects(DataObject.class).isEmpty();
    }*/

    //query example
    public RealmResults<DataObject> queryedDataObject() {

        return realm.where(DataObject.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}