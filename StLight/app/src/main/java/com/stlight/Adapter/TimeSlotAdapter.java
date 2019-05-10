package com.stlight.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stlight.Model.DayVo;
import com.stlight.Model.TimeVo;
import com.stlight.R;
import com.stlight.ViewHolder.DayViewHolder;
import com.stlight.ViewHolder.TimeSlotViewHolder;
import com.stlight.setData.IGetSlotTime;

import java.util.List;

import core.ItemClick;

/**
 * Created by user on 2/11/2018.
 */

public class TimeSlotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ItemClick myClickListener;
    private List<TimeVo> timeVoList;
    private IGetSlotTime mIGetSlotTime;
    public TimeSlotAdapter(Context ctx, List<TimeVo> timeVoList, ItemClick myClickListener,IGetSlotTime getSlotTime){
        this.timeVoList=timeVoList;
        this.myClickListener=myClickListener;
        this.mIGetSlotTime=getSlotTime;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = new TimeSlotViewHolder(inflater.inflate(R.layout.time_slot_adapter, parent, false),myClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TimeSlotViewHolder viewHolder= (TimeSlotViewHolder)holder;
        TimeVo mTimeVo=timeVoList.get(position);
        int slNumber=position+1;
        String tempList = mTimeVo.getDayList();
        viewHolder.slNumber.setText(""+slNumber);
        viewHolder.sTimeText.setText(mTimeVo.getStartTime());
        viewHolder.eTimeText.setText(mTimeVo.getEndTime());
        viewHolder.dimValue.setText(mTimeVo.getDimValue());
        viewHolder.dayListText.setText(tempList);
        viewHolder.delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIGetSlotTime.getDeletedSlotTime(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return timeVoList.size();
    }

    public void setOnItemClickListener(ItemClick myClickListener) {
        this.myClickListener = myClickListener;
    }
}
