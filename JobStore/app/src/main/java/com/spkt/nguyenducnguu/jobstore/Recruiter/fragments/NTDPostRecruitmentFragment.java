package com.spkt.nguyenducnguu.jobstore.Recruiter.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Const.RequestCode;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Follow;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkDetail;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectCarrerActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectExperienceActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectLevelActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectSalaryActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectWorkPlaceActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectWorkTypeActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mabbas007.tagsedittext.TagsEditText;

public class NTDPostRecruitmentFragment extends Fragment {

    Calendar cal = Calendar.getInstance();
    Button btn_Complete;
    TagsEditText txt_WorkType, txt_Career, txt_Level, txt_Experience, txt_Salary, txt_WorkPlace;
    TextView txt_Email, txt_CompanyName, txt_ExpirationTime, txt_TitlePost,
            txt_Title, txt_Number, txt_JobDescription, txt_JobRequired, txt_Welfare;
    Button btn_AddWorkType, btn_AddCareer, btn_AddLevel, btn_AddExperience, btn_AddSalary, btn_AddWorkPlace;
    private Recruiter recruiter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ntd_post_recruitment, container, false);

        addView(rootView);
        loadData();
        addEvent();
        setIcon();
        return rootView;
    }

    private void loadData() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        txt_Email.setText(mAuth.getCurrentUser().getEmail());

        Database.getData(Node.RECRUITERS + "/" + mAuth.getCurrentUser().getUid(),
                new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        recruiter = dataSnapshot.getValue(Recruiter.class);
                        recruiter.setKey(dataSnapshot.getKey() == null ? "" : dataSnapshot.getKey());
                        txt_CompanyName.setText(recruiter.getCompanyName() == null ? "-- Chưa cập nhật --" : recruiter.getCompanyName());
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                });
    }

    private void addView(View rootView) {
        txt_Email = (TextView) rootView.findViewById(R.id.txt_Email);
        txt_CompanyName = (TextView) rootView.findViewById(R.id.txt_CompanyName);
        txt_ExpirationTime = (TextView) rootView.findViewById(R.id.txt_ExpirationTime);
        txt_TitlePost = (TextView) rootView.findViewById(R.id.txt_TitlePost);
        txt_Title = (TextView) rootView.findViewById(R.id.txt_Title);
        txt_Number = (TextView) rootView.findViewById(R.id.txt_Number);
        txt_JobDescription = (TextView) rootView.findViewById(R.id.txt_JobDescription);
        txt_JobRequired = (TextView) rootView.findViewById(R.id.txt_JobRequired);
        txt_Welfare = (TextView) rootView.findViewById(R.id.txt_Welfare);

        txt_WorkType = (TagsEditText) rootView.findViewById(R.id.txt_WorkType);
        txt_Career = (TagsEditText) rootView.findViewById(R.id.txt_Career);
        txt_Level = (TagsEditText) rootView.findViewById(R.id.txt_Level);
        txt_Experience = (TagsEditText) rootView.findViewById(R.id.txt_Experience);
        txt_Salary = (TagsEditText) rootView.findViewById(R.id.txt_Salary);
        txt_WorkPlace = (TagsEditText) rootView.findViewById(R.id.txt_WorkPlace);

        btn_AddWorkType = (Button) rootView.findViewById(R.id.btn_AddWorkType);
        btn_AddCareer = (Button) rootView.findViewById(R.id.btn_AddCareer);
        btn_AddLevel = (Button) rootView.findViewById(R.id.btn_AddLevel);
        btn_AddExperience = (Button) rootView.findViewById(R.id.btn_AddExperience);
        btn_AddSalary = (Button) rootView.findViewById(R.id.btn_AddSalary);
        btn_AddWorkPlace = (Button) rootView.findViewById(R.id.btn_AddWorkPlace);

        btn_Complete = (Button) rootView.findViewById(R.id.btn_Complete);
    }

    private boolean checkValidation() {
        if (txt_TitlePost.getText().toString().trim().isEmpty() || txt_TitlePost.getText().toString().trim() == "") {
            Toast.makeText(getActivity(), "Vui lòng nhập tiêu đề!", Toast.LENGTH_SHORT).show();
            txt_TitlePost.requestFocus();
            return false;
        }
        if (txt_ExpirationTime.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng chọn ngày hết hạn!", Toast.LENGTH_SHORT).show();
            txt_ExpirationTime.requestFocus();
            return false;
        }
        if (txt_WorkPlace.getTags().size() == 0) {
            Toast.makeText(getActivity(), "Vui lòng chọn địa điểm làm việc!", Toast.LENGTH_SHORT).show();
            txt_WorkPlace.requestFocus();
            return false;
        }
        if (txt_WorkType.getTags().size() == 0) {
            Toast.makeText(getActivity(), "Vui lòng chọn loại hình công việc!", Toast.LENGTH_SHORT).show();
            txt_WorkType.requestFocus();
            return false;
        }
        if (txt_Career.getTags().size() == 0) {
            Toast.makeText(getActivity(), "Vui lòng chọn ngành nghề!", Toast.LENGTH_SHORT).show();
            txt_Career.requestFocus();
            return false;
        }
        if (txt_Level.getTags().size() == 0) {
            Toast.makeText(getActivity(), "Vui lòng chọn trình độ!", Toast.LENGTH_SHORT).show();
            txt_Level.requestFocus();
            return false;
        }
        if (txt_Experience.getTags().size() == 0) {
            Toast.makeText(getActivity(), "Vui lòng chọn kinh nghiệm!", Toast.LENGTH_SHORT).show();
            txt_Experience.requestFocus();
            return false;
        }
        if (txt_Title.getText().toString().trim().isEmpty() || txt_Title.getText().toString().trim() == "") {
            Toast.makeText(getActivity(), "Vui lòng nhập chức vụ!", Toast.LENGTH_SHORT).show();
            txt_Title.requestFocus();
            return false;
        }
        if (txt_Number.getText().toString().trim().isEmpty() || txt_Number.getText().toString().trim() == "") {
            Toast.makeText(getActivity(), "Vui lòng nhập số lượng cần tuyển!", Toast.LENGTH_SHORT).show();
            txt_Number.requestFocus();
            return false;
        }
        if (txt_JobDescription.getText().toString().trim().isEmpty() || txt_JobDescription.getText().toString().trim() == "") {
            Toast.makeText(getActivity(), "Vui lòng nhập mô tả công việc!", Toast.LENGTH_SHORT).show();
            txt_JobDescription.requestFocus();
            return false;
        }
        if (txt_JobRequired.getText().toString().trim().isEmpty() || txt_JobRequired.getText().toString().trim() == "") {
            Toast.makeText(getActivity(), "Vui lòng nhập yêu cầu công việc!", Toast.LENGTH_SHORT).show();
            txt_JobRequired.requestFocus();
            return false;
        }
        if (txt_Salary.getTags().size() == 0) {
            Toast.makeText(getActivity(), "Vui lòng chọn mức lương!", Toast.LENGTH_SHORT).show();
            txt_Salary.requestFocus();
            return false;
        }
        return true;
    }

    private void addData() {
        try {
            WorkInfo wf = new WorkInfo();

            wf.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            wf.setCompanyName(txt_CompanyName.getText().toString());
            wf.setTitlePost(txt_TitlePost.getText().toString());
            wf.setViews(0);
            wf.setPostTime((new Date()).getTime());
            wf.setExpirationTime(cal.getTime().getTime());
            wf.setWorkPlace(txt_WorkPlace.getTags().toString().substring(1, txt_WorkPlace.getTags().toString().length() - 1));
            wf.setStatus(0);

            WorkDetail wd = new WorkDetail();
            wd.setWorkTypes(txt_WorkType.getTags().toString().substring(1, txt_WorkType.getTags().toString().length() - 1));
            wd.setCarrers(txt_Career.getTags().toString().substring(1, txt_Career.getTags().toString().length() - 1));
            wd.setLevel(txt_Level.getTags().toString().substring(1, txt_Level.getTags().toString().length() - 1));
            wd.setExperience(txt_Experience.getTags().toString().substring(1, txt_Experience.getTags().toString().length() - 1));
            wd.setTitle(txt_Title.getText().toString());
            try {
                wd.setNumber(Integer.parseInt(txt_Number.getText().toString()));
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Số lượng phải là số nguyên!", Toast.LENGTH_LONG).show();
                txt_Number.requestFocus();
            }
            wd.setJobDescription(txt_JobDescription.getText().toString());
            wd.setJobRequired(txt_JobRequired.getText().toString());
            wd.setWelfare(txt_Welfare.getText().toString());
            wd.setSalary(txt_Salary.getTags().toString().substring(1, txt_Salary.getTags().toString().length() - 1));

            wf.setWorkDetail(wd);


            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Node.WORKINFOS).push();
            Database.addDataWithKey(Node.WORKINFOS, ref.getKey(), wf);

            if (recruiter != null) {
                addTag(wf.getWorkPlace() + "," + wd.getCarrers() + "," + wd.getLevel() + "," + wd.getExperience());

                Notification n = new Notification();
                n.setNewWorkInfo(true);
                n.setTitle(wf.getTitlePost());
                n.setContent(recruiter.getCompanyName() + " đã đăng một thông tin tuyển dụng mới.");
                n.setSendTime(wf.getPostTime());
                n.setStatus(Notification.NOTIFY);
                n.setUserId(recruiter.getKey());
                n.setWorkInfoKey(ref.getKey());

                for (Follow fl : recruiter.getFollows().values()) {
                    Database.addData(Node.CANDIDATES + "/" + fl.getUserId() + "/notifications", n);
                }
            }
        } catch (Exception e) {

        }
    }

    private void addTag(String tag) {
        String[] arr1 = tag.split(",");
        for(String str : arr1)
            recruiter.addTags(str.trim());
        Database.updateData(Node.RECRUITERS, recruiter.getKey(), recruiter);
    }

    private void addEvent() {
        btn_Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                        "Please wait...", true);
                if (checkValidation()) {
                    addData();
                    dialog.dismiss();
                    NTDPostManagerFragment fragment = new NTDPostManagerFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
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
        Intent intent = new Intent(getActivity(), selectScreen);
        String data = tags.getTags().toString().substring(1, tags.getTags().toString().length() - 1);
        intent.putExtra(keyData, data);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RequestCode.SELECT_WORKTYPE:
                if (data != null) {
                    String message = data.getStringExtra("lstWorkType");
                    txt_WorkType.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_CAREER:
                if (data != null) {
                    String message = data.getStringExtra("lstCareer");
                    txt_Career.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_LEVEL:
                if (data != null) {
                    String message = data.getStringExtra("lstLevel");
                    txt_Level.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_EXPERIENCE:
                if (data != null) {
                    String message = data.getStringExtra("lstExperience");
                    txt_Experience.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_SALARY:
                if (data != null) {
                    String message = data.getStringExtra("lstSalary");
                    txt_Salary.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_WORKPLACE:
                if (data != null) {
                    String message = data.getStringExtra("lstWorkPlace");
                    txt_WorkPlace.setTags(message.split(","));
                }
                break;
        }
    }

    private void setIcon() {
        btn_AddWorkType.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        btn_AddCareer.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        btn_AddLevel.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        btn_AddExperience.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        btn_AddSalary.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        btn_AddWorkPlace.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
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
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                getActivity(),
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hết hạn");
        pic.show();
    }
}


