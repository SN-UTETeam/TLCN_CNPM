package com.spkt.nguyenducnguu.jobstore.NTD;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.spkt.nguyenducnguu.jobstore.Adaper.RecycleViewNotifiAdapter;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

public class NTDNotificationFragment extends Fragment {

    TextView txt_Title, txt_Content, txt_Date;
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
    /*private void addNotification()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        List<Notification> lst = new ArrayList<Notification>();
        lst.add(new Notification("Ứng tuyển vị trí Android", ));

        for(int i = 0; i < lst.size(); i++)
        {
            database.getReference("Notifications").push().setValue(lst.get(i));
        }
    }*/
    private void loadData()
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Notifications").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Notification notification = dataSnapshot.getValue(Notification.class);
                lstNotification.add(notification);
                mRecyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void addView(View rootView){
        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView);
        txt_Title = (TextView) rootView.findViewById(R.id.txt_Title);
        txt_Date = (TextView) rootView.findViewById(R.id.txt_Content);
    }
   private void setmRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRcvAdapter);
    }
}
