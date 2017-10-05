package com.spkt.nguyenducnguu.jobstore.NTD;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;

public class NTDFilterActivity extends AppCompatActivity {
    TextView txt_Back, txt1, txt2, txt3, txt4, txt5, txt6;
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
        //5 textview set Icon
        txt1 = (TextView) findViewById(R.id.txt_icon1);
        txt2 = (TextView) findViewById(R.id.txt_icon2);
        txt3 = (TextView) findViewById(R.id.txt_icon3);
        txt4 = (TextView) findViewById(R.id.txt_icon4);
        txt5 = (TextView) findViewById(R.id.txt_icon5);
        txt6 = (TextView) findViewById(R.id.txt_icon6);

    }
    private void addEvent(){
        txt_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NTDFilterActivity.this.overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_out_right);
                finish();
            }
        });
    }
    private void setIcon(){
        txt_Back.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt1.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt2.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt3.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt4.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt5.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt6.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
    }

}
