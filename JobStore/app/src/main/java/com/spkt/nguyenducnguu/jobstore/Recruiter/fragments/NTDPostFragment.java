package com.spkt.nguyenducnguu.jobstore.Recruiter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Recruiter.adapters.NTDWorkInfoListAdapter;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Parameter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.Recruiter.activities.NTDViewWorkInfoActivity;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NTDPostFragment extends Fragment {
    LinearLayout ln_NonData;
    ListView lv_WorkInfo;
    List<WorkInfo> lstWorkInfo = new ArrayList<WorkInfo>();
    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ntd_post, container, false);
        addView(rootView);
        addEvent();
        loadData();
        lv_WorkInfo.setAdapter(new NTDWorkInfoListAdapter(getActivity(), lstWorkInfo));

        return rootView;
    }

    private void loadData() {
        Database.getData(Node.WORKINFOS, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                lstWorkInfo.clear();
                for(DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    WorkInfo w = mdata.getValue(WorkInfo.class);
                    if(w == null || w.getExpirationTime() == null)
                    if(w.getExpirationTime() < (new Date()).getTime())
                        continue;
                    w.setKey(mdata.getKey());
                    lstWorkInfo.add(w);
                    Collections.sort(lstWorkInfo);
                    ((BaseAdapter) lv_WorkInfo.getAdapter()).notifyDataSetChanged();
                }
                if(lstWorkInfo.size() > 0) ln_NonData.setVisibility(View.GONE);
                else ln_NonData.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, new Parameter("userId", FirebaseAuth.getInstance().getCurrentUser().getUid()));
    }

    private void addView(View rootView) {
        ln_NonData = (LinearLayout) rootView.findViewById(R.id.ln_NonData);
        lv_WorkInfo = (ListView) rootView.findViewById(R.id.lv_WorkInfo);
    }

    private void addEvent() {
        lv_WorkInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NTDViewWorkInfoActivity.class);
                TextView tv = (TextView) view.findViewById(R.id.txt_Key);
                intent.putExtra("Key", tv.getText().toString());
                startActivity(intent);
            }
        });
    }
}
