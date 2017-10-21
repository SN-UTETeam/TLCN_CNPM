package com.spkt.nguyenducnguu.jobstore.NTD;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;

public class NTDSelectFilterActivity extends AppCompatActivity {
    TextView txt_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_filter);

        addView();
        setIcon();
        addEvent();

    }
    private void addView(){
        txt_Back = (TextView) findViewById(R.id.txt_back);

    }
    private void addEvent(){
        txt_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NTDSelectFilterActivity.this.overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_out_right);
                finish();
            }
        });
    }
    private void setIcon(){
        txt_Back.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
    }

}
