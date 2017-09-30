package com.spkt.nguyenducnguu.jobstore.Adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.List;

/**
 * Created by TranAnhSon on 9/29/2017.
 */

public class RecycleViewNotifiAdapter extends RecyclerView.Adapter<RecycleViewNotifiAdapter.RecycleViewHolder>{

    private List<Notification> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public RecycleViewNotifiAdapter(Context context, List<Notification> listData){
        this.context = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //dùng để gán giao diện cho một phần tử của RecyclerView
        View view = inflater.inflate(R.layout.list_item_notification_layout, parent, false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewNotifiAdapter.RecycleViewHolder holder, int position) {
        Notification notification = listData.get(position);
        holder.txt_Title.setText(notification.getTitle());
        holder.txt_Date.setText(notification.getSendTime().toString());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder{
        //ImageView imgv_avatar_uv; //(Chua co models uv)
        TextView txt_Title, txt_Date;
        public RecycleViewHolder(View itemView) {
            super(itemView);

            //imgv_avatar_uv = (ImageView) itemView.findViewById(R.id.imgv_avatar_uv);  //(Chua co models uv)
            txt_Title = (TextView) itemView.findViewById(R.id.txt_Title);
            txt_Date = (TextView) itemView.findViewById(R.id.txt_Date);
        }
    }
}