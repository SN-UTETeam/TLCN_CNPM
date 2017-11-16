package com.spkt.nguyenducnguu.jobstore.UV.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.UV.activities.UVViewNotificationActivity;
import com.spkt.nguyenducnguu.jobstore.UV.activities.UVViewWorkInfoActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UVNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Notification> listData;
    private Context context;

    public UVNotificationAdapter(List<Notification> listData) {
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
        Boolean userNotification = false;

        if (notification.getUserId() != null && !notification.getUserId().isEmpty() && !notification.getUserId().equals("")
                && notification.getWorkInfoKey() != null && !notification.getWorkInfoKey().isEmpty()
                && !notification.getWorkInfoKey().equals("")) {
            vh.txt_UserId.setText(notification.getUserId());
            vh.txt_WorkInfoKey.setText(notification.getWorkInfoKey());
            userNotification = true;
        }

        vh.txt_Key.setText(notification.getKey());
        vh.txt_Title.setText(notification.getTitle());
        vh.txt_Content.setText(notification.getContent());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String SendTime = sdf.format(new Date(notification.getSendTime()));
        vh.txt_SendTime.setText(SendTime);

        vh.txt_icon1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
        if (notification.getStatus() == 0)//chưa xem
            vh.ln_notification.setBackgroundColor(context.getResources().getColor(R.color.dark1));
        else vh.ln_notification.setBackgroundColor(context.getResources().getColor(R.color.white));

        if (userNotification) {
            try {
                Database.getData(Node.RECRUITERS + "/" + notification.getUserId(), new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Recruiter r = dataSnapshot.getValue(Recruiter.class);
                        Picasso.with(context).load(r.getAvatar()).into(vh.imgv_Avatar);
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                });
            } catch (Exception ex) {

            }
        } else Picasso.with(context).load(R.drawable.ic_default_avatar).into(vh.imgv_Avatar);

        vh.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, final int position, boolean isLongClick) {
                final TextView txt_Key = (TextView) view.findViewById(R.id.txt_Key);
                final TextView txt_WorkInfoKey = (TextView) view.findViewById(R.id.txt_WorkInfoKey);

                Notification n = listData.get(position);
                n.setStatus(1);
                Database.updateData(Node.CANDIDATES + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                        + "/notifications", txt_Key.getText().toString(), n);

                if (txt_WorkInfoKey.getText().toString() != null && !txt_WorkInfoKey.getText().toString().isEmpty()
                        && !txt_WorkInfoKey.getText().toString().equals("")) {
                    Intent intent = new Intent(context, UVViewWorkInfoActivity.class);
                    intent.putExtra("Key", txt_WorkInfoKey.getText().toString());
                    context.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context, UVViewNotificationActivity.class);
                    intent.putExtra("Key", txt_Key.getText().toString());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    public class NotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imgv_Avatar;
        TextView txt_Title, txt_Content, txt_SendTime, txt_icon1, txt_Key, txt_UserId, txt_WorkInfoKey;
        LinearLayout ln_notification;
        private ItemClickListener clickListener;

        public NotificationHolder(View itemView) {
            super(itemView);

            imgv_Avatar = (ImageView) itemView.findViewById(R.id.imgv_Avatar);
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
            clickListener.onClick(v, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onClick(v, getPosition(), true);
            return false;
        }
    }
}
