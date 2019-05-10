package com.stlight.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.stlight.Model.DataObject;
import com.stlight.Model.DateItem;
import com.stlight.Model.GroupOfData;
import com.stlight.Model.ListItemType;
import com.stlight.R;
import com.stlight.ViewHolder.DataObjectHolder;
import com.stlight.ViewHolder.DateViewHolder;
import java.util.List;
import core.ItemClick;
import core.utils.DateTimeUtils;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<ListItemType> mDataset;
    private  ItemClick myClickListener;
    private Context ctx;
    public void setOnItemClickListener(ItemClick myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(List<ListItemType> myDataset, Context ctx) {
        mDataset = myDataset;
        this.ctx=ctx;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ListItemType.TYPE_GENERAL:
               View view= inflater.inflate(R.layout.adapter_layout, parent, false);
                viewHolder= new DataObjectHolder(view);
                break;
            case ListItemType.TYPE_DATE:
                View headerView= inflater.inflate(R.layout.adapter_header, parent, false);
                viewHolder =new DateViewHolder(headerView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ListItemType.TYPE_GENERAL:
                DataObjectHolder dataObjectHolder=(DataObjectHolder)holder;
                GroupOfData mDataObject=(GroupOfData)mDataset.get(position);

                dataObjectHolder.mExpected_value.setText(mDataObject.getDataObject().getmExpected());
                dataObjectHolder.mActual_value.setText(mDataObject.getDataObject().getmActual());
              // dataObjectHolder.mSavings_value.setText(mDataObject.getDataObject().getmSavings());
             //   dataObjectHolder.deviceName_tv.setText(mDataObject.getDataObject().getDeviceNumber());
                dataObjectHolder.last_heartbeat_time_value.setText(mDataObject.getDataObject().getmDateAndTime());

                if(DateTimeUtils.getDifferenceInHours(Long.parseLong(mDataObject.getDataObject().getDateInMills()))<=4) {
                    dataObjectHolder.status_tv.setText(ctx.getResources().getString(R.string.active_txt));
                }else {
                    dataObjectHolder.status_tv.setText(ctx.getResources().getString(R.string.inactive_txt));
                }

                break;
                case ListItemType.TYPE_DATE:
                DateViewHolder dateViewHolder=(DateViewHolder)holder;
                DateItem dateItem=(DateItem)mDataset.get(position);
                dateViewHolder.dateTime_value.setText(dateItem.getDate());
                break;

        }
    }

    public void addItem(DataObject dataObj, int index) {
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position).getType();
    }
}