package com.stlight.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stlight.Model.DayVo;
import com.stlight.Model.DeviceData;
import com.stlight.Model.DeviceDataMap;
import com.stlight.Model.MessageScheduleData;
import com.stlight.Model.TimeVo;
import com.stlight.R;
import com.stlight.setData.DayRecycleView;
import com.stlight.setData.IGetDate;
import com.stlight.setData.IGetDay;
import com.stlight.setData.IGetTime;
import com.stlight.setData.IScheduleSpinnerData;
import com.stlight.setData.ScheduleSpinnerData;
import com.stlight.setData.SetDate;
import com.stlight.setData.SetTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import core.BaseActivity;
import core.IPermissionListener;
import core.RealmController;
import core.utils.AppConstant;
import core.utils.DateTimeUtils;
import core.utils.Utils;
import io.realm.RealmResults;

public class MessageScheduleActivity extends BaseActivity implements IGetDate,View.OnClickListener,IGetDay,IGetTime, IPermissionListener,IScheduleSpinnerData {
    private DeviceDataMap mDeviceDataMap;
    private LinkedHashMap<String, List<String>> deviceData;
    private ArrayList simList;
    private ArrayList NameList;
    private ArrayList NumberList;
    private String dayListStr="";
    private List<DayVo> setDayList = new ArrayList<DayVo>();
   // private Spinner mSimSpinner;
    private Spinner mNameSpinner;
    private Spinner mNumberSpinner;
    private Context ctx;
    private MessageScheduleData mMessageScheduleData=new MessageScheduleData();
    private TimeVo mTimeVo = new TimeVo();
    private EditText startDate_edt;
    private EditText endDate_edt;
    private EditText start_time_edt;
    private EditText end_time_edt;
    private Spinner mDim;
    private  boolean isStartDate =false;
    private IGetDate mIGetDate;
    private long minDate;
    private RecyclerView day_recycleView;
    private Button schedule_add_btn;
    private RecyclerView timeSlotRecyclerView;
    private Button schedule_btn;
    private IGetTime mIGetTime;
    private boolean permissionCheck;
    private RealmResults<DeviceData> deviceDataObject;
    private IScheduleSpinnerData spinnerDataInterface;
    private String daysChecked="";
    private TextView dayList;
    public String datatemp1="";
    public TimeVo mTimeVoList = new TimeVo();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=getApplicationContext();
        setContentView(R.layout.activity_message_schedule_activity);
        init();


    }

    private void init(){
           deviceDataObject =  RealmController.with(this).getDeviceDataObject();
           if(deviceDataObject!=null){
              /* simList=Utils.getSimDataList(deviceDataObject);
               simList.add(0,"Sim Number");
*/
               NameList=Utils.getDeviceNameList(deviceDataObject);
               NameList.add(0,"Device Name");
               //NameList.add(1,"All");


               NumberList=Utils.getDeviceNumberList(deviceDataObject);
               NumberList.add(0,"Device Number");
               //NumberList.add(1,"All");

           }



        toolbar = (Toolbar) findViewById(R.id.toolbar_msg_schedule);
        setSupportActionBar(toolbar);
        toolbar_image = (ImageView) toolbar.findViewById(R.id.toolbar_image);
        toolbar_image.setOnClickListener(this);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.schedule_btn));
        dayList = (TextView) findViewById(R.id.dayList);
        startDate_edt=(EditText)findViewById(R.id.startDate_edt);
        startDate_edt.setOnClickListener(this);
        endDate_edt=(EditText)findViewById(R.id.endDate_edt);
        endDate_edt.setOnClickListener(this);
        //mSimSpinner=(Spinner)findViewById(R.id.spinner_sim);
        spinnerDataInterface=(IScheduleSpinnerData) this;
        mNumberSpinner=(Spinner)findViewById(R.id.spinner_device_number);
        new ScheduleSpinnerData(ctx,mNumberSpinner,NumberList,spinnerDataInterface).setSpinnerData();
        mNameSpinner=(Spinner)findViewById(R.id.spinner_device_name);
        new ScheduleSpinnerData(ctx,mNameSpinner,NameList,spinnerDataInterface).setSpinnerData();
        mIGetDate= (IGetDate) this;

      //  new ScheduleSpinnerData(ctx,mSimSpinner,simList,spinnerDataInterface).setSpinnerData();

        day_recycleView=(RecyclerView)findViewById(R.id.day_recycleView);
        IGetDay mIGetDay=(IGetDay) this;
        new DayRecycleView(ctx,day_recycleView,mIGetDay).setRecycleViewData();

        start_time_edt=(EditText)findViewById(R.id.st_time_edt);
        end_time_edt=(EditText)findViewById(R.id.ed_time_edt);
        mDim=(Spinner)findViewById(R.id.spinner_dim);
        schedule_add_btn=(Button)findViewById(R.id.schedule_add_btn);
        timeSlotRecyclerView=(RecyclerView)findViewById(R.id.slot_recycleView);
        mIGetTime=(IGetTime)this;
        new SetTime(this,start_time_edt,end_time_edt,schedule_add_btn,mDim,timeSlotRecyclerView,mIGetTime).getTimeAndDimValue();

        schedule_btn=(Button)findViewById(R.id.schedule_btn);
        schedule_btn.setOnClickListener(this);

        homeFooterButton =(Button)findViewById(R.id.home_footer_button);
        homeFooterButton.setOnClickListener(this);
        devicesFooterBtn=(Button)findViewById(R.id.devices_footer_btn);
        devicesFooterBtn.setOnClickListener(this);
        scheduleFooterBtn=(Button)findViewById(R.id.schedule_footer_btn);
        scheduleFooterBtn.setOnClickListener(this);



    }
