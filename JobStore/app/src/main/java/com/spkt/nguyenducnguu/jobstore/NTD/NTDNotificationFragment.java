package com.spkt.nguyenducnguu.jobstore.NTD;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.spkt.nguyenducnguu.jobstore.Adaper.RecycleViewNotifiAdapter;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Adaper.RecycleViewNotifiAdapter;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.Models.Parameter;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NTDNotificationFragment extends Fragment {

    List<Notification> lstNotification = new ArrayList<Notification>();
    List<String> lstKey = new ArrayList<String>();
    RecycleViewNotifiAdapter mRcvAdapter;
    RecyclerView mRecyclerView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ntd_notification, container, false);

        addView(rootView);
        setmRecyclerView();

        mRecyclerView.setAdapter(new RecycleViewNotifiAdapter(lstNotification, lstKey));
        loadData();
        return rootView;
    }

    private void addView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_Notification);
    }

    private void loadData()
    {
        Database.getData(Node.RECRUITERS, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot recruiterData : dataSnapshot.getChildren()) {
                        if(recruiterData.child("notifications").getValue() != null)
                        {
                            for (DataSnapshot nData : recruiterData.child("notifications").getChildren())
                            {
                                lstKey.add(nData.getKey());
                                lstNotification.add(nData.getValue(Notification.class));
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, new Parameter("email", FirebaseAuth.getInstance().getCurrentUser().getEmail()));
    }

    private void setmRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new RecycleViewNotifiAdapter(lstNotification, lstKey));
    }
}
