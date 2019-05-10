package com.stlight.setData;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.stlight.Adapter.DayRecyclerViewAdapter;
import com.stlight.Model.DayVo;
import com.stlight.R;

import java.util.ArrayList;
import java.util.List;

import core.ItemClick;

/**
 * Created by user on 2/10/2018.
 */

public class DayRecycleView implements ItemClick{
    private Context ctx;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView recycleView;
    private String []dayListArray;
    public List<DayVo> mDayVoList=new ArrayList<>();
    private DayRecyclerViewAdapter mDayRecyclerViewAdapter;
    private DayVo dayVo;
    private int lastPosition=-1;
    private IGetDay mIGetDay;
    public DayRecycleView(Context ctx,RecyclerView recycleView, IGetDay iGetDay){
        this.ctx=ctx;
        this.recycleView=recycleView;
        this.mIGetDay=iGetDay;
    }
    public void setRecycleViewData(){
        ItemClick myClickListener =(ItemClick)this;
        dayListArray= ctx.getResources().getStringArray(R.array.day_list);
        for(String day:dayListArray){
            dayVo=new DayVo();
            dayVo.setChecked(false);
            dayVo.setDay(day);
            mDayVoList.add(dayVo);

        }



        mGridLayoutManager = new GridLayoutManager(ctx, 4);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(mGridLayoutManager);
        mDayRecyclerViewAdapter=new DayRecyclerViewAdapter(ctx,mDayVoList,myClickListener);
        recycleView.setAdapter(mDayRecyclerViewAdapter);

    }

   // public int listofdays(){

     //   return mDayVoList.size();
    //};

    @Override
    public void onItemClick(int position, View v) {
        if(lastPosition!=position) {
            if (position != 0) {
                mDayVoList.get(0).setChecked(false);
                mDayVoList.get(position).setChecked(true);

            } else {
                for (DayVo dayvo : mDayVoList) {
                    if(dayvo.isChecked()) {
                        dayvo.setChecked(false);
                    }
                }
                mDayVoList.get(0).setChecked(true);

            }
            lastPosition=position;
        }else{
            mDayVoList.get(position).setChecked(false);
            lastPosition=-1;
        }
        mDayRecyclerViewAdapter.notifyDataSetChanged();
        mIGetDay.getDay(mDayVoList);



    }


}
