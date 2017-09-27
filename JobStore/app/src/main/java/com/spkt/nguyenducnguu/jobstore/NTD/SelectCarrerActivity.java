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
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.spkt.nguyenducnguu.jobstore.Adaper.CareerListAdapter;
import com.spkt.nguyenducnguu.jobstore.Models.Career;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

public class SelectCarrerActivity extends AppCompatActivity {
    Button btn_Finish;
    ListView lv_Career;
    ImageView imgv_Back;
    List<Career> lstCareer = new ArrayList<Career>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_carrer);

        addView();
        addEvent();

        lv_Career.setAdapter(new CareerListAdapter(this, lstCareer));

        loadData();
    }

    private void loadData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Careers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Career career = dataSnapshot.getValue(Career.class);
                lstCareer.add(career);
                ((BaseAdapter) lv_Career.getAdapter()).notifyDataSetChanged();
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
        lv_Career = (ListView) findViewById(R.id.lv_Career);
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
                for (int i = 0; i < lv_Career.getChildCount(); i++) {
                    CheckBox cb = (CheckBox) lv_Career.getChildAt(i).findViewById(R.id.cb_Check);
                    TextView tv = (TextView) lv_Career.getChildAt(i).findViewById(R.id.txt_Name);
                    if (cb.isChecked()) data += tv.getText() + ",";
                }
                if (data != "") data = data.substring(0, data.length() - 1);
                Intent intent = new Intent();
                intent.putExtra("lstCareer", data);
                setResult(2, intent);

                finish();
            }
        });
    }
}
