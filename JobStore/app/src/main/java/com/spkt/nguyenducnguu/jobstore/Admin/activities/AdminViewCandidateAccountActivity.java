package com.spkt.nguyenducnguu.jobstore.Admin.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Const.Status;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Block;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.Models.Diploma;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.Models.WorkExp;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Candidate.activities.UVProfileActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminViewCandidateAccountActivity extends AppCompatActivity implements View.OnClickListener{
    TextView txt_icon1, txt_icon2, txt_icon3, txt_icon4, txt_icon5, txt_icon6, txt_icon7, txt_icon8, txt_icon9,
            txt_icon10, txt_icon11, txt_icon12, txt_icon13;
    ImageView imgv_Back;
    TextView txt_FullName, txt_Tag, txt_Email, txt_Address, txt_CreateAt, txt_Status, txt_Gender, txt_BirthDay, txt_Phone,
            txt_FacebookURL, txt_WorkPlace, txt_WorkType, txt_Career, txt_Level, txt_Experience, txt_Salary;
    ExpandableTextView txt_Description;
    LinearLayout ln_WorkExps, ln_Diplomas;
    Button btn_Status, btn_ViewProfile, btn_Warning, btn_DownloadCV;
    Candidate candidate = null;
    String Key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_candidate_account);

        addView();
        addEvent();
        loadData();
        setIcon();
    }

    private void addView() {
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
        txt_FullName = (TextView) findViewById(R.id.txt_FullName);
        txt_Tag = (TextView) findViewById(R.id.txt_Tag);
        txt_Email = (TextView) findViewById(R.id.txt_Email);
        txt_Address = (TextView) findViewById(R.id.txt_Address);
        txt_CreateAt = (TextView) findViewById(R.id.txt_CreateAt);
        txt_Status = (TextView) findViewById(R.id.txt_Status);
        txt_Gender = (TextView) findViewById(R.id.txt_Gender);
        txt_BirthDay = (TextView) findViewById(R.id.txt_BirthDay);
        txt_Phone = (TextView) findViewById(R.id.txt_Phone);
        txt_FacebookURL = (TextView) findViewById(R.id.txt_FacebookURL);
        txt_WorkPlace = (TextView) findViewById(R.id.txt_WorkPlace);
        txt_WorkType = (TextView) findViewById(R.id.txt_WorkType);
        txt_Career = (TextView) findViewById(R.id.txt_Career);
        txt_Level = (TextView) findViewById(R.id.txt_Level);
        txt_Experience = (TextView) findViewById(R.id.txt_Experience);
        txt_Salary = (TextView) findViewById(R.id.txt_Salary);
        txt_Description = (ExpandableTextView) findViewById(R.id.txt_Description);
        ln_WorkExps = (LinearLayout) findViewById(R.id.ln_WorkExps);
        ln_Diplomas = (LinearLayout) findViewById(R.id.ln_Diplomas);
        btn_Status = (Button) findViewById(R.id.btn_Status);
        btn_ViewProfile = (Button) findViewById(R.id.btn_ViewProfile);
        btn_Warning = (Button) findViewById(R.id.btn_Warning);
        btn_DownloadCV = (Button) findViewById(R.id.btn_DownloadCV);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(this);
        btn_Status.setOnClickListener(this);
        btn_ViewProfile.setOnClickListener(this);
        btn_Warning.setOnClickListener(this);
        btn_DownloadCV.setOnClickListener(this);
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
            if(!candidate.getBlocked().isPermanent())
                txt_Status.setText("Mở khóa sau " + Block.getDistanceTime(candidate.getBlocked().getFinishDate()));
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
        if(intent != null){
            if(intent.getStringExtra("Key") != null && !intent.getStringExtra("Key").isEmpty())
            {
                Key = intent.getStringExtra("Key");
            }
        }
        if(!Key.equals(""))
        {
            Database.getData(Node.CANDIDATES + "/" + Key, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    candidate = dataSnapshot.getValue(Candidate.class);
                    if(candidate == null) return;
                    candidate.setKey(dataSnapshot.getKey());

                    txt_FullName.setText(candidate.getFullName() == null ? "-- Chưa cập nhật --" : candidate.getFullName());
                    if(candidate.getCandidateDetail() != null)
                        txt_Tag.setText(candidate.getCandidateDetail().getTag() == null ? "-- Chưa cập nhật --" : candidate.getCandidateDetail().getTag());
                    else txt_Tag.setText("-- Chưa cập nhật --");
                    txt_Email.setText(candidate.getEmail() == null ? "-- Chưa cập nhật --" : candidate.getEmail());
                    if(candidate.getAddress() != null)
                        txt_Address.setText(candidate.getAddress().getAddressStr() == null ? "-- Chưa cập nhật --" : candidate.getAddress().getAddressStr());
                    else txt_Address.setText("-- Chưa cập nhật --");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    if(candidate.getCreateAt() != null)
                        txt_CreateAt.setText(sdf.format(new Date(candidate.getCreateAt())));
                    else txt_CreateAt.setText("-- Chưa cập nhật --");
                    checkStatus(candidate.getStatus());
                    txt_Gender.setText(candidate.getGender() == 1 ? "Nam" : "Nữ");
                    if(candidate.getBirthday() != null)
                        txt_BirthDay.setText(sdf.format(new Date(candidate.getBirthday())));
                    else txt_BirthDay.setText("-- Chưa cập nhật --");
                    txt_Phone.setText(candidate.getPhone() == null ? "-- Chưa cập nhật --" : candidate.getPhone());
                    txt_FacebookURL.setText(candidate.getFacebookURL() == null ? "--- Chưa cập nhật ---" : candidate.getFacebookURL());
                    txt_Description.setText(candidate.getDescription() == null ? "-- Chưa cập nhật --" : candidate.getDescription());
                    if(candidate.getCandidateDetail() != null) {
                        txt_WorkPlace.setText(candidate.getCandidateDetail().getWorkPlaces() == null ? "-- Chưa cập nhật --" : candidate.getCandidateDetail().getWorkPlaces());
                        txt_WorkType.setText(candidate.getCandidateDetail().getWorkTypes() == null ? "-- Chưa cập nhật --" : candidate.getCandidateDetail().getWorkTypes());
                        txt_Career.setText(candidate.getCandidateDetail().getCareers() == null ? "-- Chưa cập nhật --" : candidate.getCandidateDetail().getCareers());
                        txt_Level.setText(candidate.getCandidateDetail().getLevel() == null ? "-- Chưa cập nhật --" : candidate.getCandidateDetail().getLevel());
                        txt_Experience.setText(candidate.getCandidateDetail().getExperience() == null ? "-- Chưa cập nhật --" : candidate.getCandidateDetail().getExperience());
                        txt_Salary.setText(candidate.getCandidateDetail().getSalary() == null ? "-- Chưa cập nhật --" : candidate.getCandidateDetail().getSalary());
                    }
                    else {
                        txt_WorkPlace.setText("-- Chưa cập nhật --");
                        txt_WorkType.setText("-- Chưa cập nhật --");
                        txt_Career.setText("-- Chưa cập nhật --");
                        txt_Level.setText("-- Chưa cập nhật --");
                        txt_Experience.setText("-- Chưa cập nhật --");
                        txt_Salary.setText("-- Chưa cập nhật --");
                    }
                    loadWorkExp();
                    loadDiploma();
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }
    }

    private void loadWorkExp() {
        if(candidate == null) return;

        ln_WorkExps.removeAllViews();
        List<WorkExp> lstWorkExp = new ArrayList<>();
        lstWorkExp.addAll(candidate.getCandidateDetail().getWorkExps().values());

        LinearLayout linearLayout = (LinearLayout) ln_WorkExps.getParent();
        if(lstWorkExp.size() <= 0) linearLayout.setVisibility(View.GONE);
        else linearLayout.setVisibility(View.VISIBLE);

        for (int i = 0; i < lstWorkExp.size(); i++) {
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = vi.inflate(R.layout.list_item_workexp_layout, null);

            LinearLayout ln_Parent = (LinearLayout) view.findViewById(R.id.ln_Parent);
            LinearLayout ln_Remove = (LinearLayout) view.findViewById(R.id.ln_Remove);
            Button btn_YearNumber = (Button) view.findViewById(R.id.btn_YearNumber);
            TextView txt_Id = (TextView) view.findViewById(R.id.txt_Id);
            TextView txt_Begin = (TextView) view.findViewById(R.id.txt_Begin);
            TextView txt_Finish = (TextView) view.findViewById(R.id.txt_Finish);
            TextView txt_CompanyName = (TextView) view.findViewById(R.id.txt_CompanyName);
            TextView txt_Time = (TextView) view.findViewById(R.id.txt_Time);
            TextView txt_Title = (TextView) view.findViewById(R.id.txt_Title);

            ln_Remove.setVisibility(View.GONE);
            float YEAR = 365 * 24 * 60 * 60 * 1000;
            btn_YearNumber.setText((lstWorkExp.get(i).getFinish() - lstWorkExp.get(i).getBegin()) / YEAR + "");
            txt_Id.setText(lstWorkExp.get(i).getId() + "");
            txt_Begin.setText(lstWorkExp.get(i).getBegin() + "");
            txt_Finish.setText(lstWorkExp.get(i).getFinish() + "");
            txt_CompanyName.setText(lstWorkExp.get(i).getCompanyName());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            txt_Time.setText("Từ " + sdf.format(new Date(lstWorkExp.get(i).getBegin()))
                    + " đến " + sdf.format(new Date(lstWorkExp.get(i).getFinish())));
            txt_Title.setText(lstWorkExp.get(i).getTitle());

            ln_WorkExps.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    private void loadDiploma() {
        if(candidate == null) return;

        ln_Diplomas.removeAllViews();
        List<Diploma> lstDiploma = new ArrayList<>();
        lstDiploma.addAll(candidate.getCandidateDetail().getDiplomas().values());

        LinearLayout linearLayout = (LinearLayout) ln_Diplomas.getParent();
        if(lstDiploma.size() <= 0) linearLayout.setVisibility(View.GONE);
        else linearLayout.setVisibility(View.VISIBLE);

        for (int i = 0; i < lstDiploma.size(); i++) {
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = vi.inflate(R.layout.list_item_diploma_layout, null);

            LinearLayout ln_Parent = (LinearLayout) view.findViewById(R.id.ln_Parent);
            LinearLayout ln_Remove = (LinearLayout) view.findViewById(R.id.ln_Remove);
            Button btn_Scores = (Button) view.findViewById(R.id.btn_Scores);
            TextView txt_Id = (TextView) view.findViewById(R.id.txt_Id);
            TextView txt_Name = (TextView) view.findViewById(R.id.txt_Name);
            TextView txt_IssuedBy = (TextView) view.findViewById(R.id.txt_IssuedBy);
            TextView txt_IssuedDate = (TextView) view.findViewById(R.id.txt_IssuedDate);
            TextView txt_Ranking = (TextView) view.findViewById(R.id.txt_Ranking);

            ln_Remove.setVisibility(View.GONE);
            txt_Id.setText(lstDiploma.get(i).getId() + "");
            btn_Scores.setText(lstDiploma.get(i).getScores() + "");
            txt_Name.setText(lstDiploma.get(i).getName());
            txt_IssuedBy.setText(lstDiploma.get(i).getIssuedBy());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            txt_IssuedDate.setText(sdf.format((new Date(lstDiploma.get(i).getIssuedDate())).getTime()));
            txt_Ranking.setText(lstDiploma.get(i).getRanking());

            ln_Diplomas.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    private void setIcon() {
        txt_icon1 = (TextView) findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) findViewById(R.id.txt_icon3);
        txt_icon4 = (TextView) findViewById(R.id.txt_icon4);
        txt_icon5 = (TextView) findViewById(R.id.txt_icon5);
        txt_icon6 = (TextView) findViewById(R.id.txt_icon6);
        txt_icon7 = (TextView) findViewById(R.id.txt_icon7);
        txt_icon8 = (TextView) findViewById(R.id.txt_icon8);
        txt_icon9 = (TextView) findViewById(R.id.txt_icon9);
        txt_icon10 = (TextView) findViewById(R.id.txt_icon10);
        txt_icon11 = (TextView) findViewById(R.id.txt_icon11);
        txt_icon12 = (TextView) findViewById(R.id.txt_icon12);
        txt_icon13 = (TextView) findViewById(R.id.txt_icon13);

        txt_icon1.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon4.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon5.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon6.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon7.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon8.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon9.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon10.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon11.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon12.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon13.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.imgv_Back:
                finish();
                break;
            case R.id.btn_Status:
                if (candidate != null) {
                    if (candidate.getStatus() == Status.ACTIVE)
                        showOptionBlockDialog();
                    else if (candidate.getStatus() == Status.BLOCKED) {
                        candidate.setBlocked(null);
                        candidate.setStatus(Status.ACTIVE);
                    } else if (candidate.getStatus() == Status.PENDING)
                        candidate.setStatus(Status.ACTIVE);
                    Database.updateData(Node.CANDIDATES, candidate.getKey(), candidate);
                }
                break;
            case R.id.btn_Warning:
                intent = new Intent(this, AdminWarningActivity.class);
                intent.putExtra("Key", candidate.getKey());
                intent.putExtra("Account", Node.CANDIDATES);
                startActivity(intent);
                break;
            case R.id.btn_ViewProfile:
                if (candidate == null) return;
                intent = new Intent(this, UVProfileActivity.class);
                intent.putExtra("Key", candidate.getKey());
                startActivity(intent);
                break;
            case R.id.btn_DownloadCV:
                if(candidate.getCandidateDetail().getCV() == null || candidate.getCandidateDetail().getCV().isEmpty()) {
                    Toast.makeText(this, "Chưa có CV!", Toast.LENGTH_LONG).show();
                    return;
                }
                StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(candidate.getCandidateDetail().getCV());
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Open file with user selected app
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, getContentResolver().getType(uri));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(), "Không thể tải file", Toast.LENGTH_SHORT).show();
                    }
                });
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

                String KeyNotify = FirebaseDatabase.getInstance().getReference(Node.CANDIDATES + "/" + candidate.getKey() + "/notifications").push().getKey();
                candidate.getNotifications().put(KeyNotify, n);
                candidate.setStatus(Status.BLOCKED);
                candidate.setBlocked(block);
                Database.updateData(Node.CANDIDATES, candidate.getKey(), candidate);
                dialog.dismiss();
            }
        });

        // hiển thị dialog
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
