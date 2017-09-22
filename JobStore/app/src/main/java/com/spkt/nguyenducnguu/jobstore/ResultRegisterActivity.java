package com.spkt.nguyenducnguu.jobstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultRegisterActivity extends AppCompatActivity {

    private String Title = "null", Content = "null", Team = "null";
    private boolean Success = false;
    TextView txt_Title, txt_Content, txt_Team;
    Button btn_LoginNow;
    ImageView imgv_Back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_register);

        addView();
        addEvent();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                Title = bundle.getString("title");
                Content = bundle.getString("content");
                Team = bundle.getString("team");
                Success = bundle.getBoolean("success");
            }
        }
        if(!Success) {
            btn_LoginNow.setVisibility(View.GONE);
            txt_Title.setTextColor(getResources().getColor(R.color.red));
            txt_Content.setTextColor(getResources().getColor(R.color.red));
            txt_Team.setTextColor(getResources().getColor(R.color.red));
        }
        else imgv_Back.setVisibility(View.INVISIBLE);
        txt_Title.setText(Title);
        txt_Content.setText(Content);
        txt_Team.setText(Team);
    }
    private void addView()
    {
        txt_Title = (TextView) findViewById(R.id.txt_Title);
        txt_Content = (TextView) findViewById(R.id.txt_Content);
        txt_Team = (TextView) findViewById(R.id.txt_Team);

        btn_LoginNow = (Button) findViewById(R.id.btn_LoginNow);

        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
    }
    private void addEvent()
    {
        btn_LoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultRegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
