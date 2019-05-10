package com.stlight.setData;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stlight.Adapter.DayRecyclerViewAdapter;
import com.stlight.Adapter.TimeSlotAdapter;
import com.stlight.Model.TimeVo;

import java.util.List;

import core.ItemClick;

/**
 * Created by user on 2/11/2018.
 */

public class SetTimeSlotRecycleView implements ItemClick,IGetSlotTime{
    private Context ctx;
    private RecyclerView recycleView;
    private LinearLayoutManager mLayoutManager;
    private TimeSlotAdapter mTimeSlotAdapter;
    private List<TimeVo> timeSlotListData;
    private IGetUpdatedSlotTime mIGetUpdatedSlotTime;
    public SetTimeSlotRecycleView(Context ctx, RecyclerView recycleView, List<TimeVo> timeSlotListData,IGetUpdatedSlotTime getUpdatedSlotTime){
        this.ctx=ctx;
        this.recycleView=recycleView;
        this.timeSlotListData=timeSlotListData;
        this.mIGetUpdatedSlotTime=getUpdatedSlotTime;

    }
    public  void setTimeSlotRecycleView(){
        IGetSlotTime iGetSlotTime=(IGetSlotTime)this;
        ItemClick myClickListener =(ItemClick)this;
        mLayoutManager = new LinearLayoutManager(ctx);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(mLayoutManager);
        mTimeSlotAdapter=new TimeSlotAdapter(ctx,timeSlotListData,myClickListener,iGetSlotTime);
        recycleView.setAdapter(mTimeSlotAdapter);
    }

    @Override
    public void onItemClick(int position, View v) {



    }


    @Override
    public void getDeletedSlotTime(int position) {
        timeSlotListData.remove(position);
        mTimeSlotAdapter.notifyDataSetChanged();
        mIGetUpdatedSlotTime.getTimeSlotList(timeSlotListData);


    }
}
