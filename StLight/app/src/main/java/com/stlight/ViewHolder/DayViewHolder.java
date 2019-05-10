package com.stlight.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.stlight.R;

import core.ItemClick;

/**
 * Created by user on 2/10/2018.
 */

public class DayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView mDay_value;
    public CheckBox mCheckBox;
    public ItemClick mItemClick;
    public DayViewHolder(View itemView,ItemClick itemClick) {
        super(itemView);
        this.mItemClick=itemClick;
        mDay_value = (TextView) itemView.findViewById(R.id.day_tv);
        mCheckBox = (CheckBox) itemView.findViewById(R.id.check_box);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      mItemClick.onItemClick(getAdapterPosition(), view);

    }
}
