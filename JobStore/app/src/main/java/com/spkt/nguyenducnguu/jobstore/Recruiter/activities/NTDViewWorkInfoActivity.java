package com.spkt.nguyenducnguu.jobstore.Recruiter.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NTDViewWorkInfoActivity extends AppCompatActivity {

    TextView txt_TitlePostToolbar, txt_TitlePost, txt_Views, txt_NumberApply, txt_ExpirationTime, txt_Title;
    TextView txt_JobDescription, txt_JobRequired, txt_Level, txt_Experience, txt_Welfare, txt_Salary, txt_Number;
    TextView txt_CompanyName, txt_Name, txt_Email, txt_Phone, txt_Address;
    TextView txt_WorkType, txt_Career, txt_WorkPlace;
    TextView txt_icon1, txt_icon2, txt_icon3;
    Button btn_ListCalidate, btn_Edit, btn_Delete;
    ImageView imgv_Back;

    private String Key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_view_work_info);

        addView();
        addEvent();
        setIcon();
        loadData();
    }

    private String createTitleToolbar(String titlePost) {
        if (titlePost.length() <= 27) return titlePost;
        return titlePost.substring(0, 24) + "...";
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (!intent.getStringExtra("Key").isEmpty()) {
                Key = intent.getStringExtra("Key");
            }
        }
        if (Key != "") {
            Database.getData(Node.WORKINFOS + "/" + Key, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    WorkInfo w = dataSnapshot.getValue(WorkInfo.class);
                    if (w == null) return;

                    txt_TitlePostToolbar.setText(w.getTitlePost() == null ? "-- Chưa cập nhật --" : createTitleToolbar(w.getTitlePost()));
                    txt_TitlePost.setText(w.getTitlePost() == null ? "-- Chưa cập nhật --" : w.getTitlePost());
                    txt_Views.setText(w.getViews() + "");
                    if(w.getApplies() != null)
                        txt_NumberApply.setText(w.getApplies().size() + "");
                    else txt_NumberApply.setText("0");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    if(w.getExpirationTime() != null)
                        txt_ExpirationTime.setText(sdf.format(new Date(w.getExpirationTime())));
                    else txt_ExpirationTime.setText("-- Chưa cập nhật --");
                    if(w.getWorkDetail() != null) {
                        txt_Title.setText(w.getWorkDetail().getTitle() == null ? "-- Chưa cập nhật --" : w.getWorkDetail().getTitle());
                        txt_JobDescription.setText(w.getWorkDetail().getJobDescription() == null ? "-- Chưa cập nhật --" : w.getWorkDetail().getJobDescription());
                        txt_JobRequired.setText(w.getWorkDetail().getJobRequired() == null ? "-- Chưa cập nhật --" : w.getWorkDetail().getJobRequired());
                        txt_Level.setText(w.getWorkDetail().getLevel() == null ? "-- Chưa cập nhật --" : w.getWorkDetail().getLevel());
                        txt_Experience.setText(w.getWorkDetail().getExperience() == null ? "-- Chưa cập nhật --" : w.getWorkDetail().getExperience());
                        txt_Welfare.setText(w.getWorkDetail().getWelfare() == null ? "-- Chưa cập nhật --" : w.getWorkDetail().getWelfare());
                        txt_Salary.setText(w.getWorkDetail().getSalary() == null ? "-- Chưa cập nhật --" : w.getWorkDetail().getSalary());
                        txt_Number.setText(w.getWorkDetail().getNumber() + "");
                        txt_WorkType.setText(w.getWorkDetail().getWorkTypes() == null ? "-- Chưa cập nhật --" : w.getWorkDetail().getWorkTypes());
                        txt_Career.setText(w.getWorkDetail().getCarrers() == null ? "-- Chưa cập nhật --" : w.getWorkDetail().getCarrers());
                    }
                    else {
                        txt_Title.setText("-- Chưa cập nhật --");
                        txt_JobDescription.setText("-- Chưa cập nhật --");
                        txt_JobRequired.setText("-- Chưa cập nhật --");
                        txt_Level.setText("-- Chưa cập nhật --");
                        txt_Experience.setText("-- Chưa cập nhật --");
                        txt_Welfare.setText("-- Chưa cập nhật --");
                        txt_Salary.setText("-- Chưa cập nhật --");
                        txt_Number.setText("0");
                        txt_WorkType.setText("-- Chưa cập nhật --");
                        txt_Career.setText("-- Chưa cập nhật --");
                    }
                    txt_CompanyName.setText(w.getCompanyName() == null ? "-- Chưa cập nhật --" : w.getCompanyName());
                    txt_WorkPlace.setText(w.getWorkPlace() == null ? "-- Chưa cập nhật --" : w.getWorkPlace());

                    Database.getData(Node.RECRUITERS + "/" + w.getUserId(), new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Recruiter r = dataSnapshot.getValue(Recruiter.class);
                            if (r.getGender() == 0)
                                txt_Name.setText("Ms. " + (r.getFullName() == null ? "-- Chưa cập nhật --" : r.getFullName()));
                            else txt_Name.setText("Mr." + (r.getFullName() == null ? "-- Chưa cập nhật --" : r.getFullName()));

                            txt_Email.setText(r.getEmail() == null ? "-- Chưa cập nhật --" : r.getEmail());
                            txt_Phone.setText(r.getPhone() == null ? "-- Chưa cập nhật --" : r.getPhone());
                            if(r.getAddress() != null)
                                txt_Address.setText(r.getAddress().getAddressStr() == null ? "-- Chưa cập nhật --" : r.getAddress().getAddressStr());
                            else txt_Address.setText("-- Chưa cập nhật --");
                        }

                        @Override
                        public void onFailed(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }
    }

    private void addView() {
        txt_TitlePostToolbar = (TextView) findViewById(R.id.txt_TitlePostToolbar);
        txt_TitlePost = (TextView) findViewById(R.id.txt_TitlePost);
        txt_Views = (TextView) findViewById(R.id.txt_Views);
        txt_NumberApply = (TextView) findViewById(R.id.txt_NumberApply);
        txt_ExpirationTime = (TextView) findViewById(R.id.txt_ExpirationTime);
        txt_Title = (TextView) findViewById(R.id.txt_Title);
        txt_JobDescription = (TextView) findViewById(R.id.txt_JobDescription);
        txt_JobRequired = (TextView) findViewById(R.id.txt_JobRequired);
        txt_Level = (TextView) findViewById(R.id.txt_Level);
        txt_Experience = (TextView) findViewById(R.id.txt_Experience);
        txt_Welfare = (TextView) findViewById(R.id.txt_Welfare);
        txt_Salary = (TextView) findViewById(R.id.txt_Salary);
        txt_Number = (TextView) findViewById(R.id.txt_Number);
        txt_CompanyName = (TextView) findViewById(R.id.txt_CompanyName);
        txt_Name = (TextView) findViewById(R.id.txt_Name);
        txt_Email = (TextView) findViewById(R.id.txt_Email);
        txt_Phone = (TextView) findViewById(R.id.txt_Phone);
        txt_Address = (TextView) findViewById(R.id.txt_Address);
        txt_WorkType = (TextView) findViewById(R.id.txt_WorkType);
        txt_Career = (TextView) findViewById(R.id.txt_Career);
        txt_WorkPlace = (TextView) findViewById(R.id.txt_WorkPlace);

        txt_icon1 = (TextView) findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) findViewById(R.id.txt_icon3);

        btn_ListCalidate = (Button) findViewById(R.id.btn_ListCalidate);
        btn_Edit = (Button) findViewById(R.id.btn_Edit);
        btn_Delete = (Button) findViewById(R.id.btn_Delete);

        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_ListCalidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Key != "") {
                    Intent intent = new Intent(NTDViewWorkInfoActivity.this, NTDViewListApplyActivity.class);
                    intent.putExtra("Key", Key);
                    startActivity(intent);
                }
            }
        });

        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Key != "") {
                    Intent intent = new Intent(NTDViewWorkInfoActivity.this, NTDEditWorkInfoActivity.class);
                    intent.putExtra("Key", Key);
                    startActivity(intent);
                }
            }
        });

        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NTDViewWorkInfoActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn xóa thông tin tuyển dụng này không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int id) {
                                try {
                                    final ProgressDialog deleteProcess = ProgressDialog.show(NTDViewWorkInfoActivity.this,
                                            "", "Please wait...", true);
                                    Database.deleteData(Node.WORKINFOS, Key);
                                    dialog.dismiss();
                                    finish();
                                } catch (Exception e) {
                                    Log.d("Error", e.toString());
                                }
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).create().show();
            }
        });
    }

    private void setIcon() {
        txt_icon1.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
    }
}
