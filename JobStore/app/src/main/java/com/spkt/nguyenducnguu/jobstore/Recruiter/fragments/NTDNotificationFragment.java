package com.spkt.nguyenducnguu.jobstore.Recruiter.fragments;

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
import com.spkt.nguyenducnguu.jobstore.Recruiter.adapters.NTDNotificationAdapter;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NTDNotificationFragment extends Fragment {

    List<Notification> lstNotification = new ArrayList<Notification>();
    RecyclerView mRecyclerView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ntd_notification, container, false);

        addView(rootView);
        setmRecyclerView();
        loadData();
        return rootView;
    }

    private void addView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_Notification);
    }

    private void loadData() {
        Database.getData(Node.RECRUITERS + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/notifications",
                new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        lstNotification.clear();
                        for (DataSnapshot mData : dataSnapshot.getChildren()) {
                            Notification n = mData.getValue(Notification.class);
                            n.setKey(mData.getKey());
                            lstNotification.add(n);
                            Collections.sort(lstNotification);
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                });
    }

    private void setmRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new NTDNotificationAdapter(lstNotification));
    }
}
