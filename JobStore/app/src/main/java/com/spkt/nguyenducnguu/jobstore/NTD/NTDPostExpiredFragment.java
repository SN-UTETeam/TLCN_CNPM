package com.spkt.nguyenducnguu.jobstore.NTD;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.spkt.nguyenducnguu.jobstore.Adaper.WorkInfoListAdapter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NTDPostExpiredFragment extends Fragment {
    LinearLayout ln_NonData;
    ListView lv_WorkInfoExpired;
    List<WorkInfo> lstWorkInfoExpired = new ArrayList<WorkInfo>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ntd_post_expired_fragment, container, false);

        addView(rootView);
        addEvent();
        //Method để sử dụng font awesome trong fragment
       /* Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(rootView.findViewById(R.id.Manage_Recruit), iconFont);*/

        lv_WorkInfoExpired.setAdapter(new WorkInfoListAdapter(getActivity(), lstWorkInfoExpired));
        loadData();
        return rootView;
    }
    private void loadData()
    {
        FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
        Query query = mdatabase.getReference("WorkInfos");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstWorkInfoExpired.clear();
                for(DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    WorkInfo w = mdata.getValue(WorkInfo.class);
                    if(w.getExpirationTime() > (new Date()).getTime())
                        continue;
                    w.setKey(mdata.getKey());
                    lstWorkInfoExpired.add(w);
                    Collections.sort(lstWorkInfoExpired);
                    ((BaseAdapter) lv_WorkInfoExpired.getAdapter()).notifyDataSetChanged();
                }
                if(lstWorkInfoExpired.size() > 0) ln_NonData.setVisibility(View.GONE);
                else ln_NonData.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void addView(View rootView){

        ln_NonData = (LinearLayout) rootView.findViewById(R.id.ln_NonData);
        lv_WorkInfoExpired = (ListView) rootView.findViewById(R.id.lv_WorkInfoExpired);
    }
    private void addEvent()
    {
        lv_WorkInfoExpired.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
