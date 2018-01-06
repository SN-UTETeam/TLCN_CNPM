package com.spkt.nguyenducnguu.jobstore.Admin.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Const.Status;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Block;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.Models.Parameter;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Candidate.activities.UVSeeDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AdminViewRecruiterAccountActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgv_Back;
    TextView txt_CompanyName, txt_Email, txt_Address, txt_CreateAt, txt_Status, txt_NumberPost, txt_NumberFollow, txt_NumberApply,
            txt_FullName, txt_BirthDay, txt_Gender, txt_Phone, txt_Website, txt_Description, txt_LastLogin;
    Button btn_Status, btn_Warning, btn_ViewProfile;
    Recruiter recruiter = null;
    String Key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_recruiter_account);

        addView();
        addEvent();
        loadData();
    }

    private void addView() {
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
        txt_CompanyName = (TextView) findViewById(R.id.txt_CompanyName);
        txt_Email = (TextView) findViewById(R.id.txt_Email);
        txt_Address = (TextView) findViewById(R.id.txt_Address);
        txt_CreateAt = (TextView) findViewById(R.id.txt_CreateAt);
        txt_Status = (TextView) findViewById(R.id.txt_Status);
        txt_NumberPost = (TextView) findViewById(R.id.txt_NumberPost);
        txt_NumberFollow = (TextView) findViewById(R.id.txt_NumberFollow);
        txt_NumberApply = (TextView) findViewById(R.id.txt_NumberApply);
        txt_FullName = (TextView) findViewById(R.id.txt_FullName);
        txt_BirthDay = (TextView) findViewById(R.id.txt_BirthDay);
        txt_Gender = (TextView) findViewById(R.id.txt_Gender);
        txt_Phone = (TextView) findViewById(R.id.txt_Phone);
        txt_Website = (TextView) findViewById(R.id.txt_Website);
        txt_Description = (TextView) findViewById(R.id.txt_Description);
        txt_LastLogin = (TextView) findViewById(R.id.txt_LastLogin);
        btn_Status = (Button) findViewById(R.id.btn_Status);
        btn_Warning = (Button) findViewById(R.id.btn_Warning);
        btn_ViewProfile = (Button) findViewById(R.id.btn_ViewProfile);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(this);
        btn_Status.setOnClickListener(this);
        btn_Warning.setOnClickListener(this);
        btn_ViewProfile.setOnClickListener(this);
    }

    private void checkStatus(int status) {
        if(status == com.spkt.nguyenducnguu.jobstore.Const.Status.ACTIVE)
        {
            txt_Status.setText("Đang hoạt động");
            btn_Status.setText("Khóa");
            btn_Status.setBackground(getResources().getDrawable(R.drawable.btn_custom_white));
            btn_Status.setTextColor(getResources().getColor(R.color.appcolor));
        }
        else if(status == com.spkt.nguyenducnguu.jobstore.Const.Status.BLOCKED)
        {
            txt_Status.setText("Đã khóa vĩnh viễn");
            if(!recruiter.getBlocked().isPermanent())
                txt_Status.setText("Mở khóa sau " + Block.getDistanceTime(recruiter.getBlocked().getFinishDate()));
            btn_Status.setText("Mở khóa");
            btn_Status.setBackground(getResources().getDrawable(R.drawable.btn_custom));
            btn_Status.setTextColor(getResources().getColor(R.color.white));
        }
        else if(status == com.spkt.nguyenducnguu.jobstore.Const.Status.PENDING)
        {
            txt_Status.setText("Đang chờ xác nhận");
            btn_Status.setText("Xác nhận");
            btn_Status.setBackground(getResources().getDrawable(R.drawable.btn_custom));
            btn_Status.setTextColor(getResources().getColor(R.color.white));
        }
        else
        {
            txt_Status.setText("???");
            btn_Status.setEnabled(false);
            btn_Warning.setEnabled(false);
        }
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
                    if(r == null) return;
                    r.setKey(dataSnapshot.getKey());
                    recruiter = r;

                    txt_CompanyName.setText(recruiter.getCompanyName() == null ? "-- Chưa cập nhật --" : recruiter.getCompanyName());
                    txt_Email.setText(recruiter.getEmail() == null ? "-- Chưa cập nhật --" : recruiter.getEmail());
                    if(recruiter.getAddress() != null)
                        txt_Address.setText(recruiter.getAddress().getAddressStr() == null ? "-- Chưa cập nhật --" : recruiter.getAddress().getAddressStr());
                    else txt_Address.setText("-- Chưa cập nhật --");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    if(recruiter.getCreateAt() != null)
                        txt_CreateAt.setText(sdf.format(new Date(recruiter.getCreateAt())));
                    else txt_CreateAt.setText("-- Chưa cập nhật --");
                    checkStatus(recruiter.getStatus());
                    if(recruiter.getFollows() != null)
                        txt_NumberFollow.setText(recruiter.getFollows().size() + "");
                    else txt_NumberFollow.setText("0");
                    Database.getData(Node.WORKINFOS, new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            int numberPost = 0, numberApply = 0;
                            for(DataSnapshot mdata : dataSnapshot.getChildren())
                            {
                                WorkInfo w = mdata.getValue(WorkInfo.class);
                                if(w == null) continue;
                                numberPost++;
                                numberApply += w.getApplies().size();
                            }
                            txt_NumberPost.setText(numberPost + "");
                            txt_NumberApply.setText(numberApply + "");
                        }

                        @Override
                        public void onFailed(DatabaseError databaseError) {

                        }
                    }, new Parameter("userId", r.getKey()));
                    txt_FullName.setText(recruiter.getFullName() == null ? "-- Chưa cập nhật --" : recruiter.getFullName());
                    if(recruiter.getBirthDay() != null)
                        txt_BirthDay.setText(sdf.format(new Date(recruiter.getBirthDay())));
                    else txt_BirthDay.setText("-- Chưa cập nhật --");
                    txt_Gender.setText(recruiter.getGender() == 1 ? "Nam" : "Nữ");
                    txt_Phone.setText(recruiter.getPhone() == null ? "-- Chưa cập nhật --" : recruiter.getPhone());
                    txt_Website.setText(recruiter.getWebsite() == null ? "-- Chưa cập nhật --" : recruiter.getWebsite());
                    txt_Description.setText(recruiter.getDescription() == null ? "-- Chưa cập nhật --" : recruiter.getDescription());
                    if(recruiter.getLastLogin() != null)
                    {
                        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        txt_LastLogin.setText(sdf.format(new Date(recruiter.getLastLogin())));
                    }
                    else txt_LastLogin.setText("Chưa login");
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.imgv_Back:
                finish();
                break;
            case R.id.btn_Status:
                if(recruiter != null)
                {
                    if(recruiter.getStatus() == Status.ACTIVE)
                        showOptionBlockDialog();
                    else if(recruiter.getStatus() == Status.BLOCKED)
                    {
                        recruiter.setBlocked(null);
                        recruiter.setStatus(Status.ACTIVE);
                    }
                    else if(recruiter.getStatus() == Status.PENDING)
                        recruiter.setStatus(Status.ACTIVE);
                    Database.updateData(Node.RECRUITERS, recruiter.getKey(), recruiter);
                }
                break;
            case R.id.btn_Warning:
                intent = new Intent(this, AdminWarningActivity.class);
                intent.putExtra("Key", recruiter.getKey());
                intent.putExtra("Account", Node.RECRUITERS);
                startActivity(intent);
                break;
            case R.id.btn_ViewProfile:
                if(recruiter == null) return;
                intent = new Intent(this, UVSeeDetailActivity.class);
                intent.putExtra("Key", recruiter.getKey());
                startActivity(intent);
                break;
        }
    }

    private void showOptionBlockDialog() {
        // khởi tạo dialog
        final Dialog dialog = new Dialog(this);
        // xét layout cho dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_block_account);

        // khai báo control trong dialog để bắt sự kiện
        final LinearLayout ln_BlockTime = (LinearLayout) dialog.findViewById(R.id.ln_BlockTime);
        final RadioButton rdb_Temporary = (RadioButton) dialog.findViewById(R.id.rdb_Temporary);
        final RadioButton rdb_Permanent = (RadioButton) dialog.findViewById(R.id.rdb_Permanent);
        final EditText txt_NumberDate = (EditText) dialog.findViewById(R.id.txt_NumberDate);
        final EditText txt_Content = (EditText) dialog.findViewById(R.id.txt_Content);

        Button btn_Cancel = (Button) dialog.findViewById(R.id.btn_Cancel);
        Button btn_Ok = (Button) dialog.findViewById(R.id.btn_Ok);
        // bắt sự kiện
        rdb_Temporary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    ln_BlockTime.setVisibility(View.VISIBLE);
            }
        });
        rdb_Permanent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    ln_BlockTime.setVisibility(View.GONE);
            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Block block = null;
                int Time = -1;
                Long FinishDate = (new Date()).getTime();

                if(rdb_Temporary.isChecked()) //Khóa tạm thời
                {
                    if(txt_NumberDate.getText().toString().isEmpty() || txt_NumberDate.getText().toString().isEmpty())
                        Toast.makeText(getBaseContext(), "Chưa nhập thời gian khóa!", Toast.LENGTH_LONG).show();
                    else
                        Time = Integer.parseInt(txt_NumberDate.getText().toString());

                    if(Time != -1)
                    {
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DAY_OF_YEAR, Time);
                        FinishDate = cal.getTime().getTime();
                    }

                    block = new Block(false, Time, FinishDate, txt_Content.getText().toString());
                }
                else if (rdb_Permanent.isChecked()) //Khóa vĩnh viễn
                {
                    block = new Block(true, Time, null, txt_Content.getText().toString());
                }

                Notification n = new Notification();
                n.setLock(true);
                n.setTitle("Tài khoản của bạn đã bị khóa " + (block.isPermanent() ? "vĩnh viễn" : (block.getTime() + " ngày")));
                n.setContent(block.getContent() + "\nMọi thắc mắc xin liên hệ jobstore.ad@gmail.com");
                n.setSendTime((new Date()).getTime());
                n.setStatus(Notification.NOTIFY);
                n.setUserId(null);
                n.setWorkInfoKey(null);

                String KeyNotify = FirebaseDatabase.getInstance().getReference(Node.RECRUITERS + "/" + recruiter.getKey() + "/notifications").push().getKey();
                recruiter.getNotifications().put(KeyNotify, n);
                recruiter.setStatus(Status.BLOCKED);
                recruiter.setBlocked(block);
                Database.updateData(Node.RECRUITERS, recruiter.getKey(), recruiter);
                dialog.dismiss();
            }
        });

        // hiển thị dialog
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
