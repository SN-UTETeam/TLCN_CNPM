package com.spkt.nguyenducnguu.jobstore.Candidate.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.spkt.nguyenducnguu.jobstore.Candidate.activities.UVViewNotificationActivity;
import com.spkt.nguyenducnguu.jobstore.Candidate.activities.UVViewWorkInfoActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

        if (notification.isNewWorkInfo()) {
            vh.txt_UserId.setText(notification.getUserId() == null ? "" : notification.getUserId());
            vh.txt_WorkInfoKey.setText(notification.getWorkInfoKey());
        }

        vh.txt_Key.setText(notification.getKey());
        vh.txt_Title.setText(notification.getTitle());
        vh.txt_Content.setText(notification.getContent());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String SendTime = sdf.format(new Date(notification.getSendTime()));
        vh.txt_SendTime.setText(SendTime);
        vh.txt_icon1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));

        if (notification.getStatus() == Notification.SEEN)//đã xem
            vh.ln_notification.setBackgroundColor(context.getResources().getColor(R.color.white));
        else vh.ln_notification.setBackgroundColor(context.getResources().getColor(R.color.dark1));

        if(notification.isLock())
        {
            vh.txt_Title.setTextColor(context.getResources().getColor(R.color.red));
            vh.txt_Content.setTextColor(context.getResources().getColor(R.color.red));
        }
        else if(notification.isWarning())
        {
            vh.txt_Title.setTextColor(context.getResources().getColor(R.color.orange));
            vh.txt_Content.setTextColor(context.getResources().getColor(R.color.orange));
        }

        Picasso.with(context).load(R.drawable.ic_default_avatar).into(vh.imgv_Avatar);

        if (notification.isNewWorkInfo()) {
            try {
                Database.getData(Node.RECRUITERS + "/" + notification.getUserId(), new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Recruiter r = dataSnapshot.getValue(Recruiter.class);
                        if(r != null)
                            Picasso.with(context).load(r.getAvatar()).into(vh.imgv_Avatar);
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                });
            } catch (Exception ex) {

            }
        }

        vh.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, final int position, boolean isLongClick) {
                final TextView txt_Key = (TextView) view.findViewById(R.id.txt_Key);

                Notification n = null;
                for(Notification no : listData) {
                    if(no.getKey().equals(txt_Key.getText().toString())) {
                        n = no;
                        break;
                    }
                }
                if(n == null) return;
                n.setStatus(Notification.SEEN);
                Database.updateData(Node.CANDIDATES + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                        + "/notifications", n.getKey(), n);

                if (n.isNewWorkInfo()) {
                    Intent intent = new Intent(context, UVViewWorkInfoActivity.class);
                    intent.putExtra("Key", n.getWorkInfoKey());
                    context.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context, UVViewNotificationActivity.class);
                    intent.putExtra("Key", n.getKey());
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
