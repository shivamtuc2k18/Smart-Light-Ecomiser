package com.stlight.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stlight.R;

import org.w3c.dom.Text;

import core.ItemClick;

public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mExpected_value;
    public TextView mActual_value;
    public TextView mSavings_value;
    public TextView status;
    public TextView status_tv;
    public TextView deviceName_tv;
    public TextView last_heartbeat_time_value;
    ItemClick mItemClick;
      public DataObjectHolder(View itemView) {
        super(itemView);
   mExpected_value = (TextView) itemView.findViewById(R.id.textView5);
   mActual_value = (TextView) itemView.findViewById(R.id.textView4);
  //mSavings_value = (TextView) itemView.findViewById(R.id.textView6);
          status_tv=(TextView)itemView.findViewById(R.id.status_tv);
          //status=(TextView)itemView.findViewById(R.id.status);
         // deviceName_tv=(TextView)itemView.findViewById(R.id.deviceName_tv);
          last_heartbeat_time_value = (TextView)itemView.findViewById(R.id.last_heartbeat_time_value);
        itemView.setOnClickListener(this);
        }

    @Override
  public void onClick(View v) {
        //mItemClick.onItemClick(getAdapterPosition(), v);
        }
        }
