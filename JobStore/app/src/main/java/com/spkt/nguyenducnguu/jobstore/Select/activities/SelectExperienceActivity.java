package com.spkt.nguyenducnguu.jobstore.Select.activities;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Select.adapters.ExperienceListAdapter;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Experience;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectExperienceActivity extends AppCompatActivity {
    Button btn_Finish;
    ListView lv_Experience;
    ImageView imgv_Back;
    TextView txt_Title;
    List<Experience> lstExperience = new ArrayList<Experience>();
    List<String> lstExperienceSelected = new ArrayList<String>();
    private int countItemSelected = 0;
    private static final int MAX_SELECT = 1;
    private static final String TITLE = "Chọn kinh nghiệm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_experience);

        addView();
        addEvent();

        Intent intent = getIntent();
        if(intent != null){
            if(!intent.getStringExtra("lstExperienceSelected").isEmpty())
            {
                lstExperienceSelected = Arrays.asList(intent.getStringExtra("lstExperienceSelected").split(", "));
                countItemSelected = lstExperienceSelected.size();
                txt_Title.setText("(" + countItemSelected + "/" + MAX_SELECT + ") " + TITLE);
            }
        }

        lv_Experience.setAdapter(new ExperienceListAdapter(this, lstExperience, lstExperienceSelected));

        loadData();
    }
    private void addView() {
        btn_Finish = (Button) findViewById(R.id.btn_Finish);
        lv_Experience = (ListView) findViewById(R.id.lv_Experience);
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

        lv_Experience.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView img = (ImageView) view.findViewById(R.id.img_Check);
                if(img.getVisibility() == View.VISIBLE)
                {
                    img.setVisibility(View.INVISIBLE);
                    txt_Title.setText("(" + (--countItemSelected) + "/" + MAX_SELECT + ") " + TITLE);
                }
                else if(countItemSelected < MAX_SELECT)
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
                for (int i = 0; i < lv_Experience.getChildCount(); i++) {
                    ImageView img = (ImageView) lv_Experience.getChildAt(i).findViewById(R.id.img_Check);
                    TextView tv = (TextView) lv_Experience.getChildAt(i).findViewById(R.id.txt_Name);
                    if (img.getVisibility() == View.VISIBLE) data += tv.getText() + ",";
                }
                if (data != "") data = data.substring(0, data.length() - 1);
                Intent intent = new Intent();
                intent.putExtra("lstExperience", data);
                setResult(4, intent);

                finish();
            }
        });
    }
    private void loadData() {
        Database.getData(Node.EXPERIENCES, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    lstExperience.add(mdata.getValue(Experience.class));
                    ((BaseAdapter) lv_Experience.getAdapter()).notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }
}
