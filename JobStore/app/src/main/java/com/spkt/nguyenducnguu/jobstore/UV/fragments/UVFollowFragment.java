package com.spkt.nguyenducnguu.jobstore.UV.fragments;


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
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.SearchWorkInfoSetting;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.UV.adapters.UVSearchNTDListAdapter;

import java.util.ArrayList;
import java.util.List;

public class UVFollowFragment extends Fragment {

    RecyclerView rv_Recruiter;
    UVSearchNTDListAdapter mAdapter;
    public static SearchWorkInfoSetting mSettingSearch;
    List<Recruiter> lstData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_uv_follow, container, false);
        mSettingSearch = new SearchWorkInfoSetting();
        rv_Recruiter = (RecyclerView) rootView.findViewById(R.id.rv_Recruiter);
        mAdapter = new UVSearchNTDListAdapter(lstData);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_Recruiter.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        rv_Recruiter.setHasFixedSize(true);
        rv_Recruiter.setAdapter(mAdapter);

        loadData();

        return rootView;
    }

    private void loadData()
    {
        Database.getData(Node.RECRUITERS, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                lstData.clear();
                for(DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    Recruiter r = mdata.getValue(Recruiter.class);
                    r.setKey(mdata.getKey());
                    if(r.checkFollow(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    {
                        lstData.add(r);
                        mAdapter.notifyDataSetChanged();
                        mAdapter.FilterData(mSettingSearch);
                    }
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }
}
