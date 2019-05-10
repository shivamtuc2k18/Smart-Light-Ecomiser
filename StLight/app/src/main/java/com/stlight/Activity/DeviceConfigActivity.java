package com.stlight.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;

import com.stlight.Model.DeviceData;
import com.stlight.R;
import com.stlight.setData.IScheduleSpinnerData;
import com.stlight.setData.ScheduleSpinnerData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import core.BaseActivity;
import core.RealmController;
import core.utils.AppConstant;
import core.utils.Utils;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.Calendar;
import java.util.Date;

public class DeviceConfigActivity extends BaseActivity implements View.OnClickListener,IScheduleSpinnerData {

    private Spinner spinner_select_phone;
    private Spinner spinner_select_device;
    private Spinner spinner_select_type;
    private Spinner spinner_select_Hour;
    private RealmResults<DeviceData> deviceDataObject;
    private ArrayList NameList;
    private ArrayList NumberList;
    private String deviceName="";
    private String deviceNumber="";
    private ArrayList configType=new ArrayList();
    private IScheduleSpinnerData spinnerDataInterface;
    private EditText config_device_number_edt;
    private EditText config_device_name_edt;
    private EditText config_device_old_Number_edt;
    private EditText config_device_number_dlt;
    private EditText config_device_number_add;
    private Button submit_btn;
    private Button add_new_btn;
    private DeviceData mDeviceData=new DeviceData();
    private int NumberPosition=-1;
    private int NamePosition=-1;
    private Realm realm;
    private Boolean DeviceStatus =null;
    private ArrayList configHour = new ArrayList();
    private String HourValue ="";
    private int Dropdownposition =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.realm = RealmController.with(this).getRealm();
        setContentView(R.layout.activity_device_config);
        deviceDataObject =  RealmController.with(this).getDeviceDataObject();
        if(deviceDataObject!=null){
              /* simList=Utils.getSimDataList(deviceDataObject);
               simList.add(0,"Sim Number");
*/
            getNameList();
            getNumberList();

        }
        configType.add("Type");
        configType.add("Add");
        configType.add("Update");
        configType.add("Delete");
        configType.add("Reset Time");
        configType.add("On");
        configType.add("Off");
        configType.add("Ping");

        configHour.add("01:00");
        configHour.add("04:00");
        configHour.add("08:00");
        configHour.add("12:00");
        configHour.add("16:00");
        configHour.add("20:00");
        configHour.add("24:00");



