package com.spkt.nguyenducnguu.jobstore.UV;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.spkt.nguyenducnguu.jobstore.R;

public class UVViewProfileActivity extends AppCompatActivity {

    private String Email = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_view_profile);

        Intent intent = getIntent();
        if(intent != null){
            if(!intent.getStringExtra("Email").isEmpty())
            {
                Email = intent.getStringExtra("Email");
            }
        }
    }
}
