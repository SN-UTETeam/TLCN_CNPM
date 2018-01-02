package com.spkt.nguyenducnguu.jobstore.Admin.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.spkt.nguyenducnguu.jobstore.Admin.activities.AdminChangeProfileActivity;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.Date;

public class NotificationFragment extends Fragment {

    RadioButton rdb_Candidate, rdb_Recruiter;
    EditText txt_Title, txt_Content;
    Button btn_Send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ;

        addView(view);
        addEvent();

        return view;
    }

    private void addView(View view) {
        rdb_Candidate = (RadioButton) view.findViewById(R.id.rdb_Candidate);
        rdb_Recruiter = (RadioButton) view.findViewById(R.id.rdb_Recruiter);
        txt_Title = (EditText) view.findViewById(R.id.txt_Title);
        txt_Content = (EditText) view.findViewById(R.id.txt_Content);
        btn_Send = (Button) view.findViewById(R.id.btn_Send);
    }

    private void addEvent() {
        /*btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });*/
    }

    private void showNotify(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void send() {
        if (txt_Title.getText().toString().isEmpty() || txt_Title.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Chưa nhập tiêu đề!", Toast.LENGTH_SHORT).show();
            txt_Title.requestFocus();
            return;
        }

        if (txt_Content.getText().toString().isEmpty() || txt_Content.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Chưa nhập tiêu đề!", Toast.LENGTH_SHORT).show();
            txt_Content.requestFocus();
            return;
        }

        boolean isStart = false;
        final Notification n = new Notification();
        n.setTitle(txt_Title.getText().toString());
        n.setContent(txt_Content.getText().toString());
        n.setSendTime((new Date()).getTime());
        n.setStatus(Notification.NOTIFY);
        n.setUserId(null);
        n.setWorkInfoKey(null);

        if (rdb_Candidate.isChecked()) {
            if (!isStart) {
                isStart = true;
                Database.getData(Node.CANDIDATES, new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        for (DataSnapshot mdata : dataSnapshot.getChildren()) {
                            Candidate can = mdata.getValue(Candidate.class);
                            if (can == null) return;

                            String KeyNotify = FirebaseDatabase.getInstance().getReference(Node.CANDIDATES +
                                    "/" + mdata.getKey() + "/notifications").push().getKey();
                            can.getNotifications().put(KeyNotify, n);
                            Database.updateData(Node.CANDIDATES, mdata.getKey(), can);
                        }
                        showNotify("Đã gửi thành công!");
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {
                        showNotify("Gửi thất bại!");
                    }
                });
            }
        } else if (rdb_Recruiter.isChecked()) {
            if (!isStart) {
                isStart = true;
                Database.getData(Node.RECRUITERS, new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        for (DataSnapshot mdata : dataSnapshot.getChildren()) {
                            Recruiter r = mdata.getValue(Recruiter.class);
                            if (r == null) return;

                            String KeyNotify = FirebaseDatabase.getInstance().getReference(Node.RECRUITERS +
                                    "/" + mdata.getKey() + "/notifications").push().getKey();
                            r.getNotifications().put(KeyNotify, n);
                            Database.updateData(Node.RECRUITERS, mdata.getKey(), r);
                        }
                        showNotify("Đã gửi thành công!");
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {
                        showNotify("Gửi thất bại!");
                    }
                });
            }
        }
    }
}
