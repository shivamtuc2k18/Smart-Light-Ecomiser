package com.stlight.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.stlight.Model.DataObject;
import com.stlight.Model.DeviceData;
import com.stlight.R;
import com.stlight.setData.IGetSpinnerData;
import com.stlight.setData.SetSpinnerData;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.BaseActivity;
import core.BottomSheet;
import core.IDateListener;
import core.IPermissionListener;
import core.RealmController;
import core.utils.DateTimeUtils;
import io.realm.Realm;
import io.realm.RealmResults;

public class StatisticsActivity extends BaseActivity implements View.OnClickListener, IPermissionListener,IGetSpinnerData, IDateListener {
    private TextView inactive_text_tv;
    private TextView inactive_tv;
    private ProgressBar progressBarInner;
    private ProgressBar progressBarOuter;
    private Spinner select_device;
    private TextView total_saving_tv;
    private TextView tc_tv;
    private TextView ec_tv;
    private Button activeButton;
    private Button inactiveButton;
    private Realm realm;
    private RealmResults<DataObject> dataObject;

    private DataObject savingspercentage = new DataObject();
    private ArrayList<DataObject> smsList;
    private Double mActual=0.0;
    private Double mExpected=0.0;
    private Double mTotalSavings=0.0;
    public int percentagevalue;
    private List<Integer> percen = new ArrayList<>();
    private IDateListener mIDateListener;

    //Nikhil's Code
    private long mDName=0;
    private long mTLO=0;
    private long mMT=0;
    //-----------



    private int active=0;
    private int inactive=0;
    private int totalDevices=0;
    private Map<String,Boolean> ActiveInactiveMap;
    private DataObject dataObjectID;

