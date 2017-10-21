package com.spkt.nguyenducnguu.jobstore.Adaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.Models.Parameter;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.NTD.NTDViewListApplyActivity;
import com.spkt.nguyenducnguu.jobstore.NTD.NTDViewWorkInfoActivity;
import com.spkt.nguyenducnguu.jobstore.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

<<<<<<< HEAD:JobStore/app/src/main/java/com/spkt/nguyenducnguu/jobstore/Adaper/RecycleViewNotifiAdapter.java
public class RecycleViewNotifiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
=======
public class NotifiAdapter extends RecyclerView.Adapter<NotifiAdapter.RecycleViewHolder>{
>>>>>>> f2c81f992b4dbbd73a2c9fc80258214ca19ac435:JobStore/app/src/main/java/com/spkt/nguyenducnguu/jobstore/Adaper/NotifiAdapter.java

    private List<Notification> listData;
    private List<String> lstKey;
    private Context context;
<<<<<<< HEAD:JobStore/app/src/main/java/com/spkt/nguyenducnguu/jobstore/Adaper/RecycleViewNotifiAdapter.java

    public RecycleViewNotifiAdapter(List<Notification> listData, List<String> lstKey){
=======
    public static int ROW_INDEX = -1;
    public NotifiAdapter(Context context, List<Notification> listData){
        this.context = context;
>>>>>>> f2c81f992b4dbbd73a2c9fc80258214ca19ac435:JobStore/app/src/main/java/com/spkt/nguyenducnguu/jobstore/Adaper/NotifiAdapter.java
        this.listData = listData;
        this.lstKey = lstKey;
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
        vh.txt_Key.setText(lstKey.get(position));
        vh.txt_UserId.setText(notification.getUserId());
        vh.txt_WorkInfoKey.setText(notification.getWorkInfoKey());
        vh.txt_Title.setText(notification.getTitle());
        vh.txt_Content.setText(notification.getContent());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String SendTime = sdf.format(new Date(notification.getSendTime()));
        vh.txt_SendTime.setText(SendTime);

        vh.txt_icon1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
        if(notification.getStatus() == 0)//chưa xem
            vh.ln_notification.setBackgroundColor(context.getResources().getColor(R.color.dark1));
        else vh.ln_notification.setBackgroundColor(context.getResources().getColor(R.color.white));

        try
        {
            Database.getData(Node.CANDIDATES + "/" + notification.getUserId(), new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Candidate c = dataSnapshot.getValue(Candidate.class);
                    Picasso.with(context).load(c.getAvatar()).into(vh.imgv_Avatar);
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception ex)
        {

        }

        vh.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, final int position, boolean isLongClick) {
                final TextView txt_Key = (TextView) view.findViewById(R.id.txt_Key);
                final TextView txt_WorkInfoKey = (TextView) view.findViewById(R.id.txt_WorkInfoKey);
                final LinearLayout ln_notification = (LinearLayout) view.findViewById(R.id.ln_notification);

                Database.getData(Node.RECRUITERS, new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        for(DataSnapshot mdata : dataSnapshot.getChildren())
                        {
                            Notification n = listData.get(position);
                            n.setStatus(1);
                            Database.updateData(Node.RECRUITERS + "/" + mdata.getKey() + "/notifications", txt_Key.getText().toString(), n);
                            ln_notification.setBackgroundColor(context.getResources().getColor(R.color.white));

                            Intent intent = new Intent(context, NTDViewWorkInfoActivity.class);
                            intent.putExtra("Key", txt_WorkInfoKey.getText().toString());
                            context.startActivity(intent);
                            break;
                        }
                    }
                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                }, new Parameter("email", FirebaseAuth.getInstance().getCurrentUser().getEmail()));
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
    public class NotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
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
