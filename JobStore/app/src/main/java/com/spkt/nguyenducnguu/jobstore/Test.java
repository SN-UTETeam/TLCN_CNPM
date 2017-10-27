package com.spkt.nguyenducnguu.jobstore;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by TranAnhSon on 10/5/2017.
 */

public class Test extends AppCompatActivity {
    Dialog dialogFollow;
    Button btnFollow;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        btnFollow = (Button) findViewById(R.id.btnFollow);
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup(view);
            }
        });
    }
    private void ShowPopup(View view){
        TextView txtClose;
        Button btnFollow;
        dialogFollow = new Dialog(this);
        dialogFollow.setContentView(R.layout.test2);

        txtClose = (TextView) dialogFollow.findViewById(R.id.txtClose);
        btnFollow = (Button) dialogFollow.findViewById(R.id.btnFollow);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFollow.dismiss();
            }
        });
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Test.this, "You've follow", Toast.LENGTH_LONG).show();
            }
        });
        dialogFollow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFollow.show();
    }

}
