package com.spkt.nguyenducnguu.jobstore.Recruiter.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Const.RequestCode;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectCarrerActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectExperienceActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectLevelActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectSalaryActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectWorkPlaceActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectWorkTypeActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mabbas007.tagsedittext.TagsEditText;

public class NTDEditWorkInfoActivity extends AppCompatActivity {
    Calendar cal = Calendar.getInstance();
    Button btn_Finish;
    ImageView imgv_Back;
    TagsEditText txt_WorkType, txt_Career, txt_Level, txt_Experience, txt_Salary, txt_WorkPlace;
    TextView txt_Email, txt_CompanyName, txt_ExpirationTime, txt_TitlePost,
            txt_Title, txt_Number, txt_JobDescription, txt_JobRequired, txt_Welfare;
    Button btn_AddWorkType, btn_AddCareer, btn_AddLevel, btn_AddExperience, btn_AddSalary, btn_AddWorkPlace;
    private String Key = "";
    private WorkInfo wf = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_edit_work_info);

        addView();
        addEvent();
        loadData();
        setIcon();
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
                    wf = dataSnapshot.getValue(WorkInfo.class);

                    if (wf == null) return;

                    txt_TitlePost.setText(wf.getTitlePost() == null ? "" : wf.getTitlePost());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    if(wf.getExpirationTime() != null)
                        txt_ExpirationTime.setText(sdf.format(new Date(wf.getExpirationTime())));
                    else txt_ExpirationTime.setText("");
                    String strArrtmp[] = txt_ExpirationTime.getText().toString().split("/");
                    if(strArrtmp.length >= 3)
                        cal.set(Integer.parseInt(strArrtmp[2]), Integer.parseInt(strArrtmp[1]), Integer.parseInt(strArrtmp[0]));
                    if(wf.getWorkPlace() != null)
                        txt_WorkPlace.setTags(wf.getWorkPlace().split(","));
                    if(wf.getWorkDetail() != null && wf.getWorkDetail().getWorkTypes() != null)
                        txt_WorkType.setTags(wf.getWorkDetail().getWorkTypes().split(","));
                    if(wf.getWorkDetail() != null && wf.getWorkDetail().getCarrers() != null)
                        txt_Career.setTags(wf.getWorkDetail().getCarrers().split(","));
                    if(wf.getWorkDetail() != null && wf.getWorkDetail().getLevel() != null)
                        txt_Level.setTags(wf.getWorkDetail().getLevel().split(","));
                    if(wf.getWorkDetail() != null && wf.getWorkDetail().getExperience() != null)
                        txt_Experience.setTags(wf.getWorkDetail().getExperience().split(","));
                    if(wf.getWorkDetail() != null && wf.getWorkDetail().getSalary() != null)
                        txt_Salary.setTags(wf.getWorkDetail().getSalary().split(","));
                    if(wf.getWorkDetail() != null && wf.getWorkDetail().getTitle() != null)
                        txt_Title.setText(wf.getWorkDetail().getTitle());
                    if(wf.getWorkDetail() != null)
                        txt_Number.setText(wf.getWorkDetail().getNumber() + "");
                    if(wf.getWorkDetail() != null && wf.getWorkDetail().getJobDescription() != null)
                        txt_JobDescription.setText(wf.getWorkDetail().getJobDescription());
                    if(wf.getWorkDetail() != null && wf.getWorkDetail().getJobRequired() != null)
                        txt_JobRequired.setText(wf.getWorkDetail().getJobRequired());
                    if(wf.getWorkDetail() != null && wf.getWorkDetail().getWelfare() != null)
                        txt_Welfare.setText(wf.getWorkDetail().getWelfare());

                    Database.getData(Node.RECRUITERS + "/" + wf.getUserId(), new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Recruiter r = dataSnapshot.getValue(Recruiter.class);
                            if(r == null){
                                txt_Email.setText("");
                                txt_CompanyName.setText("");
                                return;
                            }
                            txt_Email.setText(r.getEmail() == null ? "" : r.getEmail());
                            txt_CompanyName.setText(r.getCompanyName() == null ? "" : r.getCompanyName());
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
        } else {
            Toast.makeText(this, "Không thể load dữ liệu, xin hãy thử lại sau!", Toast.LENGTH_LONG).show();
        }
    }

    private void addView() {
        txt_Email = (TextView) findViewById(R.id.txt_Email);
        txt_CompanyName = (TextView) findViewById(R.id.txt_CompanyName);
        txt_ExpirationTime = (TextView) findViewById(R.id.txt_ExpirationTime);
        txt_TitlePost = (TextView) findViewById(R.id.txt_TitlePost);
        txt_Title = (TextView) findViewById(R.id.txt_Title);
        txt_Number = (TextView) findViewById(R.id.txt_Number);
        txt_JobDescription = (TextView) findViewById(R.id.txt_JobDescription);
        txt_JobRequired = (TextView) findViewById(R.id.txt_JobRequired);
        txt_Welfare = (TextView) findViewById(R.id.txt_Welfare);

        txt_WorkType = (TagsEditText) findViewById(R.id.txt_WorkType);
        txt_Career = (TagsEditText) findViewById(R.id.txt_Career);
        txt_Level = (TagsEditText) findViewById(R.id.txt_Level);
        txt_Experience = (TagsEditText) findViewById(R.id.txt_Experience);
        txt_Salary = (TagsEditText) findViewById(R.id.txt_Salary);
        txt_WorkPlace = (TagsEditText) findViewById(R.id.txt_WorkPlace);

        btn_AddWorkType = (Button) findViewById(R.id.btn_AddWorkType);
        btn_AddCareer = (Button) findViewById(R.id.btn_AddCareer);
        btn_AddLevel = (Button) findViewById(R.id.btn_AddLevel);
        btn_AddExperience = (Button) findViewById(R.id.btn_AddExperience);
        btn_AddSalary = (Button) findViewById(R.id.btn_AddSalary);
        btn_AddWorkPlace = (Button) findViewById(R.id.btn_AddWorkPlace);

        btn_Finish = (Button) findViewById(R.id.btn_Finish);
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
    }

    private boolean checkValidation() {
        if (txt_TitlePost.getText().toString().trim().isEmpty() || txt_TitlePost.getText().toString().trim() == "") {
            Toast.makeText(this, "Vui lòng nhập tiêu đề!", Toast.LENGTH_SHORT).show();
            txt_TitlePost.requestFocus();
            return false;
        }
        if (txt_ExpirationTime.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày hết hạn!", Toast.LENGTH_SHORT).show();
            txt_ExpirationTime.requestFocus();
            return false;
        }
        if (txt_WorkPlace.getTags().size() == 0) {
            Toast.makeText(this, "Vui lòng chọn địa điểm làm việc!", Toast.LENGTH_SHORT).show();
            txt_WorkPlace.requestFocus();
            return false;
        }
        if (txt_WorkType.getTags().size() == 0) {
            Toast.makeText(this, "Vui lòng chọn loại hình công việc!", Toast.LENGTH_SHORT).show();
            txt_WorkType.requestFocus();
            return false;
        }
        if (txt_Career.getTags().size() == 0) {
            Toast.makeText(this, "Vui lòng chọn ngành nghề!", Toast.LENGTH_SHORT).show();
            txt_Career.requestFocus();
            return false;
        }
        if (txt_Level.getTags().size() == 0) {
            Toast.makeText(this, "Vui lòng chọn trình độ!", Toast.LENGTH_SHORT).show();
            txt_Level.requestFocus();
            return false;
        }
        if (txt_Experience.getTags().size() == 0) {
            Toast.makeText(this, "Vui lòng chọn kinh nghiệm!", Toast.LENGTH_SHORT).show();
            txt_Experience.requestFocus();
            return false;
        }
        if (txt_Title.getText().toString().trim().isEmpty() || txt_Title.getText().toString().trim() == "") {
            Toast.makeText(this, "Vui lòng nhập chức vụ!", Toast.LENGTH_SHORT).show();
            txt_Title.requestFocus();
            return false;
        }
        if (txt_Number.getText().toString().trim().isEmpty() || txt_Number.getText().toString().trim() == "") {
            Toast.makeText(this, "Vui lòng nhập số lượng cần tuyển!", Toast.LENGTH_SHORT).show();
            txt_Number.requestFocus();
            return false;
        }
        if (txt_JobDescription.getText().toString().trim().isEmpty() || txt_JobDescription.getText().toString().trim() == "") {
            Toast.makeText(this, "Vui lòng nhập mô tả công việc!", Toast.LENGTH_SHORT).show();
            txt_JobDescription.requestFocus();
            return false;
        }
        if (txt_JobRequired.getText().toString().trim().isEmpty() || txt_JobRequired.getText().toString().trim() == "") {
            Toast.makeText(this, "Vui lòng nhập yêu cầu công việc!", Toast.LENGTH_SHORT).show();
            txt_JobRequired.requestFocus();
            return false;
        }
        if (txt_Salary.getTags().size() == 0) {
            Toast.makeText(this, "Vui lòng chọn mức lương!", Toast.LENGTH_SHORT).show();
            txt_Salary.requestFocus();
            return false;
        }
        return true;
    }

    private void update() {
        try {
            if (wf == null) {
                Toast.makeText(this, "Không thể cập nhật thông tin tuyển dụng!", Toast.LENGTH_LONG).show();
                return;
            }

            wf.setCompanyName(txt_CompanyName.getText().toString());
            wf.setTitlePost(txt_TitlePost.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                wf.setExpirationTime(sdf.parse(txt_ExpirationTime.getText().toString()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            wf.setWorkPlace(txt_WorkPlace.getTags().toString().substring(1, txt_WorkPlace.getTags().toString().length() - 1));

            wf.getWorkDetail().setWorkTypes(txt_WorkType.getTags().toString().substring(1, txt_WorkType.getTags().toString().length() - 1));
            wf.getWorkDetail().setCarrers(txt_Career.getTags().toString().substring(1, txt_Career.getTags().toString().length() - 1));
            wf.getWorkDetail().setLevel(txt_Level.getTags().toString().substring(1, txt_Level.getTags().toString().length() - 1));
            wf.getWorkDetail().setExperience(txt_Experience.getTags().toString().substring(1, txt_Experience.getTags().toString().length() - 1));
            wf.getWorkDetail().setTitle(txt_Title.getText().toString());
            wf.getWorkDetail().setNumber(Integer.parseInt(txt_Number.getText().toString()));
            wf.getWorkDetail().setJobDescription(txt_JobDescription.getText().toString());
            wf.getWorkDetail().setJobRequired(txt_JobRequired.getText().toString());
            wf.getWorkDetail().setWelfare(txt_Welfare.getText().toString());
            wf.getWorkDetail().setSalary(txt_Salary.getTags().toString().substring(1, txt_Salary.getTags().toString().length() - 1));

            Database.updateData(Node.WORKINFOS, Key, wf);
        } catch (Exception e) {

        }
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = ProgressDialog.show(NTDEditWorkInfoActivity.this, "",
                        "Please wait...", true);
                if (checkValidation()) {
                    update();
                    dialog.dismiss();
                    finish();
                }
                dialog.dismiss();
            }
        });

        txt_ExpirationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btn_AddWorkType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectWorkTypeActivity.class, txt_WorkType, "lstWorkTypeSelected", RequestCode.SELECT_WORKTYPE);
            }
        });
        btn_AddCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectCarrerActivity.class, txt_Career, "lstCareerSelected", RequestCode.SELECT_CAREER);
            }
        });
        btn_AddLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectLevelActivity.class, txt_Level, "lstLevelSelected", RequestCode.SELECT_LEVEL);
            }
        });
        btn_AddExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectExperienceActivity.class, txt_Experience, "lstExperienceSelected", RequestCode.SELECT_EXPERIENCE);
            }
        });
        btn_AddSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectSalaryActivity.class, txt_Salary, "lstSalarySelected", RequestCode.SELECT_SALARY);
            }
        });
        btn_AddWorkPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectWorkPlaceActivity.class, txt_WorkPlace, "lstWorkPlaceSelected", RequestCode.SELECT_WORKPLACE);
            }
        });
    }

    private void startActivitySelectForResult(Class selectScreen, TagsEditText tags, String keyData, int requestCode) {
        Intent intent = new Intent(this, selectScreen);
        String data = tags.getTags().toString().substring(1, tags.getTags().toString().length() - 1);
        intent.putExtra(keyData, data);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (data != null) {
                    String message = data.getStringExtra("lstWorkType");
                    txt_WorkType.setTags(message.split(","));
                }
                break;
            case 2:
                if (data != null) {
                    String message = data.getStringExtra("lstCareer");
                    txt_Career.setTags(message.split(","));
                }
                break;
            case 3:
                if (data != null) {
                    String message = data.getStringExtra("lstLevel");
                    txt_Level.setTags(message.split(","));
                }
                break;
            case 4:
                if (data != null) {
                    String message = data.getStringExtra("lstExperience");
                    txt_Experience.setTags(message.split(","));
                }
                break;
            case 5:
                if (data != null) {
                    String message = data.getStringExtra("lstSalary");
                    txt_Salary.setTags(message.split(","));
                }
                break;
            case 6:
                if (data != null) {
                    String message = data.getStringExtra("lstWorkPlace");
                    txt_WorkPlace.setTags(message.split(","));
                }
                break;
        }
    }

    private void setIcon() {
        btn_AddWorkType.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddCareer.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddLevel.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddExperience.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddSalary.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddWorkPlace.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView
                txt_ExpirationTime.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        if (txt_ExpirationTime.getText().length() == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            txt_ExpirationTime.setText(sdf.format(new Date()));
        }
        String s = txt_ExpirationTime.getText() + "";
        String strArrtmp[] = s.split("/");
        cal.set(Integer.parseInt(strArrtmp[2]), Integer.parseInt(strArrtmp[1]), Integer.parseInt(strArrtmp[0]));
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hết hạn");
        pic.show();
    }
}
