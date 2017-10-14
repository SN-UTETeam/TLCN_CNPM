package com.spkt.nguyenducnguu.jobstore.NTD;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class NTDProfileActivity extends AppCompatActivity {
    TextView txt_back, txt_changeProfile, txt_CompanyName, txt_Address, txt_FullName,
            txt_Email, txt_Phone, txt_Website, txt_Description, txt_NumberFollow;
    KenBurnsView img_CoverPhoto;
    CircleImageView img_Avatar;
    TextView txt_icon1, txt_icon2, txt_icon3, txt_icon4, txt_icon5, txt_icon6, txt_icon7;
    private String Key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_profile);

        addView();
        addEvent();
        setIcon();
        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (!intent.getStringExtra("Key").isEmpty()) {
                Key = intent.getStringExtra("Key");
            }
        }
        if (Key != "") {
            Database.getData(Node.RECRUITERS + "/" + Key, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Recruiter r = dataSnapshot.getValue(Recruiter.class);
                    if (r == null) return;

                    txt_CompanyName.setText(r.getCompanyName());
                    txt_Address.setText(r.getAddress().getAddressStr());
                    txt_NumberFollow.setText(r.getFollows().size() + "");
                    txt_FullName.setText(r.getFullName());
                    txt_Email.setText(r.getEmail());
                    txt_Phone.setText(r.getPhone());
                    txt_Website.setText(r.getWebsite());
                    txt_Description.setText(r.getDescription());
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }
    }

    private void addView() {
        img_CoverPhoto = (KenBurnsView) findViewById(R.id.img_CoverPhoto);
        img_Avatar = (CircleImageView) findViewById(R.id.img_Avatar);
        txt_back = (TextView) findViewById(R.id.txt_back);
        txt_changeProfile = (TextView) findViewById(R.id.txt_changeProfile);
        txt_CompanyName = (TextView) findViewById(R.id.txt_CompanyName);
        txt_Address = (TextView) findViewById(R.id.txt_Address);
        txt_FullName = (TextView) findViewById(R.id.txt_FullName);
        txt_Email = (TextView) findViewById(R.id.txt_Email);
        txt_Phone = (TextView) findViewById(R.id.txt_Phone);
        txt_Website = (TextView) findViewById(R.id.txt_Website);
        txt_Description = (TextView) findViewById(R.id.txt_Description);
        txt_NumberFollow = (TextView) findViewById(R.id.txt_NumberFollow);
        txt_icon1 = (TextView) findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) findViewById(R.id.txt_icon3);
        txt_icon4 = (TextView) findViewById(R.id.txt_icon4);
        txt_icon5 = (TextView) findViewById(R.id.txt_icon5);
        txt_icon6 = (TextView) findViewById(R.id.txt_icon6);
        txt_icon7 = (TextView) findViewById(R.id.txt_icon7);
    }

    private void addEvent() {
        img_CoverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        img_Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NTDProfileActivity.this.overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_out_right);
                finish();
            }
        });
        txt_changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), NTDChangeProfileActivity.class);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_slide_in_right, R.anim.anim_slide_out_right).toBundle();
                startActivity(myIntent, bndlanimation);
            }
        });
    }

    private void setIcon() {
        txt_changeProfile.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_back.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon1.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon4.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon5.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
    }
}
