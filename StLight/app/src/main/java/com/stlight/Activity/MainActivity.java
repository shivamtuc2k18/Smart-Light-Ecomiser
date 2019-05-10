package com.stlight.Activity;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.stlight.Adapter.MyRecyclerViewAdapter;
import com.stlight.Model.DataObject;
import com.stlight.Model.DeviceData;
import com.stlight.R;
import com.stlight.setData.IGetSpinnerData;
import com.stlight.setData.SetSpinnerData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import core.BaseActivity;
import core.BottomSheet;
import core.ConsolidatedList;
import core.DateComparator;
import core.IDateListener;
import core.IPermissionListener;
import core.RealmController;
import core.utils.DateTimeUtils;
import core.utils.Utils;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity implements IPermissionListener, View.OnClickListener, IDateListener,IGetSpinnerData {
    private ArrayList<DataObject> smsList;
    private ArrayList<DataObject> smsListtemp;
    private ArrayList<String> TempsmsList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CoordinatorLayout mCoordinatorLayout;
    private IDateListener mIDateListener;
    private RealmResults<DataObject> dataObject;
    private FloatingActionButton mFabButton;
    private Spinner select_device;
    private  RealmResults<DeviceData> deviceDataObject;
    private int i=0;
   // private String hashMapKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deviceDataObject =  RealmController.with(this).getDeviceDataObject();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        toolbar = (Toolbar) findViewById(R.id.sms_toolbar);
        setSupportActionBar(toolbar);
        toolbar_image = (ImageView) toolbar.findViewById(R.id.toolbar_image);
        toolbar_image.setOnClickListener(this);
        toolbar_filter_image = (ImageView) toolbar.findViewById(R.id.filter_image);
        toolbar_filter_image.setOnClickListener(this);
        toolbar_filter_image.setVisibility(View.VISIBLE);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.devices_footer));
        mFabButton=(FloatingActionButton) findViewById(R.id.fab_btn);
        mFabButton.setVisibility(View.GONE);
        mFabButton.setOnClickListener(this);
        mIDateListener=(IDateListener)this;
        mCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        select_device=(Spinner)findViewById(R.id.select_device);
        //select_device.setSelection(1);
        IGetSpinnerData spinnerInterface= (IGetSpinnerData)this;
        new SetSpinnerData(getApplicationContext(),select_device, Utils.getDeviceDataList(deviceDataObject),spinnerInterface).setSpinnerData();


        homeFooterButton =(Button)findViewById(R.id.home_footer_button);
        homeFooterButton.setOnClickListener(this);
        devicesFooterBtn=(Button)findViewById(R.id.devices_footer_btn);
        devicesFooterBtn.setOnClickListener(this);
        scheduleFooterBtn=(Button)findViewById(R.id.schedule_footer_btn);
        scheduleFooterBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        permissionCheck(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS));
    }

    private void showContacts() {
        smsList = new ArrayList<DataObject>();
        dataObject =  RealmController.with(this).getDataObject();
        getSMSList(dataObject);
        setDataToAdapter();
    }

    @Override
    public void permissionCheck(boolean b) {
        if(b){
            showContacts();
        }
    }

    LinkedHashMap groupDataIntoHashMap(List<DataObject> dataObject) {
        LinkedHashMap<String, List<DataObject>> groupedHashMap = new LinkedHashMap<>();
        for (DataObject values : dataObject) {

                String hashMapKey = values.getDeviceNumber();
                if (groupedHashMap.containsKey(hashMapKey)) {
                    groupedHashMap.get(hashMapKey).add(values);
                } else {
                    List<DataObject> list = new ArrayList<>();
                    list.add(values);
                    groupedHashMap.put(hashMapKey, list);
                }



        }
                return groupedHashMap;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar_image:
                startActivity(new Intent(this,DeviceConfigActivity.class));
             //  new BottomSheet().ShowBottomSheet(this,mIDateListener);
                break;
            case R.id.fab_btn:
                Intent intent =new Intent(this,AddPhoneNumberActivity.class);
                startActivity(intent);
                break;
            case R.id.home_footer_button:
                startActivity(new Intent(this,StatisticsActivity.class));
                break;
            case R.id.devices_footer_btn:
                //startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.schedule_footer_btn:
                startActivity(new Intent(this,MessageScheduleActivity.class));
                break;

            case R.id.filter_image:
                new BottomSheet().ShowBottomSheet(this,mIDateListener);
                break;

        }
    }

    @Override
    public void dateListener(long fromDate,long toDate,int filterPosition) {

         if(filterPosition==0)
            dataObject =  RealmController.with(this).getDataObject();
        else if(filterPosition==1)
             dataObject=  RealmController.with(this).today((DateTimeUtils.getDate(fromDate)));
        else
             dataObject=  RealmController.with(this).between(new Date(fromDate),new Date(toDate));

        smsList.clear();
        getSMSList(dataObject);
        setDataToAdapter();

    }

   private void  getSMSList(RealmResults<DataObject> dataObject){
       //Collections.sort(dataObject, new DateComparator());
       for (int i = 0; i < dataObject.size(); i++) {

           smsList.add(dataObject.get(i));

       }
       Collections.sort(smsList, new DateComparator());

   }

    private void  setDataToAdapter(){
        if(smsList!=null && smsList.size()>0) {

            mAdapter = new MyRecyclerViewAdapter(ConsolidatedList.consolidatedList(groupDataIntoHashMap(smsList)),this);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            // TODO change display message
            Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "No data  available", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void getPosition(int position) {
        if(position!=0) {
            dataObject = RealmController.with(this).getAllDeviceNumber(Utils.getDeviceDataList(deviceDataObject).get(position + 1).toString());
        }else{
            dataObject =  RealmController.with(this).getDataObject();
        }
            smsList.clear();
            getSMSList(dataObject);
            setDataToAdapter();

    }
}
