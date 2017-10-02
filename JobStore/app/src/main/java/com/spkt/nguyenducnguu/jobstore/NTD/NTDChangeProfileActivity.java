package com.spkt.nguyenducnguu.jobstore.NTD;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;

public class NTDChangeProfileActivity extends AppCompatActivity {
    TextView txt_icon1, txt_icon2, txt_icon3, txt_icon4, txt_icon5, txt_icon6, txt_icon7;
    ImageView imgv_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_change_profile);

        addView();
        addEvent();
        setIcon();
    }
    private void addView(){
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
        txt_icon1 = (TextView) findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) findViewById(R.id.txt_icon3);
        txt_icon4 = (TextView) findViewById(R.id.txt_icon4);
        txt_icon5 = (TextView) findViewById(R.id.txt_icon5);
        txt_icon6 = (TextView) findViewById(R.id.txt_icon6);
        txt_icon7 = (TextView) findViewById(R.id.txt_icon7);
    }
    private void addEvent(){
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setIcon(){
        txt_icon1.setTypeface(FontManager.getTypeface(getApplicationContext(),FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(getApplicationContext(),FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(getApplicationContext(),FontManager.FONTAWESOME));
        txt_icon4.setTypeface(FontManager.getTypeface(getApplicationContext(),FontManager.FONTAWESOME));
        txt_icon5.setTypeface(FontManager.getTypeface(getApplicationContext(),FontManager.FONTAWESOME));
        txt_icon6.setTypeface(FontManager.getTypeface(getApplicationContext(),FontManager.FONTAWESOME));
        txt_icon7.setTypeface(FontManager.getTypeface(getApplicationContext(),FontManager.FONTAWESOME));

    }
}
