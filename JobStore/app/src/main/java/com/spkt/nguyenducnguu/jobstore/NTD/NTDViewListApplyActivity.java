package com.spkt.nguyenducnguu.jobstore.NTD;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.spkt.nguyenducnguu.jobstore.Adaper.CalidateListAdapter;
import com.spkt.nguyenducnguu.jobstore.Models.Apply;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.UV.UVViewProfileActivity;

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
    private void loadData()
    {
        Intent intent = getIntent();
        if(intent != null){
            if(!intent.getStringExtra("Key").isEmpty())
            {
                Key = intent.getStringExtra("Key");
            }
        }
        if(Key != "") {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference workInfoRef = database.getReference("WorkInfos/" + Key);
            workInfoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    WorkInfo w = dataSnapshot.getValue(WorkInfo.class);
                    if(w == null) return;

                    for(Apply ap : w.getApplies().values())
                    {
                        Query query = database.getReference("Candidates").orderByChild("email").equalTo(ap.getEmail());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot mdata : dataSnapshot.getChildren()) {
                                    Candidate can = mdata.getValue(Candidate.class);
                                    Log.d("candidate", can == null ? "candidate null" : can.getFullName().toString());
                                    if (can == null) return;
                                    lstCandidate.add(can);
                                    ((BaseAdapter) lv_CandidateApply.getAdapter()).notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
    private void addView()
    {
        lv_CandidateApply = (ListView) findViewById(R.id.lv_CandidateApply);
        lv_CandidateApply.setAdapter(new CalidateListAdapter(this, lstCandidate));

        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
    }
    private void addEvent()
    {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lv_CandidateApply.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NTDViewListApplyActivity.this, UVViewProfileActivity.class);
                TextView txt_Email = (TextView) view.findViewById(R.id.txt_Email);
                intent.putExtra("Email", txt_Email.getText().toString());
                startActivity(intent);
            }
        });
    }
}