package com.spkt.nguyenducnguu.jobstore.Admin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.spkt.nguyenducnguu.jobstore.Admin.adapters.AdminNotificationAdapter;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AdminWarningActivity extends AppCompatActivity {
    ImageView imgv_Back;
    EditText txt_Title, txt_Content;
    Button btn_Send;
    RecyclerView rv_WarningNotification;
    AdminNotificationAdapter mAdapter;
    List<Notification> lstData = new ArrayList<>();
    String Key = "";
    String Account = "";
    Recruiter recruiter = null;
    Candidate candidate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_warning);

        addView();
        addEvent();
        setRecyclerView();
        loadData();
    }
    private void addView() {
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
        txt_Title = (EditText) findViewById(R.id.txt_Title);
        txt_Content = (EditText) findViewById(R.id.txt_Content);
        btn_Send = (Button) findViewById(R.id.btn_Send);
        rv_WarningNotification = (RecyclerView) findViewById(R.id.rv_WarningNotification);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recruiter == null && candidate == null) return;

                if(txt_Title.getText().toString().isEmpty() || txt_Title.getText().toString().trim() == "") {
                    Toast.makeText(getBaseContext(), "Chưa nhập tiêu đề", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(txt_Content.getText().toString().isEmpty() || txt_Content.getText().toString().trim() == "") {
                    Toast.makeText(getBaseContext(), "Chưa nhập nội dung", Toast.LENGTH_LONG).show();
                    return;
                }
                Notification n = new Notification();
                n.setWarning(true);
                n.setTitle(txt_Title.getText().toString());
                n.setContent(txt_Content.getText().toString() + "\nMọi thắc mắc xin liên hệ jobstore.ad@gmail.com");
                n.setSendTime((new Date()).getTime());
                n.setStatus(Notification.NOTIFY);
                n.setUserId(null);
                n.setWorkInfoKey(null);

                if(recruiter != null) {
                    String KeyNotify = FirebaseDatabase.getInstance().getReference(Node.RECRUITERS + "/" + recruiter.getKey() + "/notifications").push().getKey();
                    recruiter.getNotifications().put(KeyNotify, n);
                    Database.updateData(Node.RECRUITERS, recruiter.getKey(), recruiter);
                }
                else if(candidate != null){
                    String KeyNotify = FirebaseDatabase.getInstance().getReference(Node.CANDIDATES + "/" + candidate.getKey() + "/notifications").push().getKey();
                    candidate.getNotifications().put(KeyNotify, n);
                    Database.updateData(Node.CANDIDATES, candidate.getKey(), candidate);
                }
                txt_Title.setText("");
                txt_Content.setText("");
                Toast.makeText(getBaseContext(), "Đã gửi thành công", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setRecyclerView() {
        mAdapter = new AdminNotificationAdapter(lstData);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_WarningNotification.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        rv_WarningNotification.setHasFixedSize(true);
        rv_WarningNotification.setAdapter(mAdapter);
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (!intent.getStringExtra("Key").isEmpty()) {
                Key = intent.getStringExtra("Key");
            }
            if (!intent.getStringExtra("Account").isEmpty()) {
                Account = intent.getStringExtra("Account");
            }
        }
        if (!Key.equals("") && !Account.equals("")) {
            Database.getData(Account + "/" + Key, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if(Account.equals(Node.RECRUITERS)) {
                        Recruiter r = dataSnapshot.getValue(Recruiter.class);
                        if (r == null || r.getNotifications() == null) return;
                        r.setKey(dataSnapshot.getKey());
                        recruiter = r;
                        lstData.clear();
                        for (Notification no : recruiter.getNotifications().values()) {
                            if (no.isWarning() || no.isLock()) {
                                lstData.add(no);
                                Collections.sort(lstData);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    else if(Account.equals(Node.CANDIDATES)) {
                        Candidate can = dataSnapshot.getValue(Candidate.class);
                        if (can == null || can.getNotifications() == null) return;
                        can.setKey(dataSnapshot.getKey());
                        candidate = can;
                        lstData.clear();
                        for (Notification no : candidate.getNotifications().values()) {
                            if (no.isWarning() || no.isLock()) {
                                lstData.add(no);
                                Collections.sort(lstData);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }
    }
}
