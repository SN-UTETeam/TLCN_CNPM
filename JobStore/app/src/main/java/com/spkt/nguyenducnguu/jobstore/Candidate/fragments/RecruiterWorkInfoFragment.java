package com.spkt.nguyenducnguu.jobstore.Candidate.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Parameter;
import com.spkt.nguyenducnguu.jobstore.Models.SearchWorkInfoSetting;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Candidate.adapters.UVSearchWorkInfoAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecruiterWorkInfoFragment extends Fragment {
    private String Key = "";
    LinearLayout ln_NonData;
    RecyclerView rv_WorkInfo;
    List<WorkInfo> lstWorkInfo = new ArrayList<WorkInfo>();
    UVSearchWorkInfoAdapter mRcvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recruiter_work_info_fragment, container, false);
        if(getArguments().getString("Key") != null && !getArguments().getString("Key").isEmpty())
        {
            Key = getArguments().getString("Key");
        }
        addView(rootView);
        setmRecyclerView();
        loadData();
        return rootView;
    }

    private void addView(View view) {
        rv_WorkInfo = (RecyclerView) view.findViewById(R.id.rv_WorkInfo);
        ln_NonData = (LinearLayout) view.findViewById(R.id.ln_NonData);
    }

    private void loadData() {
        Database.getData(Node.WORKINFOS, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                lstWorkInfo.clear();
                for(DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    WorkInfo w = mdata.getValue(WorkInfo.class);
                    if(w == null || w.getExpirationTime() == null) continue;
                    if(w.getExpirationTime() < (new Date()).getTime())
                        continue;
                    w.setKey(mdata.getKey());
                    lstWorkInfo.add(w);
                    Collections.sort(lstWorkInfo);
                    mRcvAdapter.notifyDataSetChanged();
                    mRcvAdapter.FilterData(new SearchWorkInfoSetting());
                }
                if(lstWorkInfo.size() > 0) ln_NonData.setVisibility(View.GONE);
                else ln_NonData.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, new Parameter("userId", Key));
    }

    private void setmRecyclerView(){
        mRcvAdapter = new UVSearchWorkInfoAdapter(lstWorkInfo);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_WorkInfo.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        rv_WorkInfo.setHasFixedSize(true);
        rv_WorkInfo.setAdapter(mRcvAdapter);
    }
}
