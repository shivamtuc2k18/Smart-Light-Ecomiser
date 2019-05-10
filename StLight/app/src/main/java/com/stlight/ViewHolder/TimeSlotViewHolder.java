package com.stlight.ViewHolder;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.stlight.R;

import org.w3c.dom.Text;

import core.ItemClick;

/**
 * Created by user on 2/11/2018.
 */

public class TimeSlotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView slNumber;
    public TextView dayListText;
    public TextView sTimeText;
    public TextView eTimeText;
    public TextView dimValue;
    public ItemClick mItemClick;
    public ImageView delete_icon;

    public TimeSlotViewHolder(View itemView, ItemClick itemClick) {
        super(itemView);
        this.mItemClick = itemClick;
        slNumber = (TextView) itemView.findViewById(R.id.sl_number);
        sTimeText = (TextView) itemView.findViewById(R.id.st_time_tv);
        eTimeText = (TextView) itemView.findViewById(R.id.ed_time_tv);
        dimValue = (TextView) itemView.findViewById(R.id.dim_tv);
        delete_icon=(ImageView) itemView.findViewById(R.id.delete_icon);
        dayListText = (TextView) itemView.findViewById(R.id.dayList);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mItemClick.onItemClick(getAdapterPosition(), view);

    }
}