package com.spkt.nguyenducnguu.jobstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.spkt.nguyenducnguu.jobstore.Adaper.WorkTypeListAdapter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectWorkTypeActivity extends AppCompatActivity {

    Button btn_Finish;
    ListView lv_WorkType;
    ImageView imgv_Back;
    TextView txt_Title;
    List<WorkType> lstWorkType = new ArrayList<WorkType>();
    List<String> lstWorkTypeSelected = new ArrayList<String>();
    private int countItemSelected = 0;
    private static final int MAX_SELECT = 3;
    private static final String TITLE = "Chọn loại hình công việc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worktype);

        addView();
        addEvent();

        Intent intent = getIntent();
        if(intent != null){
            if(!intent.getStringExtra("lstWorkTypeSelected").isEmpty())
            {
                lstWorkTypeSelected = Arrays.asList(intent.getStringExtra("lstWorkTypeSelected").split(", "));
                countItemSelected = lstWorkTypeSelected.size();
                txt_Title.setText("(" + countItemSelected + "/" + MAX_SELECT + ") " + TITLE);
            }
        }
        lv_WorkType.setAdapter(new WorkTypeListAdapter(this, lstWorkType, lstWorkTypeSelected));

        loadData();
    }

    private void loadData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
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
        txt_Title = (TextView) findViewById(R.id.txt_Title);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lv_WorkType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView img = (ImageView) view.findViewById(R.id.img_Check);
                if(img.getVisibility() == View.VISIBLE)
                {
                    img.setVisibility(View.INVISIBLE);
                    txt_Title.setText("(" + (--countItemSelected) + "/" + MAX_SELECT + ") " + TITLE);
                }
                else if(countItemSelected < 3)
                {
                    img.setVisibility(View.VISIBLE);
                    txt_Title.setText("(" + (++countItemSelected) + "/" + MAX_SELECT + ") " + TITLE);
                }
            }
        });
        btn_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "";
                for (int i = 0; i < lv_WorkType.getChildCount(); i++) {
                    ImageView img = (ImageView) lv_WorkType.getChildAt(i).findViewById(R.id.img_Check);
                    TextView tv = (TextView) lv_WorkType.getChildAt(i).findViewById(R.id.txt_Name);
                    if (img.getVisibility() == View.VISIBLE) data += tv.getText() + ",";
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
