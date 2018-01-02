package com.spkt.nguyenducnguu.jobstore;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.spkt.nguyenducnguu.jobstore.Candidate.activities.UVSeeDetailActivity;

/**
 * Created by TranAnhSon on 10/5/2017.
 */

public class Test extends AppCompatActivity {
    Dialog dialogFollow;
    Button btnFollow, btnSeeDetail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        addView();
        addEvent();

    }
    private void addEvent(){
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup(view);
            }
        });
    }
    private void addView(){
        btnFollow = (Button) findViewById(R.id.btnFollow);
    }
    private void ShowPopup(View view){
        TextView txtClose;
        Button btnFollow;
        dialogFollow = new Dialog(this);
        dialogFollow.setContentView(R.layout.dialog_see_ntd_detail);

        txtClose = (TextView) dialogFollow.findViewById(R.id.txtClose);
        btnFollow = (Button) dialogFollow.findViewById(R.id.btnFollow);
        btnSeeDetail = (Button) dialogFollow.findViewById(R.id.btnSeeDetail);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFollow.dismiss();
            }
        });
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Test.this, "You've follow", Toast.LENGTH_SHORT).show();
            }
        });
        btnSeeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Test.this, "Intent Seed detail activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Test.this, UVSeeDetailActivity.class);
                startActivity(intent);
            }
        });
        dialogFollow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFollow.show();
    }

}
