package com.spkt.nguyenducnguu.jobstore.NTD;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.spkt.nguyenducnguu.jobstore.Adaper.WorkTypeListAdapter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkType;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

public class SelectWorkTypeActivity extends AppCompatActivity {

    Button btn_Finish;
    ListView lv_WorkType;
    ImageView imgv_Back;
    List<WorkType> lstWorkType = new ArrayList<WorkType>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worktype);

        addView();
        addEvent();

        lv_WorkType.setAdapter(new WorkTypeListAdapter(this, lstWorkType));

        loadData();
    }

    private void loadData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("WorkTypes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                WorkType workType = dataSnapshot.getValue(WorkType.class);
                lstWorkType.add(workType);
                ((BaseAdapter) lv_WorkType.getAdapter()).notifyDataSetChanged();
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

    private void addView() {
        btn_Finish = (Button) findViewById(R.id.btn_Finish);
        lv_WorkType = (ListView) findViewById(R.id.lv_WorkType);
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "";
                for (int i = 0; i < lv_WorkType.getChildCount(); i++) {
                    CheckBox cb = (CheckBox) lv_WorkType.getChildAt(i).findViewById(R.id.cb_Check);
                    if (cb.isChecked()) data += lstWorkType.get(i).getName() + ",";
                }
                if (data != "") data = data.substring(0, data.length() - 1);
                Intent intent = new Intent();
                intent.putExtra("lstWorkType", data);
                setResult(1, intent);

                finish();
            }
        });
    }
}