        init();
    }

    private void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_device_config);
        setSupportActionBar(toolbar);
        toolbar_image = (ImageView) toolbar.findViewById(R.id.toolbar_image);
        toolbar_image.setVisibility(View.GONE);
        toolbar_image.setOnClickListener(this);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.device_config_title));
        spinnerDataInterface=(IScheduleSpinnerData) this;
        config_device_number_edt=(EditText)findViewById(R.id.config_device_number_edt);
        config_device_name_edt=(EditText)findViewById(R.id.config_device_name_edt);
        config_device_old_Number_edt= (EditText)findViewById(R.id.config_device_old_Number_edt);
        config_device_number_dlt = (EditText)findViewById(R.id.config_device_number_dlt);
        config_device_number_add = (EditText)findViewById(R.id.config_device_number_add);

        spinner_select_Hour = (Spinner)findViewById(R.id.spinner_select_Hour);
        submit_btn=(Button)findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);
        spinner_select_phone=(Spinner)findViewById(R.id.spinner_select_phone);
        new ScheduleSpinnerData(getApplicationContext(),spinner_select_phone,NumberList,spinnerDataInterface).setSpinnerData();
        spinner_select_device=(Spinner)findViewById(R.id.spinner_select_device);
        new ScheduleSpinnerData(getApplicationContext(),spinner_select_device,NameList,spinnerDataInterface).setSpinnerData();
        spinner_select_type=(Spinner)findViewById(R.id.spinner_select_type);
        new ScheduleSpinnerData(getApplicationContext(),spinner_select_type,configType,spinnerDataInterface).setSpinnerData();
        spinner_select_Hour=(Spinner)findViewById(R.id.spinner_select_Hour);
        new ScheduleSpinnerData(getApplicationContext(),spinner_select_Hour,configHour,spinnerDataInterface).setSpinnerData();

        homeFooterButton =(Button)findViewById(R.id.home_footer_button);
        homeFooterButton.setOnClickListener(this);
        devicesFooterBtn=(Button)findViewById(R.id.devices_footer_btn);
        devicesFooterBtn.setOnClickListener(this);
        scheduleFooterBtn=(Button)findViewById(R.id.schedule_footer_btn);
        scheduleFooterBtn.setOnClickListener(this);
        add_new_btn=(Button)findViewById(R.id.add_new_btn);
        add_new_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SmsManager smsManager = SmsManager.getDefault();
        switch (view.getId()){
            case R.id.submit_btn:
                    /*if (config_device_number_edt.getText().length() > 0 && !deviceNumber.equalsIgnoreCase(AppConstant.NONE)) {
                        DeviceNumberUpdate(1);

                        }

                    if (config_device_name_edt.getText().length() > 0 && !deviceName.equalsIgnoreCase(AppConstant.NONE)) {

                        DeviceNameUpdate(1);
                    }*/

                if(Dropdownposition ==1){
                    AddNewMobileNumber();

                }
                if(Dropdownposition ==2){
                        DeviceNumberMessageUpdate();
                    }
                if(Dropdownposition ==3){
                    MobileNumberMessageDelete();

                }

                if(Dropdownposition ==4){
                    ResetCurrentTime();
                }


                if((deviceNumber !=null && deviceName!=null) && (Dropdownposition ==5 || Dropdownposition == 6)){
                    HourValue = spinner_select_Hour.getSelectedItem().toString();
                    StringBuffer deviceStatusval = new StringBuffer();

                    deviceStatusval.append("$").append("DName-").append(deviceName).append(";").append("TName-DConfig").append(";").append("ConfigType-");
                    if(DeviceStatus == Boolean.TRUE){
                         deviceStatusval.append("5");
                    }else{
                        deviceStatusval.append("6");
                    }

                    deviceStatusval.append(";").append("ConfigDetails-").append(HourValue).append("*");
                    smsManager.sendTextMessage(deviceNumber, null, deviceStatusval.toString(), null, null);
                    Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();

                }


                break;
            case R.id.home_footer_button:
                startActivity(new Intent(this,StatisticsActivity.class));
                break;
            case R.id.devices_footer_btn:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.schedule_footer_btn:
                startActivity(new Intent(this,MessageScheduleActivity.class));
                break;
            case R.id.add_new_btn:
                startActivity(new Intent(this,AddPhoneNumberActivity.class));
                break;

        }


    }

    @Override
    public void spinnerData(int position, View view) {
         /* if(view.getId()==R.id.spinner_sim){
            mMessageScheduleData.setPhoneNumber(simList.get(position).toString());
        }*/
        if(view.getId()==R.id.spinner_select_phone){
            deviceNumber=NumberList.get(position).toString();
            if(deviceNumber.equalsIgnoreCase(AppConstant.NONE)){
                config_device_number_edt.setVisibility(View.GONE);
                config_device_number_edt.setText("");

            }

            NumberPosition=position-2;

        }
        if(view.getId()==R.id.spinner_select_device){
            deviceName=NameList.get(position).toString();
            if(deviceName.equalsIgnoreCase(AppConstant.NONE)){
                config_device_name_edt.setVisibility(View.GONE);
                config_device_name_edt.setText("");
            }
            NamePosition=position-2;
        }

        if(view.getId()==R.id.spinner_select_type){
            if(position ==1){
                config_device_number_add.setVisibility(View.VISIBLE);
                config_device_number_edt.setVisibility(View.GONE);
                config_device_old_Number_edt.setVisibility(View.GONE);
                config_device_name_edt.setVisibility(View.GONE);
                config_device_number_dlt.setVisibility(View.GONE);
                spinner_select_Hour.setVisibility(View.GONE);
                Dropdownposition =1;
            }
            if(position==2){
                if(deviceNumber.length()>0 && !deviceNumber.equalsIgnoreCase(AppConstant.NONE)){
                    config_device_number_edt.setVisibility(View.VISIBLE);
                    config_device_number_edt.setHint(getResources().getString(R.string.updated_number_hint));
                    config_device_old_Number_edt.setVisibility(View.VISIBLE);
                    config_device_number_add.setVisibility(View.GONE);
                    config_device_number_dlt.setVisibility(View.GONE);
                    spinner_select_Hour.setVisibility(View.GONE);
                }

                if(deviceName.length()>0 && !deviceName.equalsIgnoreCase(AppConstant.NONE)){
                    config_device_name_edt.setVisibility(View.VISIBLE);
                    config_device_number_edt.setHint(getResources().getString(R.string.update_name_hint));
                    spinner_select_Hour.setVisibility(View.GONE);
                }

                Dropdownposition =2;

            }else  if(position==3){
               /* if(!deviceNumber.equalsIgnoreCase(AppConstant.NONE) && deviceName.equalsIgnoreCase(AppConstant.NONE)){
                    NamePosition=NumberPosition;
                    DeviceNumberUpdate(2);
                    DeviceNameUpdate(2);
                    //spinner_select_Hour.setVisibility(View.GONE);
                }
                else if(!deviceName.equalsIgnoreCase(AppConstant.NONE) && deviceNumber.equalsIgnoreCase(AppConstant.NONE)){
                    NumberPosition=NamePosition;
                    DeviceNameUpdate(2);
                    DeviceNumberUpdate(2);
                    //spinner_select_Hour.setVisibility(View.GONE);
                }
                else if(!deviceNumber.equalsIgnoreCase(AppConstant.NONE) && !deviceName.equalsIgnoreCase(AppConstant.NONE)){
                    DeviceNumberUpdate(2);
                    DeviceNameUpdate(2);
                    //spinner_select_Hour.setVisibility(View.GONE);
                }*/
                config_device_number_dlt.setVisibility(View.VISIBLE);
                config_device_number_add.setVisibility(View.GONE);
                config_device_number_edt.setVisibility(View.GONE);
                config_device_old_Number_edt.setVisibility(View.GONE);
                config_device_name_edt.setVisibility(View.GONE);
                spinner_select_Hour.setVisibility(View.GONE);
                Dropdownposition = 3;
            }else if(position==4){
                Dropdownposition = 4;
            }
            else if(position==5){
                if(deviceNumber!=null && deviceName !=null){
                    DeviceStatus =true;
                    spinner_select_Hour.setVisibility(View.VISIBLE);
                    config_device_number_add.setVisibility(View.GONE);
                    config_device_number_edt.setVisibility(View.GONE);
                    config_device_old_Number_edt.setVisibility(View.GONE);
                    config_device_name_edt.setVisibility(View.GONE);
                    config_device_number_dlt.setVisibility(View.GONE);
                    Dropdownposition =5;
                }
                }else if(position==6){
                if(deviceNumber !=null && deviceName !=null ){
                    DeviceStatus =false;
                    config_device_number_add.setVisibility(View.GONE);
                    config_device_number_edt.setVisibility(View.GONE);
                    config_device_old_Number_edt.setVisibility(View.GONE);
                    config_device_name_edt.setVisibility(View.GONE);
                    config_device_number_dlt.setVisibility(View.GONE);
                    Dropdownposition =6;
                }
            }else if(position !=4){
                spinner_select_Hour.setVisibility(View.GONE);
            }

            }
        }



    private void getNameList(){
        NameList= Utils.getDeviceNameList(deviceDataObject);
        NameList.add(0,"Device Name");
        NameList.add(1,"None");
        //NameList.add(1,"All");
    }

    private void getNumberList(){

        NumberList=Utils.getDeviceNumberList(deviceDataObject);
        NumberList.add(0,"Device Number");
        NumberList.add(1,"None");
        //NumberList.add(1,"All");
    }

    //To update the registered mobile number
    private void DeviceNumberMessageUpdate(){
        StringBuffer UpdatedPhoneDetails = new StringBuffer();
        SmsManager  smsManager = SmsManager.getDefault();
        UpdatedPhoneDetails.append("DName-")
                .append(deviceName).append(";")
                .append("TName-DConfig")
                .append(";").append("ConfigType-").append("2").append(";")
                .append("ConfigDetails-").append(config_device_old_Number_edt.getText().toString()).append(":").append(config_device_number_edt.getText().toString());

        smsManager.sendTextMessage(deviceNumber, null, UpdatedPhoneDetails.toString() , null, null);

        Toast.makeText(this, "Request for Update Has Been Sent", Toast.LENGTH_SHORT).show();
    }

    // To delete the registered mobile number
    private void MobileNumberMessageDelete(){
        StringBuffer DeletedPhoneDetails = new StringBuffer();
        SmsManager  smsManager = SmsManager.getDefault();
        DeletedPhoneDetails.append("DName-")
                .append(deviceName).append(";")
                .append("TName-DConfig")
                .append(";").append("ConfigType-").append("3").append(";")
                .append("ConfigDetails-").append(config_device_number_dlt.getText().toString());

        smsManager.sendTextMessage(deviceNumber, null, DeletedPhoneDetails.toString() , null, null);
        Toast.makeText(this, "Request for Delete Number Has Been Sent", Toast.LENGTH_SHORT).show();
    }

    //To Add new mobile to a device
    private void AddNewMobileNumber(){
        StringBuffer AddNewMobile = new StringBuffer();
        SmsManager smsManager = SmsManager.getDefault();
        AddNewMobile.append("DName")
                .append(deviceName).append(";")
                .append("TName-DConfig")
                .append(";").append("ConfigType-").append("1").append(";")
                .append("ConfigDetails-").append(config_device_number_add.getText().toString());

        smsManager.sendTextMessage(deviceNumber, null, AddNewMobile.toString() , null, null);
        Toast.makeText(this, "Request for Add Number Has Been Sent", Toast.LENGTH_SHORT).show();
    }

    // To Reset the current time of device
    private void ResetCurrentTime(){

        SimpleDateFormat dayandtime = new SimpleDateFormat("dd-MMM-YY HH:mm:ss");
        String currentTime = dayandtime.format(Calendar.getInstance().getTime());
        StringBuffer ResetTime = new StringBuffer();
        SmsManager smsManager = SmsManager.getDefault();
        ResetTime.append("DName")
                .append(deviceName).append(";")
                .append("TName-DConfig")
                .append(";").append("ConfigType-").append("4").append(";")
                .append("ConfigDetails-").append(currentTime);

        smsManager.sendTextMessage(deviceNumber, null, ResetTime.toString() , null, null);
        Toast.makeText(this, "Request for Reset Has Been Sent", Toast.LENGTH_SHORT).show();
    }

    private  void DeviceNumberUpdate(int type){
        getNumberList();
        new ScheduleSpinnerData(getApplicationContext(),spinner_select_phone,NumberList,spinnerDataInterface).setSpinnerData();
        config_device_number_edt.setVisibility(View.GONE);
        NumberPosition=-1;
    }


   private void DeviceNameUpdate(int type){
        getNameList();
        new ScheduleSpinnerData(getApplicationContext(),spinner_select_device,NameList,spinnerDataInterface).setSpinnerData();
        config_device_name_edt.setVisibility(View.GONE);
        NamePosition=-1;
    }

}
