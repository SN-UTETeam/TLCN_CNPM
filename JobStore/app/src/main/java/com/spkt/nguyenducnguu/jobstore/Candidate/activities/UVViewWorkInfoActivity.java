package com.spkt.nguyenducnguu.jobstore.Candidate.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Apply;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.Save;
import com.spkt.nguyenducnguu.jobstore.Models.Share;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UVViewWorkInfoActivity extends AppCompatActivity {

    TextView txt_TitlePostToolbar, txt_TitlePost, txt_Views, txt_NumberApply, txt_ExpirationTime, txt_Title;
    TextView txt_JobDescription, txt_JobRequired, txt_Level, txt_Experience, txt_Welfare, txt_Salary, txt_Number;
    TextView txt_CompanyName, txt_Name, txt_Email, txt_Phone, txt_Address;
    TextView txt_WorkType, txt_Career, txt_WorkPlace;
    TextView txt_icon1, txt_icon2, txt_icon3;

    Button btn_Apply, btn_Save, btn_Share;
    ImageView imgv_Back;

    private String Key = "";
    private WorkInfo workInfo = null;
    private Recruiter recruiter = null;
    private Candidate candidate = null;

    private boolean Applied = false;
    private boolean Saved = false;
    private boolean Shared = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_view_work_info);

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

        btn_Apply = (Button) findViewById(R.id.btn_Apply);
        btn_Save = (Button) findViewById(R.id.btn_Save);
        btn_Share = (Button) findViewById(R.id.btn_Share);

        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);

        txt_icon1 = (TextView) findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) findViewById(R.id.txt_icon3);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workInfo != null)
                {
                    if(workInfo.getExpirationTime() < (new Date()).getTime())
                    {
                        Toast.makeText(UVViewWorkInfoActivity.this, "Thông tin tuyển dụng đã hết hạn!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String NotificationKey = FirebaseDatabase.getInstance().getReference(Node.RECRUITERS
                            + "/" + workInfo.getUserId()
                            + "/notifications").push().getKey();

                    Notification n = new Notification();
                    n.setNewApply(true);
                    n.setKey(NotificationKey);
                    n.setTitle(workInfo.getTitlePost());
                    if(candidate != null)
                        n.setContent(candidate.getFullName() + " đã ứng tuyển.");
                    else n.setContent(FirebaseAuth.getInstance().getCurrentUser().getEmail() + " đã ứng tuyển.");
                    n.setStatus(Notification.NOTIFY);
                    n.setSendTime((new Date()).getTime());
                    n.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    n.setWorkInfoKey(workInfo.getKey());

                    if(Applied) //Đã ứng tuyển
                    {
                        workInfo.getApplies().remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        if(recruiter != null)
                        {
                            for(Notification notification : recruiter.getNotifications().values())
                            {
                                if(notification.getUserId().equals(n.getUserId())
                                        && notification.getWorkInfoKey().equals(n.getWorkInfoKey()))
                                {
                                    recruiter.getNotifications().remove(notification.getKey());
                                    break;
                                }
                            }
                        }
                    }
                    else //Chưa ứng tuyển
                    {
                        Apply ap = new Apply(FirebaseAuth.getInstance().getCurrentUser().getUid(), (new Date()).getTime());
                        workInfo.getApplies().put(FirebaseAuth.getInstance().getCurrentUser().getUid(), ap);

                        if(recruiter != null)
                            recruiter.getNotifications().put(NotificationKey, n);
                    }
                    Database.updateData(Node.WORKINFOS, Key, workInfo);
                    Database.updateData(Node.RECRUITERS, recruiter.getKey(), recruiter);
                }
            }
        });
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workInfo != null)
                {
                    if(Saved) //Đã lưu
                    {
                        workInfo.getSaves().remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    }
                    else //Chưa lưu
                    {
                        Save sa = new Save(FirebaseAuth.getInstance().getCurrentUser().getUid(), (new Date()).getTime());
                        workInfo.getSaves().put(FirebaseAuth.getInstance().getCurrentUser().getUid(), sa);
                    }
                    Database.updateData(Node.WORKINFOS, Key, workInfo);
                }
            }
        });
        btn_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getSubject());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getShareBody());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

                if(Shared) //Đã share
                {
                    workInfo.getShares().get(FirebaseAuth.getInstance().getCurrentUser().getUid()).setTime((new Date().getTime()));
                }
                else //Chưa share
                {
                    Share sh = new Share(FirebaseAuth.getInstance().getCurrentUser().getUid(), (new Date()).getTime());
                    workInfo.getShares().put(FirebaseAuth.getInstance().getCurrentUser().getUid(), sh);
                }
                Database.updateData(Node.WORKINFOS, Key, workInfo);
            }
        });
    }

    private String getSubject() {
        if(workInfo != null)
            return workInfo.getTitlePost();
        return "";
    }

    private String getShareBody() {
        StringBuilder shareBody = new StringBuilder();
        if(workInfo != null) {
            shareBody.append(workInfo.getTitlePost());
            shareBody.append("\nCông ty: ");
            shareBody.append(workInfo.getCompanyName());
            shareBody.append("\nLoại công việc: ");
            shareBody.append(workInfo.getWorkDetail().getWorkTypes());
            shareBody.append("\nNgành nghề: ");
            shareBody.append(workInfo.getWorkDetail().getCarrers());
            shareBody.append("\nKhu vực: ");
            shareBody.append(workInfo.getWorkPlace());
            shareBody.append("\nChức vụ: ");
            shareBody.append(workInfo.getWorkDetail().getTitle());
            shareBody.append("\nMô tả: \n\t");
            shareBody.append(workInfo.getWorkDetail().getJobDescription());
            shareBody.append("\nYêu cầu: \n\t");
            shareBody.append(workInfo.getWorkDetail().getJobRequired());
            shareBody.append("\nTrình độ: ");
            shareBody.append(workInfo.getWorkDetail().getLevel());
            shareBody.append("\nKinh nghiệm: ");
            shareBody.append(workInfo.getWorkDetail().getExperience());
            shareBody.append("\nPhúc lợi: \n\t");
            shareBody.append(workInfo.getWorkDetail().getWelfare());
            shareBody.append("\nLương: ");
            shareBody.append(workInfo.getWorkDetail().getSalary());
            shareBody.append("\nSố lượng: ");
            shareBody.append(workInfo.getWorkDetail().getNumber());
            shareBody.append(" người");
            if(recruiter != null) {
                shareBody.append("\nThông tin liên hệ: \n\t");
                shareBody.append(recruiter.getCompanyName());
                shareBody.append("\n\t");
                if (recruiter.getGender() == 1)
                    shareBody.append("Mr. ");
                else shareBody.append("Ms. ");
                shareBody.append(recruiter.getFullName());
                shareBody.append("\n\t");
                shareBody.append(recruiter.getEmail());
                shareBody.append("\n\t");
                shareBody.append(recruiter.getPhone());
                shareBody.append("\n\t");
                shareBody.append(recruiter.getAddress().getAddressStr());
            }
            shareBody.append("\n\n--- From: Job Store App ---");
        }
        return shareBody.toString();
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

                    Applied = w.checkApply(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Saved = w.checkSave(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Shared = w.checkShare(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    if(Applied) {
                        btn_Apply.setBackground(getResources().getDrawable(R.drawable.btn_custom));
                        btn_Apply.setTextColor(getResources().getColor(R.color.white));
                        btn_Apply.setText("Bỏ ứng tuyển");
                    }
                    else {
                        btn_Apply.setBackground(getResources().getDrawable(R.drawable.btn_custom_white));
                        btn_Apply.setTextColor(getResources().getColor(R.color.appcolor));
                        btn_Apply.setText("Ứng tuyển");
                    }

                    if(Saved) {
                        btn_Save.setBackground(getResources().getDrawable(R.drawable.btn_custom));
                        btn_Save.setTextColor(getResources().getColor(R.color.white));
                        btn_Save.setText("Bỏ lưu");
                    }
                    else {
                        btn_Save.setBackground(getResources().getDrawable(R.drawable.btn_custom_white));
                        btn_Save.setTextColor(getResources().getColor(R.color.appcolor));
                        btn_Save.setText("Lưu");
                    }

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

                            if(r == null) return;

                            r.setKey(dataSnapshot.getKey());
                            recruiter = r;

                            if (r.getGender() == 0)
                                txt_Name.setText("Ms. " + (r.getFullName()) == null ? "-- Chưa cập nhật --" : r.getFullName());
                            else txt_Name.setText("Mr." + (r.getFullName()) == null ? "-- Chưa cập nhật --" : r.getFullName());

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

        Database.getData(Node.CANDIDATES + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid(), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                candidate = dataSnapshot.getValue(Candidate.class);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        if(workInfo != null) {
            //Set Views
            workInfo.setViews(workInfo.getViews() + 1);
            Database.updateData(Node.WORKINFOS, Key, workInfo);
        }
        super.onDestroy();
    }

    private void setIcon() {
        txt_icon1.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
    }
}