    private  RealmResults<DeviceData> deviceDataObject;
    private  ArrayList deviceDataList;
    private  ArrayList DataListAct;
    private  ArrayList DataListExp;
    private  float proportion;
    private  int per;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        init();
    }

    private void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_statistics);
        setSupportActionBar(toolbar);
        toolbar_image = (ImageView) toolbar.findViewById(R.id.toolbar_image);
        toolbar_image.setOnClickListener(this);
        ImageView filter_image = (ImageView) findViewById(R.id.filter_image2);
        filter_image.setOnClickListener(this);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.statistics_header_txt));
        inactive_text_tv=(TextView)findViewById(R.id.inactive_text_tv);
        inactive_tv=(TextView)findViewById(R.id.inactive_tv);
        progressBarInner=(ProgressBar) findViewById(R.id.progressBar_inner);
        progressBarOuter=(ProgressBar) findViewById(R.id.progressBar_outer);
        select_device=(Spinner)findViewById(R.id.select_device);
        total_saving_tv=(TextView)findViewById(R.id.total_saving_tv);
        tc_tv=(TextView)findViewById(R.id.tc_tv);
        ec_tv=(TextView)findViewById(R.id.ec_tv);
        activeButton=(Button)findViewById(R.id.active_button);
        inactiveButton=(Button)findViewById(R.id.inactive_button);
        //get realm instance
        this.realm = RealmController.with(this).getRealm();
        this.ActiveInactiveMap=new HashMap<>();
        mIDateListener=(IDateListener)this;

        homeFooterButton =(Button)findViewById(R.id.home_footer_button);
        homeFooterButton.setOnClickListener(this);
        devicesFooterBtn=(Button)findViewById(R.id.devices_footer_btn);
        devicesFooterBtn.setOnClickListener(this);
        scheduleFooterBtn=(Button)findViewById(R.id.schedule_footer_btn);
        scheduleFooterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_footer_button:
                //startActivity(new Intent(this,DeviceConfigActivity.class));
                break;
            case R.id.devices_footer_btn:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.schedule_footer_btn:
                startActivity(new Intent(this,MessageScheduleActivity.class));
                break;
            case R.id.toolbar_image:
                new BottomSheet().ShowBottomSheet(this,mIDateListener);
                break;
            case R.id.filter_image:

                startActivity(new Intent(this,DeviceConfigActivity.class));
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        resetValues();
        permissionCheck(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS));
    }

    @Override
    public void permissionCheck(boolean b) {
        if(b){
            saveContacts();
        }
    }


    private void saveContacts() {
        deviceDataObject =  RealmController.with(this).getDeviceDataObject();
        Uri inboxURI = Uri.parse("content://sms/inbox");
        smsList = new ArrayList<DataObject>();
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(inboxURI, null, null, null, null);
        if(cursor!=null){
            while (cursor.moveToNext()) {
                String Number = cursor.getString(cursor.getColumnIndexOrThrow("address"));


                //TODO change phone number
                // for(int i=0;i<getDeviceDataList(deviceDataObject).size();i++){
                if (getDeviceDataList(deviceDataObject).contains(Number)) {
                    DataObject mDataObject = new DataObject();

                    String ID = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                    String Body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

                    // ActiveInactiveMap.size()
                    String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                    // message should be semicolon separated ex: 30;40;50;
                    if (Body.contains(";")) {
                        //Nikhil's Code
                        String[] dateSplit = Body.split(";");
                        String[] splittwo = dateSplit[0].split("-");
                        String[] splittwo2 = dateSplit[2].split("-");
                        String[] splittwo3 = dateSplit[3].split("-");
                        String[] splittwo4 = dateSplit[4].split("-");

                        try {
                            mDataObject.setmDName(splittwo[1]);
                            String tlo = splittwo4[1].replaceAll("%", "");
                            String actu = splittwo3[1].replaceAll("V", "");
                            String expec = splittwo2[1].replaceAll("V", "");


                            //Long actuu = Long.parseLong(actu);
                            // Long expecc = Long.parseLong(expec);


                            mExpected += Double.parseDouble(expec);
                            mActual += Double.parseDouble(actu);


                           /* mDataObject.setmActual(actu);
                            mDataObject.setmExpected(expec);
                            mDataObject.setmTLO(tlo);*/

                            Float mt = Float.valueOf(splittwo4[1]);

                            //percen.add(Integer.parseInt(mDataObject.getmTLO()));
                            System.out.println(percen.get(0));
                            int tempvaluelist = percen.get(0);
                            System.out.println(50);
                            System.out.println(tempvaluelist);


//----------------
                        } catch (Exception exp) {

                        }
                    }


                    mDataObject.setmExpected(String.valueOf(mExpected));
                    mDataObject.setmActual(String.valueOf(mActual));
                    mDataObject.setmDateAndTime((DateTimeUtils.getDate(Long.parseLong(date))));
                    mDataObject.setDate(new Date(Long.parseLong(date)));
                    mDataObject.setDateInMills(date);
                    mDataObject.setId(ID);
                    dataObjectID = RealmController.with(this).getDataObject(ID);
                    mDataObject.setDeviceNumber(Number);


                    if (dataObjectID == null) {
                        realm.beginTransaction();
                        realm.copyToRealm(mDataObject);
                        realm.commitTransaction();
                    } else {
                        if (!dataObjectID.getId().equalsIgnoreCase(ID)) {
                            realm.beginTransaction();
                            realm.copyToRealm(mDataObject);
                            realm.commitTransaction();
                        }

                    }




                       /* Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        DataObject dataobj = realm.where(DataObject.class).equalTo("deviceNumber", Number).findFirst();
                        dataobj.setmExpected(String.valueOf(mExpected));
                        dataobj.setmActual(String.valueOf(mActual));
                        // dataobj.setmExpected(String.valueOf(mExpected));
                        realm.commitTransaction();
                        realm.close();


                    mDataObject.setmExpected(String.valueOf(mExpected));
                    mDataObject.setmActual(String.valueOf(mActual));
                    mDataObject.setmDateAndTime((DateTimeUtils.getDate(Long.parseLong(date))));
                    mDataObject.setDate(new Date(Long.parseLong(date)));
                    mDataObject.setDateInMills(date);
                    mDataObject.setId(ID);
                    dataObjectID = RealmController.with(this).getDataObject(ID);
                    mDataObject.setDeviceNumber(Number);


                    if (dataObjectID == null) {
                        realm.beginTransaction();
                        realm.copyToRealm(mDataObject);
                        realm.commitTransaction();
                    } else {
                        if (!dataObjectID.getId().equalsIgnoreCase(ID)) {
                            realm.beginTransaction();
                            realm.copyToRealm(mDataObject);
                            realm.commitTransaction();
                        }

                    }*/


                }
            }
            //}
            cursor.close();
            // RealmResults<DataObject> dataObject=  RealmController.with(this).between();
            dataObject =  RealmController.with(this).getDataObject();
            getSMSList(dataObject);

            IGetSpinnerData spinnerInterface= (IGetSpinnerData)this;
            new SetSpinnerData(getApplicationContext(),select_device,getDeviceDataList(deviceDataObject),spinnerInterface).setSpinnerData();
            getActiveInActive();
        }
    }

    private void getActiveInActive(){
        // if(dataObjectID!=null) {
        activeButton.setText("" + active);
        inactiveButton.setText("" + inactive);
        getPercentage(inactive, totalDevices);

        //}
    }

    @Override
    public void dateListener(long fromDate,long toDate,int filterPosition) {

        if(filterPosition==0)
            dataObject =  RealmController.with(this).getDataObject();
        else if(filterPosition==1)
            dataObject=  RealmController.with(this).today((DateTimeUtils.getDate(fromDate)));
        else
            dataObject=  RealmController.with(this).between(new Date(fromDate),new Date(toDate));

        resetValues();
        getSMSList(dataObject);
        getActiveInActive();
    }

    private void  getSMSList(RealmResults<DataObject> dataObject){

        for (int i = 0; i < dataObject.size(); i++) {
            String pNumber=dataObject.get(i).getDeviceNumber();
            if(DateTimeUtils.getDifferenceInHours(Long.parseLong(dataObject.get(i).getDateInMills()))<=4 && !ActiveInactiveMap.containsKey(pNumber)){
                ActiveInactiveMap.put(pNumber,true);
                // mDataObject.setIsActive(true);
                active= active+1;
                //mDataObject.setActive(active);
            }else{
                if(!ActiveInactiveMap.containsKey(pNumber)) {
                    ActiveInactiveMap.put(pNumber, false);
                    // mDataObject.setIsActive(false);
                    inactive = inactive + 1;
                    //mDataObject.setInActive(inactive);
                }

            }
            try {
                if (dataObject.get(i).getmActual() != null && dataObject.get(i).getmExpected() != null) {
                  /*  mActual = mActual + Double.parseDouble(dataObject.get(i).getmActual());
                    mExpected = mExpected + Double.parseDouble(dataObject.get(i).getmExpected());
                    mTotalSavings = Double.parseDouble(dataObject.get(i).getmTLO());*/
                }
            }catch (Exception e){

            }
        }

        /*total_saving_tv.setText(getResources().getString(R.string.savings) + " " + mTotalSavings);
        tc_tv.setText(getResources().getString(R.string.tc) + " " + mActual);
        ec_tv.setText(getResources().getString(R.string.ec) + " " + mExpected);*/

        //  Collections.sort(smsList, new DateComparator());

    }

    private ArrayList getDeviceDataList(RealmResults<DeviceData>  deviceDataObject){
        deviceDataList = new ArrayList();;
        for (int i = 0; i < deviceDataObject.size(); i++) {
            deviceDataList.add(deviceDataObject.get(i).getDeviceNumber());

        }

        totalDevices=deviceDataList.size();
        deviceDataList.add(0, "Device Name");
        return deviceDataList;
    }

    @Override
    public void getPosition(int position) {
        if(deviceDataList!=null && deviceDataList.size()>0) {
            resetValues();
            totalDevices=1;
            dataObject = RealmController.with(this).deviceNumber(deviceDataList.get(position+1).toString());
            getSMSList(dataObject);
            getActiveInActive();
        }else{


        }
    }


    public  void getPercentage(int inactiveDevices, int totalDevices) {
        proportion = ((float) inactiveDevices) / ((float) totalDevices);
        //progressBarOuter.setMax(totalDevices);
        per=(int)(Math.round(mTotalSavings));
        progressBarOuter.setProgress(per);
        progressBarOuter.setMax(100);
        inactive_tv.setText(String.valueOf(per)+"%");
    }


    private void resetValues(){
        mActual=0.0;
        mExpected=0.0;
        mTotalSavings=0.0;
        active=0;
        inactive=0;
        ActiveInactiveMap.clear();
        proportion=0f;
        per=0;
        totalDevices=0;
    }
}
