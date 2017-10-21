package com.spkt.nguyenducnguu.jobstore.NTD;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.spkt.nguyenducnguu.jobstore.Adaper.NotifiAdapter;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

public class NTDNotificationFragment extends Fragment {

    List<Notification> lstNotification = new ArrayList<Notification>();
    NotifiAdapter mRcvAdapter;
    RecyclerView mRecyclerView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ntd_notification, container, false);

        addView(rootView);
        setmRecyclerView();
        mRecyclerView.setAdapter(new NotifiAdapter(getActivity(), lstNotification));

        loadData();
        return rootView;
    }

    private void addView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_Notification);
    }

    private void loadData()
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        Query query = database.getReference("Recruiters").orderByChild("email").equalTo("jobstore.ad@gmail.com");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot recruiterData : dataSnapshot.getChildren()) {
                        if(recruiterData.child("Notifications").getValue() != null)
                        {
                            for (DataSnapshot nData : recruiterData.child("Notifications").getChildren())
                            {
                                lstNotification.add(nData.getValue(Notification.class));
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setmRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRcvAdapter);
    }
}
