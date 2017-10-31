package com.spkt.nguyenducnguu.jobstore.UV.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sontr on 10/25/17.
 */

public class UVProfileActivity extends AppCompatActivity {
    TextView txt_Career, txt_changeProfile, txt_UVName, txt_BirthDay,
            txt_Phone, txt_Email, txt_FacebookURL, txt_Description, txt_back;
    KenBurnsView img_CoverPhotoUV;
    CircleImageView img_AvatarUV;
    TextView txt_icon1, txt_icon2, txt_icon3, txt_icon4, txt_icon5, txt_icon6, txt_iconCareer;

    TextView txt_Back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_profile);

        addView();
        addEvent();
        setIcon();
    }
    private void addView() {
        img_CoverPhotoUV = (KenBurnsView) findViewById(R.id.img_CoverPhotoUV);
        img_AvatarUV = (CircleImageView) findViewById(R.id.img_AvatarUV);
        txt_UVName = (TextView) findViewById(R.id.txt_UVName);
        txt_Career = (TextView) findViewById(R.id.txt_Career);
        txt_BirthDay = (TextView) findViewById(R.id.txt_BirthDay);
        txt_Phone = (TextView) findViewById(R.id.txt_Phone);
        txt_Email = (TextView) findViewById(R.id.txt_Email);
        txt_FacebookURL = (TextView) findViewById(R.id.txt_FacebookURL);
        txt_Description = (TextView) findViewById(R.id.txt_Description);

        txt_Back = (TextView) findViewById(R.id.txt_Back);
        txt_changeProfile = (TextView) findViewById(R.id.txt_changeProfile);
        txt_iconCareer = (TextView) findViewById(R.id.txt_iconCareer);

        txt_icon1 = (TextView) findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) findViewById(R.id.txt_icon3);
        txt_icon4 = (TextView) findViewById(R.id.txt_icon4);
        txt_icon5 = (TextView) findViewById(R.id.txt_icon5);
        txt_icon6 = (TextView) findViewById(R.id.txt_icon6);
    }
    private void addEvent(){
        txt_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(UVProfileActivity.this, "CLCKED", Toast.LENGTH_SHORT).show();
                UVProfileActivity.this.overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_out_right);
                finish();
            }
        });
        txt_changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UVProfileActivity.this, UVChangeProfileActivity.class);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_slide_in_right, R.anim.anim_slide_out_right).toBundle();
                startActivity(intent, bndlanimation);
            }
        });
    }
    private void setIcon() {
        txt_Back.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_changeProfile.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_iconCareer.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon1.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon4.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon5.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon6.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));

    }
}
