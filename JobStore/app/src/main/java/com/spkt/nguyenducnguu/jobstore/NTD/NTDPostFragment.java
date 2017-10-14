package com.spkt.nguyenducnguu.jobstore.NTD;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.spkt.nguyenducnguu.jobstore.Adaper.WorkInfoListAdapter;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Parameter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.spkt.nguyenducnguu.jobstore.R.id.lv_WorkInfoExpired;

public class NTDPostFragment extends Fragment {
    LinearLayout ln_NonData;
    ListView lv_WorkInfo;
    List<WorkInfo> lstWorkInfo = new ArrayList<WorkInfo>();
    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ntd_post_fragment, container, false);
        Log.d("NTD", "POST");

        addView(rootView);
        addEvent();

        //Method để sử dụng font awesome trong fragment
       /* Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(rootView.findViewById(R.id.Manage_Recruit), iconFont);*/
        loadData();
        lv_WorkInfo.setAdapter(new WorkInfoListAdapter(getActivity(), lstWorkInfo));


        return rootView;
    }

    private void loadData() {
        Database.getData(Node.RECRUITERS, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    Database.getData(Node.WORKINFOS, new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            lstWorkInfo.clear();
                            for(DataSnapshot mdata : dataSnapshot.getChildren())
                            {
                                WorkInfo w = mdata.getValue(WorkInfo.class);
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
                    }, new Parameter("userId", mdata.getKey()));
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, new Parameter("email", FirebaseAuth.getInstance().getCurrentUser().getEmail()));
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
