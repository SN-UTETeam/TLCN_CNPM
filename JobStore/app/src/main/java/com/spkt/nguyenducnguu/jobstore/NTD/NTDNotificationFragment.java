package com.spkt.nguyenducnguu.jobstore.NTD;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.spkt.nguyenducnguu.jobstore.Adaper.RecycleViewNotifiAdapter;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

public class NTDNotificationFragment extends Fragment {

    TextView txt_Title, txt_Content, txt_Date, btn_Login;
    List<Notification> lstNotification = new ArrayList<Notification>();
    RecycleViewNotifiAdapter mRcvAdapter;
    RecyclerView mRecyclerView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ntd_notification_fragment, container, false);

        addView(rootView);
        setmRecyclerView();
        mRecyclerView.setAdapter(new RecycleViewNotifiAdapter(getActivity(), lstNotification));

        loadData();
        return rootView;
    }

    private void addView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_Notification);
        txt_Title = (TextView) rootView.findViewById(R.id.txt_Title);
        txt_Date = (TextView) rootView.findViewById(R.id.txt_Date);
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
