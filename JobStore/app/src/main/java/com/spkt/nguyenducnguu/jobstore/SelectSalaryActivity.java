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
import com.spkt.nguyenducnguu.jobstore.Adaper.SalaryListAdapter;
import com.spkt.nguyenducnguu.jobstore.Models.Salary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectSalaryActivity extends AppCompatActivity {
    Button btn_Finish;
    ListView lv_Salary;
    ImageView imgv_Back;
    TextView txt_Title;
    List<Salary> lstSalary = new ArrayList<Salary>();
    List<String> lstSalarySelected = new ArrayList<String>();
    private int countItemSelected = 0;
    private static final int MAX_SELECT = 1;
    private static final String TITLE = "Chọn mức lương";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_salary);

        addView();
        addEvent();

        Intent intent = getIntent();
        if(intent != null){
            if(!intent.getStringExtra("lstSalarySelected").isEmpty())
            {
                lstSalarySelected = Arrays.asList(intent.getStringExtra("lstSalarySelected").split(", "));
                countItemSelected = lstSalarySelected.size();
                txt_Title.setText("(" + countItemSelected + "/" + MAX_SELECT + ") " + TITLE);
            }
        }

        lv_Salary.setAdapter(new SalaryListAdapter(this, lstSalary, lstSalarySelected));

        loadData();
    }
    private void addView() {
        btn_Finish = (Button) findViewById(R.id.btn_Finish);
        lv_Salary = (ListView) findViewById(R.id.lv_Salary);
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

        lv_Salary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                for (int i = 0; i < lv_Salary.getChildCount(); i++) {
                    ImageView img = (ImageView) lv_Salary.getChildAt(i).findViewById(R.id.img_Check);
                    TextView tv = (TextView) lv_Salary.getChildAt(i).findViewById(R.id.txt_Name);
                    if (img.getVisibility() == View.VISIBLE) data += tv.getText() + ",";
                }
                if (data != "") data = data.substring(0, data.length() - 1);
                Intent intent = new Intent();
                intent.putExtra("lstSalary", data);
                setResult(5, intent);

                finish();
            }
        });
    }
    private void loadData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Salaries").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                lstSalary.add(dataSnapshot.getValue(Salary.class));
                ((BaseAdapter) lv_Salary.getAdapter()).notifyDataSetChanged();
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
}
