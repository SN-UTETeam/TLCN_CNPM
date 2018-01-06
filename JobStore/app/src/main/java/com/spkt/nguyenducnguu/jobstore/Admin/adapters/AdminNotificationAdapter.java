package com.spkt.nguyenducnguu.jobstore.Admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Notification> listData;
    private Context context;

    public AdminNotificationAdapter(List<Notification> listData) {
        this.listData = listData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v1 = inflater.inflate(R.layout.list_item_notification_layout, parent, false);
        viewHolder = new NotificationHolder(v1);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final NotificationHolder vh = (NotificationHolder) holder;
        Notification notification = listData.get(position);
        if(notification == null) return;
        vh.txt_Key.setText(notification.getKey() == null ? "" : notification.getKey());
        vh.txt_Title.setText(notification.getTitle() == null ? "-- Chưa cập nhật --" : notification.getTitle());
        vh.txt_Content.setText(notification.getContent() == null ? "-- Chưa cập nhật --" : notification.getContent());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if(notification.getSendTime() != null) {
            String SendTime = sdf.format(new Date(notification.getSendTime()));
            vh.txt_SendTime.setText(SendTime);
        }
        else vh.txt_SendTime.setText("null");
        vh.txt_icon1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));

        vh.ln_notification.setBackgroundColor(context.getResources().getColor(R.color.white));
        Picasso.with(context).load(R.drawable.ic_default_avatar).into(vh.imgv_Avatar);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    public class NotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CircleImageView imgv_Avatar;
        TextView txt_Title, txt_Content, txt_SendTime, txt_icon1, txt_Key, txt_UserId, txt_WorkInfoKey;
        LinearLayout ln_notification;
        private ItemClickListener clickListener;

        public NotificationHolder(View itemView) {
            super(itemView);

            imgv_Avatar = (CircleImageView) itemView.findViewById(R.id.imgv_Avatar);
            txt_Title = (TextView) itemView.findViewById(R.id.txt_Title);
            txt_Content = (TextView) itemView.findViewById(R.id.txt_Content);
            txt_SendTime = (TextView) itemView.findViewById(R.id.txt_SendTime);
            txt_Key = (TextView) itemView.findViewById(R.id.txt_Key);
            txt_UserId = (TextView) itemView.findViewById(R.id.txt_UserId);
            txt_WorkInfoKey = (TextView) itemView.findViewById(R.id.txt_WorkInfoKey);
            txt_icon1 = (TextView) itemView.findViewById(R.id.txt_icon1);
            ln_notification = (LinearLayout) itemView.findViewById(R.id.ln_notification);

            ln_notification.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null)
                clickListener.onClick(v, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            if(clickListener != null)
                clickListener.onClick(v, getPosition(), true);
            return false;
        }
    }
}
