package com.spkt.nguyenducnguu.jobstore.Adaper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotifiAdapter extends RecyclerView.Adapter<NotifiAdapter.RecycleViewHolder>{

    private List<Notification> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public static int ROW_INDEX = -1;
    public NotifiAdapter(Context context, List<Notification> listData){
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
    public void onBindViewHolder(final RecycleViewHolder holder, final int position) {
        Notification notification = listData.get(position);
        holder.txt_Title.setText(notification.getTitle());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String SendTime = sdf.format(new Date(notification.getSendTime()));
        holder.txt_SendTime.setText(SendTime);

        holder.txt_icon1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));

        holder.ln_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ROW_INDEX = position;
                notifyDataSetChanged();
            }
        });
        if(ROW_INDEX==position){
            holder.ln_notification.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder{
        //ImageView imgv_avatar_uv;
        TextView txt_Title, txt_SendTime, txt_icon1;
        LinearLayout ln_notification;
        public RecycleViewHolder(View itemView) {
            super(itemView);

            //imgv_avatar_uv = (ImageView) itemView.findViewById(R.id.imgv_avatar_uv);
            txt_Title = (TextView) itemView.findViewById(R.id.txt_Title);
            txt_SendTime = (TextView) itemView.findViewById(R.id.txt_SendTime);
            txt_icon1 = (TextView) itemView.findViewById(R.id.txt_icon1);
            ln_notification = (LinearLayout) itemView.findViewById(R.id.ln_notification);
        }
    }
}
