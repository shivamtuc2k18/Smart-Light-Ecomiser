package com.stlight.setData;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.stlight.R;

import java.util.ArrayList;

/**
 * Created by user on 3/16/2018.
 */

public class ScheduleSpinnerData {

    private Context ctx ;
    private Spinner mSpinnerView;
    private ArrayList listData;
    private IScheduleSpinnerData spinnerInterface;
    public ScheduleSpinnerData(Context ctx ,Spinner view, ArrayList listData,IScheduleSpinnerData spinnerInterface){
        this.ctx=ctx;
        this.mSpinnerView=view;
        this.listData=listData;
        this.spinnerInterface=spinnerInterface;

    }

    public void setSpinnerData(){
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ctx, R.layout.spinner_item,listData) {
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
    }


    public void setSpinnerAdapter(ArrayAdapter<String> spinnerArrayAdapter){

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerView.setAdapter(spinnerArrayAdapter);

        mSpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    spinnerInterface.spinnerData(position,mSpinnerView);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
