package com.stlight.setData;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.stlight.Activity.MessageScheduleActivity;
import com.stlight.Model.DayVo;
import com.stlight.Model.MessageScheduleData;
import com.stlight.Model.TimeVo;
import com.stlight.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by user on 2/11/2018.
 */

public class SetTime implements View.OnClickListener,IGetUpdatedSlotTime{
    private  Context ctx;
    private EditText ed1;
    private EditText ed2;
    private List<String> setDayList = new ArrayList<String>();
    private MessageScheduleData mMessageScheduleData=new MessageScheduleData();
    private MessageScheduleActivity datalistofdays = new MessageScheduleActivity();
    private Button schedule_add_btn;
    private Spinner spinner;
    private String [] dimListArray;
    private RecyclerView timeSlotRecyclerView;
    private boolean isStartTime;
    private Calendar myCalendar = Calendar.getInstance();
    private IGetUpdatedSlotTime mIGetUpdatedSlotTime;
    private  TimePickerDialog.OnTimeSetListener onStartTimeListener;
    private List<TimeVo> timeSlotListData=new ArrayList<>();
    private String startDate="";
    private String EndDate="";
    private String dimValue="";
    private IGetTime iGetTime;
    private String setDay="";
    public String datatemp2= "";
    private MessageScheduleData mMessageScheduleDataSetTime=new MessageScheduleData();
   public SetTime(Context ctx, EditText ed1, EditText ed2, Button schedule_add_btn, Spinner spinner, RecyclerView timeSlotRecyclerView,IGetTime iGetTime){
       this.ctx=ctx;
       this.ed1=ed1;
       this.ed2=ed2;
       this.spinner=spinner;
       this.timeSlotRecyclerView=timeSlotRecyclerView;
       this.schedule_add_btn=schedule_add_btn;
       mIGetUpdatedSlotTime=this;
       this.iGetTime=iGetTime;
    }

    public void getTimeAndDimValue(){
        ed1.setOnClickListener(this);
        ed2.setOnClickListener(this);
        schedule_add_btn.setOnClickListener(this);
        dimListArray= ctx.getResources().getStringArray(R.array.dim_list);
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ctx, R.layout.spinner_item,dimListArray) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                return view;
            }
        };
        setSpinnerAdapter(spinnerArrayAdapter);




// time picker listener
           onStartTimeListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String Hour=""+hourOfDay;
                String Minute=""+minute;
                if(hourOfDay<=9){
                    Hour="0"+""+hourOfDay;
                }if(minute<=9){
                    Minute="0"+""+minute;

                }
                   if(isStartTime){
                       ed1.setText(Hour+":"+Minute);
                       startDate=Hour+":"+Minute;
                   }else {
                       ed2.setText(Hour+":"+Minute);
                       EndDate=Hour+":"+Minute;

                   }


            }
        };


    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.st_time_edt){
            isStartTime=true;
            new TimePickerDialog(ctx, onStartTimeListener, myCalendar
                    .get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), true).show();

        }else if (view.getId()== R.id.ed_time_edt){
            if(ed1.getText().toString().length()>0){
                isStartTime=false;
            }else {
                Toast.makeText(ctx,ctx.getResources().getString(R.string.st_time_toast),Toast.LENGTH_LONG).show();
                return;
            }
            new TimePickerDialog(ctx, onStartTimeListener, myCalendar
                    .get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), true).show();
        }

        else if(view.getId()==R.id.schedule_add_btn){
            mIGetUpdatedSlotTime=this;
            if(startDate.length()>0 && EndDate.length()>0 && dimValue.length()>0){
                if(timeSlotListData.size()<4){
                    TimeVo mTimeVo=new TimeVo();
                    mTimeVo.setStartTime(startDate);
                    mTimeVo.setEndTime(EndDate);
                    mTimeVo.setDimValue(dimValue);
                    timeSlotListData.add(timeSlotListData.size(),mTimeVo);
                }else{
                    // TODO toast
                }
                new SetTimeSlotRecycleView(ctx,timeSlotRecyclerView,timeSlotListData,mIGetUpdatedSlotTime).setTimeSlotRecycleView();
                mIGetUpdatedSlotTime.getTimeSlotList(timeSlotListData);

            }else{
                Toast.makeText(ctx,ctx.getResources().getString(R.string.valid_data_toast),Toast.LENGTH_LONG).show();
                return;
            }
        }

    }

    public void setSpinnerAdapter(ArrayAdapter<String> spinnerArrayAdapter){

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                   dimValue=dimListArray[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void getTimeSlotList(List<TimeVo> timeSlotListData) {
        iGetTime.getTimeSlot(timeSlotListData);

    }
}
