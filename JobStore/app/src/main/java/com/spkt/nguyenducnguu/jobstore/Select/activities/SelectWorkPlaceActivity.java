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
import com.spkt.nguyenducnguu.jobstore.Select.adapters.WorkPlaceListAdapter;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.WorkPlace;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectWorkPlaceActivity extends AppCompatActivity {
    Button btn_Finish;
    ListView lv_WorkPlace;
    ImageView imgv_Back;
    TextView txt_Title;
    List<WorkPlace> lstWorkPlace = new ArrayList<WorkPlace>();
    List<String> lstWorkPlaceSelected = new ArrayList<String>();
    private int countItemSelected = 0;
    private static final int MAX_SELECT = 3;
    private static final String TITLE = "Chọn nơi làm việc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_work_place);

        addView();
        addEvent();

        Intent intent = getIntent();
        if(intent != null){
            if(!intent.getStringExtra("lstWorkPlaceSelected").isEmpty())
            {
                lstWorkPlaceSelected = Arrays.asList(intent.getStringExtra("lstWorkPlaceSelected").split(", "));
                countItemSelected = lstWorkPlaceSelected.size();
                txt_Title.setText("(" + countItemSelected + "/" + MAX_SELECT + ") " + TITLE);
            }
        }
        lv_WorkPlace.setAdapter(new WorkPlaceListAdapter(this, lstWorkPlace, lstWorkPlaceSelected));

        loadData();
    }
    private void loadData() {
        Database.getData(Node.WORKPLACES, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    lstWorkPlace.add(mdata.getValue(WorkPlace.class));
                    ((BaseAdapter) lv_WorkPlace.getAdapter()).notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    private void addView() {
        btn_Finish = (Button) findViewById(R.id.btn_Finish);
        lv_WorkPlace = (ListView) findViewById(R.id.lv_WorkPlace);
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
        lv_WorkPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView img = (ImageView) lv_WorkPlace.getChildAt(i).findViewById(R.id.img_Check);
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
                for (int i = 0; i < lv_WorkPlace.getChildCount(); i++) {
                    ImageView img = (ImageView) lv_WorkPlace.getChildAt(i).findViewById(R.id.img_Check);
                    TextView tv = (TextView) lv_WorkPlace.getChildAt(i).findViewById(R.id.txt_Name);
                    if (img.getVisibility() == View.VISIBLE) data += tv.getText() + ",";
                }
                if (data != "") data = data.substring(0, data.length() - 1);
                Intent intent = new Intent();
                intent.putExtra("lstWorkPlace", data);
                setResult(6, intent);

                finish();
            }
        });
    }
}
