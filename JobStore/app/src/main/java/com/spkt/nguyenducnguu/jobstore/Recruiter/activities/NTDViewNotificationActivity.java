package com.spkt.nguyenducnguu.jobstore.Recruiter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NTDViewNotificationActivity extends AppCompatActivity {
    ImageView imgv_Back;
    TextView txt_TitleToolbar, txt_Title, txt_Content, txt_SendTime;
    private String Key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_view_notification);

        addView();
        addEvent();
        loadData();
    }

    private void addView() {
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
        txt_TitleToolbar = (TextView) findViewById(R.id.txt_TitleToolbar);
        txt_Title = (TextView) findViewById(R.id.txt_Title);
        txt_Content = (TextView) findViewById(R.id.txt_Content);
        txt_SendTime = (TextView) findViewById(R.id.txt_SendTime);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String createTitleToolbar(String titlePost) {
        int count = 33;
        if (titlePost.length() <= count) return titlePost;
        return titlePost.substring(0, count - 3) + "...";
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (!intent.getStringExtra("Key").isEmpty()) {
                Key = intent.getStringExtra("Key");
            }
        }
        if (Key != "") {
            Database.getData(Node.RECRUITERS + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                    + "/notifications/" + Key, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Notification n = dataSnapshot.getValue(Notification.class);
                    if(n == null) return;

                    txt_TitleToolbar.setText(n.getTitle() == null ? "-- Chưa cập nhật --" : createTitleToolbar(n.getTitle()));
                    txt_Title.setText(n.getTitle() == null ? "-- Chưa cập nhật --" : n.getTitle());
                    txt_Content.setText(n.getContent() == null ? "-- Chưa cập nhật --" : n.getContent());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    if(n.getSendTime() != null) {
                        String SendTime = sdf.format(new Date(n.getSendTime()));
                        txt_SendTime.setText(SendTime);
                    }
                    else txt_SendTime.setText("-- Chưa cập nhật --");
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }
    }
}
