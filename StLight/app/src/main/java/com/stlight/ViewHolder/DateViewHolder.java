package com.stlight.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stlight.R;

import core.ItemClick;

public class DateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView dateTime_value;
    ItemClick mItemClick;
    public DateViewHolder(View itemView) {
        super(itemView);
        dateTime_value = (TextView) itemView.findViewById(R.id.date_tv);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       // mItemClick.onItemClick(getAdapterPosition(), v);
    }
}