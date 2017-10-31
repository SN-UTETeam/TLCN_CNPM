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
import com.spkt.nguyenducnguu.jobstore.Select.adapters.LevelListAdapter;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Level;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectLevelActivity extends AppCompatActivity {
    Button btn_Finish;
    ListView lv_Level;
    ImageView imgv_Back;
    TextView txt_Title;
    List<Level> lstLevel = new ArrayList<Level>();
    List<String> lstLevelSelected = new ArrayList<String>();
    private int countItemSelected = 0;
    private static final int MAX_SELECT = 1;
    private static final String TITLE = "Chọn trình độ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);

        addView();
        addEvent();

        Intent intent = getIntent();
        if(intent != null){
            if(!intent.getStringExtra("lstLevelSelected").isEmpty())
            {
                lstLevelSelected = Arrays.asList(intent.getStringExtra("lstLevelSelected").split(", "));
                countItemSelected = lstLevelSelected.size();
                txt_Title.setText("(" + countItemSelected + "/" + MAX_SELECT + ") " + TITLE);
            }
        }

        lv_Level.setAdapter(new LevelListAdapter(this, lstLevel, lstLevelSelected));

        loadData();
    }

    private void addView() {
        btn_Finish = (Button) findViewById(R.id.btn_Finish);
        lv_Level = (ListView) findViewById(R.id.lv_Level);
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

        lv_Level.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                for (int i = 0; i < lv_Level.getChildCount(); i++) {
                    ImageView img = (ImageView) lv_Level.getChildAt(i).findViewById(R.id.img_Check);
                    TextView tv = (TextView) lv_Level.getChildAt(i).findViewById(R.id.txt_Name);
                    if (img.getVisibility() == View.VISIBLE) data += tv.getText() + ",";
                }
                if (data != "") data = data.substring(0, data.length() - 1);
                Intent intent = new Intent();
                intent.putExtra("lstLevel", data);
                setResult(3, intent);

                finish();
            }
        });
    }
    private void loadData() {
        Database.getData(Node.LEVELS, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    lstLevel.add(mdata.getValue(Level.class));
                    ((BaseAdapter) lv_Level.getAdapter()).notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }
}
