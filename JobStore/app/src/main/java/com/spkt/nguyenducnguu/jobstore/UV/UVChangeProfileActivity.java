package com.spkt.nguyenducnguu.jobstore.UV;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;

public class UVChangeProfileActivity extends AppCompatActivity {
    Button btn_Finish;
    TextView txt_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_change_profile);
        addView();
        addEvent();
        setIcon();
    }
    private void addView() {
        btn_Finish = (Button) findViewById(R.id.btn_Finish);
        txt_Back = (TextView) findViewById(R.id.txt_back);
    }
    private void addEvent() {
        txt_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UVChangeProfileActivity.this.overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_out_right);
                finish();
            }
        });

    }
    private void setIcon() {
        txt_Back.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
    }
}
