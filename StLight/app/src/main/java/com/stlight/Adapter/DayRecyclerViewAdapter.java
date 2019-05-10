package com.stlight.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stlight.Model.DayVo;
import com.stlight.R;
import com.stlight.ViewHolder.DayViewHolder;

import java.util.List;

import core.ItemClick;

/**
 * Created by user on 2/10/2018.
 */

public class DayRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ItemClick myClickListener;
    private List<DayVo> dayVoList;

    public DayRecyclerViewAdapter(Context ctx,List<DayVo> dayVoList,ItemClick myClickListener){
        this.dayVoList=dayVoList;
        this.myClickListener=myClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = new DayViewHolder(inflater.inflate(R.layout.day_view_adapter, parent, false),myClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DayViewHolder viewHolder= (DayViewHolder)holder;
        DayVo mDayVo=dayVoList.get(position);
        viewHolder.mDay_value.setText(mDayVo.getDay());
        viewHolder.mCheckBox.setChecked(mDayVo.isChecked());
    }

    @Override
    public int getItemCount() {
        return dayVoList.size();
    }

    public void setOnItemClickListener(ItemClick myClickListener) {
        this.myClickListener = myClickListener;
    }
}
