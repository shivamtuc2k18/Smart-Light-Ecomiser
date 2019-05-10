package com.stlight.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.stlight.Model.DeviceData;
import com.stlight.R;

import core.BaseActivity;
import core.RealmController;
import io.realm.Realm;

public class AddPhoneNumberActivity extends BaseActivity implements View.OnClickListener {
//private EditText phoneNumber;
private EditText deviceName;
private EditText deviceNumber;
private Button addButton;
private Button nextButton;
private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_number);
        this.realm = RealmController.with(this).getRealm();
        init();
    }
    private void init(){
       // phoneNumber=(EditText)findViewById(R.id.phone_edt);
        deviceNumber=(EditText)findViewById(R.id.device_edt);
        deviceName=(EditText)findViewById(R.id.device_name_edt);

        addButton=(Button) findViewById(R.id.add_btn);
        addButton.setOnClickListener(this);
        nextButton=(Button) findViewById(R.id.next_btn);
        nextButton.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_add_number);
        setSupportActionBar(toolbar);
        toolbar_image = (ImageView) toolbar.findViewById(R.id.toolbar_image);
        toolbar_image.setOnClickListener(this);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.device_details));

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
            case R.id.add_btn:
                if(deviceNumber.getText().toString().length()>=1 && deviceName.getText().toString().length()!=0)
                    getDeviceData();
                else
                    showSnackBar(getResources().getString(R.string.valid_data_toast));
                break;
            case R.id.next_btn:
                Intent MessageSchedule = new Intent(this, StatisticsActivity.class);
                startActivity(MessageSchedule);
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
            case R.id.toolbar_image:
                startActivity(new Intent(this,DeviceConfigActivity.class));
                break;
        }
    }

    private void getDeviceData(){
        if( deviceNumber.getText().toString().length()>0 && deviceName.getText().toString().length()>0){
            DeviceData mDeviceData=new DeviceData();
          /*  if(!deviceData.contains(phoneNumber.getText().toString().trim())) {
                deviceData.add(phoneNumber.getText().toString().trim());
                mDeviceData.setPhoneNumber(phoneNumber.getText().toString().trim());
            }*/
            mDeviceData.setDeviceNumber("+91"+deviceNumber.getText().toString().trim());
            mDeviceData.setDeviceName(deviceName.getText().toString().trim());
            realm.beginTransaction();
            realm.copyToRealm(mDeviceData);
            realm.commitTransaction();
        }else{
            showSnackBar(getResources().getString(R.string.phone_number_text));
        }

    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