// getting phoneNumber deviceName

// getting startDate endDate
    @Override
    public void getDate(long date,View view) {
        if(view.getId()==R.id.startDate_edt){
            startDate_edt.setText(DateTimeUtils.getDate(date));
            mMessageScheduleData.setStartDate(DateTimeUtils.getDate(date));
        }else if(view.getId()==R.id.endDate_edt){
            endDate_edt.setText(DateTimeUtils.getDate(date));
            mMessageScheduleData.setEndDate(DateTimeUtils.getDate(date));
        }

    }

    public String listofdays(View view){

            for (DayVo dayvotemp : mMessageScheduleData.getDayVoList()) {
                    if (dayvotemp.isChecked()) {
                        datatemp1 = datatemp1 + dayvotemp.getDay();
                    }
                }
                Log.i("listof days",datatemp1);
                mTimeVoList.setDayList(datatemp1);
                Log.i("getdaysfunc",mTimeVoList.getDayList());
                return datatemp1;

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.startDate_edt){
            isStartDate =true;
            minDate=new Date().getTime();
            new SetDate(this,mIGetDate,view,minDate).setDate();
        }else if(view.getId()==R.id.endDate_edt){
            isStartDate = false;
            if(startDate_edt.getText().toString().length()>0) {
                minDate=DateTimeUtils.getDateInMills(startDate_edt.getText().toString()+" 12:00:00 AM");
            } else {
                Toast.makeText(ctx,getResources().getString(R.string.start_date_toast),Toast.LENGTH_LONG).show();
                return;
            }
            new SetDate(this,mIGetDate,view,minDate).setDate();
        }

       else if(view.getId()==R.id.schedule_btn){
            int dayCount=0;
            if(permissionCheck) {
                if( mMessageScheduleData.getDayVoList()==null){
                    Toast.makeText(ctx,getResources().getString(R.string.valid_data_toast),Toast.LENGTH_LONG).show();
                    return;
                }
                for(int i=1;i<mMessageScheduleData.getDayVoList().size();i++){
                    if (mMessageScheduleData.getDayVoList().get(i).isChecked()){
                        dayCount=dayCount+1;
                    }
                }

                //if user checks ALL Days
                if (mMessageScheduleData.getDayVoList().get(0).isChecked()){
                    dayCount=7;
                }

                if(dayCount<7){
                    Toast.makeText(ctx,getResources().getString(R.string.valid_data_toast),Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    for (DayVo dayvo : mMessageScheduleData.getDayVoList()) {
                        if (dayvo.isChecked()) {
                            ScheduleMessage(dayvo.getDay());
                        }
                    }

                }
            }else{
                showSnackBar(getResources().getString(R.string.sms_permission));
            }

        }
        else if(view.getId()==R.id.home_footer_button){
            startActivity(new Intent(this,StatisticsActivity.class));
        }

        else if(view.getId()==R.id.devices_footer_btn){
            startActivity(new Intent(this,MainActivity.class));
        }

        else if(view.getId()==R.id.schedule_footer_btn){
            // startActivity(new Intent(this,MessageScheduleActivity.class));
        }
        else if(view.getId()==R.id.toolbar_image) {
                startActivity(new Intent(this, DeviceConfigActivity.class));

        }

    }

    // get dayData like ALL,Mon etc..
    @Override
    public void getDay(List<DayVo> dayVoList) {
        mMessageScheduleData.setDayVoList(dayVoList);
        //System.out.println(dayVoList.size());
    }



