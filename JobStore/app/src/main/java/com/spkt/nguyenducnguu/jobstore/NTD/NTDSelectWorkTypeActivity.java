package com.spkt.nguyenducnguu.jobstore.NTD;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;

public class NTDSelectWorkTypeActivity extends AppCompatActivity {

    Button btnFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_select_worktype);

        addView();
        addEvent();

        //Method để sử dụng font awesome trong fragment
        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.textview_back), iconFont);

    }
    private void addView(){
        btnFinish = (Button) findViewById(R.id.btn_Finish);
    }
    private void addEvent(){
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
