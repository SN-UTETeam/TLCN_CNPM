package com.spkt.nguyenducnguu.jobstore.Candidate.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Candidate.adapters.UVNotificationAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UVNotificationFragment extends Fragment {
    RecyclerView rv_Notification;
    UVNotificationAdapter mAdapter;
    List<Notification> lstData = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_uv_notification, container, false);

        rv_Notification = (RecyclerView) rootView.findViewById(R.id.rv_Notification);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_Notification.setLayoutManager(layoutManager);
        rv_Notification.setHasFixedSize(true);

        mAdapter = new UVNotificationAdapter(lstData);
        rv_Notification.setAdapter(mAdapter);

        loadData();

        return rootView;
    }

    private void loadData() {
        Database.getData(Node.CANDIDATES + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                + "/notifications", new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                lstData.clear();
                for(DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    Notification n = mdata.getValue(Notification.class);
                    if(n == null) continue;
                    n.setKey(mdata.getKey());
                    lstData.add(n);
                    Collections.sort(lstData);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }
}
