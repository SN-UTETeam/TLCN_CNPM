package com.spkt.nguyenducnguu.jobstore.Admin.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminViewWorkInfoActivity extends AppCompatActivity {
    TextView txt_TitlePostToolbar, txt_TitlePost, txt_Views, txt_NumberApply, txt_ExpirationTime, txt_Title;
    TextView txt_JobDescription, txt_JobRequired, txt_Level, txt_Experience, txt_Welfare, txt_Salary, txt_Number;
    TextView txt_CompanyName, txt_Name, txt_Email, txt_Phone, txt_Address;
    TextView txt_WorkType, txt_Career, txt_WorkPlace;
    TextView txt_icon1, txt_icon2, txt_icon3;

    Button btn_Delete, btn_Warning, btn_ViewRecruiter;
    ImageView imgv_Back;

    private String Key = "";
    private WorkInfo workInfo = null;
    private Recruiter recruiter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_work_info);

        addView();
        addEvent();
        loadData();
        setIcon();
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

        btn_Delete = (Button) findViewById(R.id.btn_Delete);
        btn_Warning = (Button) findViewById(R.id.btn_Warning);
        btn_ViewRecruiter = (Button) findViewById(R.id.btn_ViewRecruiter);

        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workInfo != null && recruiter != null)
                    showSendNotificationDialog(true);
            }
        });
        btn_Warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workInfo != null && recruiter != null)
                    showSendNotificationDialog(false);
            }
        });
        btn_ViewRecruiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recruiter == null) return;
                Intent intent = new Intent(AdminViewWorkInfoActivity.this, AdminViewRecruiterAccountActivity.class);
                intent.putExtra("Key", recruiter.getKey());
                startActivity(intent);
            }
        });
    }

    private String createTitleToolbar(String titlePost) {
        int count = 33;
        if (titlePost.length() <= count) return titlePost;
        return titlePost.substring(0, count - 3) + "...";
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

                    w.setKey(Key);
                    workInfo = w;

                    txt_TitlePostToolbar.setText(w.getTitlePost() == null ? "-- Chưa cập nhật --" : createTitleToolbar(w.getTitlePost()));
                    txt_TitlePost.setText(w.getTitlePost() == null ? "-- Chưa cập nhật --" : w.getTitlePost());
                    txt_Views.setText((w.getViews() + 1) + "");
                    if(w.getApplies() != null)
                        txt_NumberApply.setText(w.getApplies().size() + "");
                    else txt_NumberApply.setText("0");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    if(w.getExpirationTime() != null)
                        txt_ExpirationTime.setText(sdf.format(new Date(w.getExpirationTime())));
                    else txt_ExpirationTime.setText("-- Chưa cập nhật --");
                    txt_Title.setText(w.getWorkDetail().getTitle() == null ? "-- Chưa cập nhật --" : w.getWorkDetail().getTitle());
                    if(w.getWorkDetail() != null) {
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

                            if(r == null) return;

                            r.setKey(dataSnapshot.getKey());
                            recruiter = r;

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

    private void setIcon() {
        txt_icon1 = (TextView) findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) findViewById(R.id.txt_icon3);

        txt_icon1.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
    }

    private void showSendNotificationDialog(final boolean isDelete) {
        if(workInfo == null || recruiter == null) return;

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_send_notify);

        final TextView txt_TitleToolbar = (TextView) dialog.findViewById(R.id.txt_TitleToolbar);
        final EditText txt_Content = (EditText) dialog.findViewById(R.id.txt_Content);

        Button btn_Cancel = (Button) dialog.findViewById(R.id.btn_Cancel);
        Button btn_Ok = (Button) dialog.findViewById(R.id.btn_Ok);

        if(isDelete)
        {
            txt_TitleToolbar.setText("Xóa thông tin tuyển dụng");
            txt_Content.setHint("Nhập lý do xóa thông tin tuyển dụng này");
        }
        else
        {
            txt_TitleToolbar.setText("Cảnh báo");
            txt_Content.setHint("Nhập nội dung thông báo");
        }

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_Content.getText().toString().isEmpty() || txt_Content.getText().toString().trim() == "") {
                    txt_Content.setText("");
                    Toast.makeText(getBaseContext(), "Chưa nhập nội dung", Toast.LENGTH_LONG).show();
                    return;
                }

                Notification n = new Notification();
                if(isDelete)
                {
                    n.setDelete(true);
                    n.setTitle("[Đã bị xóa] " + workInfo.getTitlePost());
                }
                else
                {
                    n.setWarning(true);
                    n.setTitle("[Cảnh báo] " + workInfo.getTitlePost());
                    n.setWorkInfoKey(workInfo.getKey());
                }
                n.setContent(txt_Content.getText().toString() + "\nMọi thắc mắc xin liên hệ jobstore.ad@gmail.com");
                n.setSendTime((new Date()).getTime());
                n.setUserId(null);
                n.setStatus(Notification.NOTIFY);

                String KeyNotify = FirebaseDatabase.getInstance().getReference(Node.RECRUITERS + "/" + recruiter.getKey() + "/notifications").push().getKey();
                recruiter.getNotifications().put(KeyNotify, n);

                if(isDelete) Database.deleteData(Node.WORKINFOS, Key);
                Database.updateData(Node.RECRUITERS, recruiter.getKey(), recruiter);

                dialog.dismiss();
            }
        });

        // hiển thị dialog
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
