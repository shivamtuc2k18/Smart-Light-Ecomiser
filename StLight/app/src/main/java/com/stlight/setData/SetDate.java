package com.stlight.setData;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 2/10/2018.
 */

public class SetDate {
    private Calendar myCalendar = Calendar.getInstance();
    private  DatePickerDialog.OnDateSetListener dateDialogListener;
    private Context ctx;
    private IGetDate mIGetDate;
    private  View EditTextView;
    private long minDate;
    public  SetDate(Context ctx, IGetDate iGetDate,View view,long minDate){
        this.ctx=ctx;
        this.mIGetDate=iGetDate;
        this.EditTextView=view;
        this.minDate=minDate;
    }


    public void setDate(){
        DatePickerDialog dateListener= new DatePickerDialog(ctx, getDialogListener(), myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dateListener.getDatePicker().setMinDate(minDate);
        dateListener.show();

    }

    public DatePickerDialog.OnDateSetListener getDialogListener(){
        dateDialogListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mIGetDate.getDate(myCalendar.getTime().getTime(),EditTextView);

            }


        };
        return dateDialogListener;
    }

}