// get TimeSlot list
    @Override
    public void getTimeSlot(List<TimeVo> timeSlotListData) {
        mMessageScheduleData.setTimeSlotListData(timeSlotListData);
    }

// ScheduleSMS logic here
    private void ScheduleMessage(String day){
        StringBuilder sb=new StringBuilder();
        if(!Utils.Valid(mMessageScheduleData.getDeviceNumber())){
            Toast.makeText(ctx,getResources().getString(R.string.valid_data_toast),Toast.LENGTH_LONG).show();
            return;
        }

        //condition for deviceName
        if(Utils.Valid(mMessageScheduleData.getDeviceName())){
            sb.append("$").append(AppConstant.DName).append(mMessageScheduleData.getDeviceName()).append(AppConstant.SEMICOLON);
        }else{
            Toast.makeText(ctx,getResources().getString(R.string.valid_data_toast),Toast.LENGTH_LONG).show();
            return;
        }

        //condition for StartDate/EndDate
        if(Utils.Valid(mMessageScheduleData.getStartDate()) && Utils.Valid(mMessageScheduleData.getEndDate())){
            sb.append(AppConstant.START_DATE).append(mMessageScheduleData.getStartDate().replaceAll("/","")).append(AppConstant.SEMICOLON);
            sb.append(AppConstant.END_DTAE).append(mMessageScheduleData.getEndDate().replaceAll("/","")).append(AppConstant.SEMICOLON);

        }else{
            Toast.makeText(ctx,getResources().getString(R.string.valid_data_toast),Toast.LENGTH_LONG).show();
            return;
        }
        sb.append(AppConstant.DAY).append(day).append(AppConstant.SEMICOLON);
        ////condition for StartTime/EndTime/dim value
        if(mMessageScheduleData.getTimeSlotListData()==null || mMessageScheduleData.getTimeSlotListData().size()<1){
            Toast.makeText(ctx,getResources().getString(R.string.valid_data_toast),Toast.LENGTH_LONG).show();
            return;
        }
        else{
            int slot=0;
            for(TimeVo timevo:mMessageScheduleData.getTimeSlotListData()){
                    slot=slot+1;
                sb.append("S"+slot).append("-").append(timevo.getStartTime().replaceAll(":","")).append(":").append(timevo.getEndTime().replaceAll(":","")).append(AppConstant.SEMICOLON);
                sb.append("D"+slot).append("-").append(timevo.getDimValue().replaceAll("%","")).append(AppConstant.SEMICOLON);
            }
        }
                sb.append("TName-SCH").append("*");

        // to sent sms
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(mMessageScheduleData.getDeviceNumber(), null, sb.toString(), null, null);
        Log.v("TAG-MSG",""+sb.toString());
        //System.out.println(mMessageScheduleData);



    }

    @Override
    protected void onResume() {
        super.onResume();
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            permissionCheck(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS));
        }else{
            permissionCheck=true;
        }

    }

    @Override
    public void permissionCheck(boolean b) {
        permissionCheck=b;
    }

    @Override
    public void spinnerData(int position, View view) {

       /* if(view.getId()==R.id.spinner_sim){
            mMessageScheduleData.setPhoneNumber(simList.get(position).toString());
        }*/
        if(view.getId()==R.id.spinner_device_number){
           mMessageScheduleData.setDeviceNumber(NumberList.get(position).toString());
           // mMessageScheduleData.setDeviceName(NameList.get(position).toString());
        }
        if(view.getId()==R.id.spinner_device_name){
            mMessageScheduleData.setDeviceName(NameList.get(position).toString());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
