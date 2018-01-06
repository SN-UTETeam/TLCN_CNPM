package com.spkt.nguyenducnguu.jobstore.Recruiter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Recruiter.adapters.NTDCandidateListAdapter;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Apply;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Candidate.activities.UVProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class NTDViewListApplyActivity extends AppCompatActivity {

    ListView lv_CandidateApply;
    ImageView imgv_Back;
    List<Candidate> lstCandidate = new ArrayList<Candidate>();
    private String Key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_view_list_apply);

        addView();
        addEvent();
        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (!intent.getStringExtra("Key").isEmpty()) {
                Key = intent.getStringExtra("Key");
            }
        }
        if (Key != "") {
            Database.getData(Node.WORKINFOS + "/" + Key, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    WorkInfo w = dataSnapshot.getValue(WorkInfo.class);
                    if (w == null || w.getApplies() == null) return;

                    for (Apply ap : w.getApplies().values()) {
                        lstCandidate.clear();
                        Database.getData(Node.CANDIDATES + "/" + ap.getUserId(), new OnGetDataListener() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                Candidate can = dataSnapshot.getValue(Candidate.class);
                                if (can == null) return;
                                can.setKey(dataSnapshot.getKey());
                                lstCandidate.add(can);
                                ((BaseAdapter) lv_CandidateApply.getAdapter()).notifyDataSetChanged();
                            }

                            @Override
                            public void onFailed(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(this, "Key not found!!!", Toast.LENGTH_LONG).show();
        }

    }

    private void addView() {
        lv_CandidateApply = (ListView) findViewById(R.id.lv_CandidateApply);
        lv_CandidateApply.setAdapter(new NTDCandidateListAdapter(this, lstCandidate));

        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lv_CandidateApply.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NTDViewListApplyActivity.this, UVProfileActivity.class);
                TextView txt_Key = (TextView) view.findViewById(R.id.txt_Key);
                intent.putExtra("Key", txt_Key.getText().toString());
                startActivity(intent);
            }
        });
    }
}
