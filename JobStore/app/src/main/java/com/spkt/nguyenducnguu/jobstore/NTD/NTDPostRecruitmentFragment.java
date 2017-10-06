package com.spkt.nguyenducnguu.jobstore.NTD;

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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkDetail;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.SelectCarrerActivity;
import com.spkt.nguyenducnguu.jobstore.SelectExperienceActivity;
import com.spkt.nguyenducnguu.jobstore.SelectLevelActivity;
import com.spkt.nguyenducnguu.jobstore.SelectSalaryActivity;
import com.spkt.nguyenducnguu.jobstore.SelectWorkPlaceActivity;
import com.spkt.nguyenducnguu.jobstore.SelectWorkTypeActivity;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ntd_post_recruitment_fragment, container, false);

        addView(rootView);
        loadData();
        addEvent();
        setIcon();
        return rootView;
    }
    private void loadData()
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        txt_Email.setText(mAuth.getCurrentUser().getEmail());

        FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
        Query query = mdatabase.getReference("Recruiters").orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    txt_CompanyName.setText(mdata.getValue(Recruiter.class).getCompanyName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void addView(View rootView)
    {
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
        txt_Career= (TagsEditText) rootView.findViewById(R.id.txt_Career);
        txt_Level= (TagsEditText) rootView.findViewById(R.id.txt_Level);
        txt_Experience= (TagsEditText) rootView.findViewById(R.id.txt_Experience);
        txt_Salary= (TagsEditText) rootView.findViewById(R.id.txt_Salary);
        txt_WorkPlace= (TagsEditText) rootView.findViewById(R.id.txt_WorkPlace);

        btn_AddWorkType = (Button) rootView.findViewById(R.id.btn_AddWorkType);
        btn_AddCareer = (Button) rootView.findViewById(R.id.btn_AddCareer);
        btn_AddLevel = (Button) rootView.findViewById(R.id.btn_AddLevel);
        btn_AddExperience = (Button) rootView.findViewById(R.id.btn_AddExperience);
        btn_AddSalary = (Button) rootView.findViewById(R.id.btn_AddSalary);
        btn_AddWorkPlace = (Button) rootView.findViewById(R.id.btn_AddWorkPlace);

        btn_Complete = (Button) rootView.findViewById(R.id.btn_Complete);
    }
    private boolean checkValidation()
    {
        if(txt_TitlePost.getText().toString().trim().isEmpty() || txt_TitlePost.getText().toString().trim() == "")
        {
            Toast.makeText(getActivity(),"Vui lòng nhập tiêu đề!",Toast.LENGTH_SHORT).show();
            txt_TitlePost.requestFocus();
            return false;
        }
        if(txt_ExpirationTime.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getActivity(),"Vui lòng chọn ngày hết hạn!",Toast.LENGTH_SHORT).show();
            txt_ExpirationTime.requestFocus();
            return false;
        }
        if(txt_WorkPlace.getTags().size() == 0)
        {
            Toast.makeText(getActivity(),"Vui lòng chọn địa điểm làm việc!",Toast.LENGTH_SHORT).show();
            txt_WorkPlace.requestFocus();
            return false;
        }
        if(txt_WorkType.getTags().size() == 0)
        {
            Toast.makeText(getActivity(),"Vui lòng chọn loại hình công việc!",Toast.LENGTH_SHORT).show();
            txt_WorkType.requestFocus();
            return false;
        }
        if(txt_Career.getTags().size() == 0)
        {
            Toast.makeText(getActivity(),"Vui lòng chọn ngành nghề!",Toast.LENGTH_SHORT).show();
            txt_Career.requestFocus();
            return false;
        }
        if(txt_Level.getTags().size() == 0)
        {
            Toast.makeText(getActivity(),"Vui lòng chọn trình độ!",Toast.LENGTH_SHORT).show();
            txt_Level.requestFocus();
            return false;
        }
        if(txt_Experience.getTags().size() == 0)
        {
            Toast.makeText(getActivity(),"Vui lòng chọn kinh nghiệm!",Toast.LENGTH_SHORT).show();
            txt_Experience.requestFocus();
            return false;
        }
        if(txt_Title.getText().toString().trim().isEmpty() || txt_Title.getText().toString().trim() == "")
        {
            Toast.makeText(getActivity(),"Vui lòng nhập chức vụ!",Toast.LENGTH_SHORT).show();
            txt_Title.requestFocus();
            return false;
        }
        if(txt_Number.getText().toString().trim().isEmpty() || txt_Number.getText().toString().trim() == "")
        {
            Toast.makeText(getActivity(),"Vui lòng nhập số lượng cần tuyển!",Toast.LENGTH_SHORT).show();
            txt_Number.requestFocus();
            return false;
        }
        if(txt_JobDescription.getText().toString().trim().isEmpty() || txt_JobDescription.getText().toString().trim() == "")
        {
            Toast.makeText(getActivity(),"Vui lòng nhập mô tả công việc!",Toast.LENGTH_SHORT).show();
            txt_JobDescription.requestFocus();
            return false;
        }
        if(txt_JobRequired.getText().toString().trim().isEmpty() || txt_JobRequired.getText().toString().trim() == "")
        {
            Toast.makeText(getActivity(),"Vui lòng nhập yêu cầu công việc!",Toast.LENGTH_SHORT).show();
            txt_JobRequired.requestFocus();
            return false;
        }
        if(txt_Salary.getTags().size() == 0)
        {
            Toast.makeText(getActivity(),"Vui lòng chọn mức lương!",Toast.LENGTH_SHORT).show();
            txt_Salary.requestFocus();
            return false;
        }
        return true;
    }
    private boolean addData()
    {
        try {
            WorkInfo wf = new WorkInfo();
            wf.setEmail(txt_Email.getText().toString());
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
                return false;
            }
            wd.setJobDescription(txt_JobDescription.getText().toString());
            wd.setJobRequired(txt_JobRequired.getText().toString());
            wd.setWelfare(txt_Welfare.getText().toString());
            wd.setSalary(txt_Salary.getTags().toString().substring(1, txt_Salary.getTags().toString().length() - 1));

            wf.setWorkDetail(wd);

            FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
            mdatabase.getReference("WorkInfos").push().setValue(wf);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    private void addEvent()
    {
        btn_Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                        "Please wait...", true);
                if(checkValidation())
                {
                    if(addData())
                    {
                        dialog.dismiss();
                        NTDPostManagerFragment fragment = new NTDPostManagerFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.commit();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Đã xảy ra lỗi trong quá trình đăng bài, hãy thử lại sau!", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
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
                Intent intent = new Intent(getActivity(), SelectWorkTypeActivity.class);
                String data = txt_WorkType.getTags().toString().substring(1, txt_WorkType.getTags().toString().length() - 1);
                intent.putExtra("lstWorkTypeSelected", data);
                startActivityForResult(intent, 1);
            }
        });
        btn_AddCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectCarrerActivity.class);
                String data = txt_Career.getTags().toString().substring(1, txt_Career.getTags().toString().length() - 1);
                intent.putExtra("lstCareerSelected", data);
                startActivityForResult(intent,2);
            }
        });
        btn_AddLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectLevelActivity.class);
                String data = txt_Level.getTags().toString().substring(1, txt_Level.getTags().toString().length() - 1);
                intent.putExtra("lstLevelSelected", data);
                startActivityForResult(intent,3);
            }
        });
        btn_AddExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectExperienceActivity.class);
                String data = txt_Experience.getTags().toString().substring(1, txt_Experience.getTags().toString().length() - 1);
                intent.putExtra("lstExperienceSelected", data);
                startActivityForResult(intent,4);
            }
        });
        btn_AddSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectSalaryActivity.class);
                String data = txt_Salary.getTags().toString().substring(1, txt_Salary.getTags().toString().length() - 1);
                intent.putExtra("lstSalarySelected", data);
                startActivityForResult(intent,5);
            }
        });
        btn_AddWorkPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectWorkPlaceActivity.class);
                String data = txt_WorkPlace.getTags().toString().substring(1, txt_WorkPlace.getTags().toString().length() - 1);
                intent.putExtra("lstWorkPlaceSelected", data);
                startActivityForResult(intent,6);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(data != null) {
                String message = data.getStringExtra("lstWorkType");
                txt_WorkType.setTags(message.split(","));
            }
        }
        else if(requestCode == 2){
            if(data != null) {
                String message = data.getStringExtra("lstCareer");
                txt_Career.setTags(message.split(","));
            }
        }
        else if(requestCode == 3){
            if(data != null) {
                String message = data.getStringExtra("lstLevel");
                txt_Level.setTags(message.split(","));
            }
        }
        else if(requestCode == 4){
            if(data != null) {
                String message = data.getStringExtra("lstExperience");
                txt_Experience.setTags(message.split(","));
            }
        }
        else if(requestCode == 5){
            if(data != null) {
                String message = data.getStringExtra("lstSalary");
                txt_Salary.setTags(message.split(","));
            }
        }
        else if(requestCode == 6){
            if(data != null) {
                String message = data.getStringExtra("lstWorkPlace");
                txt_WorkPlace.setTags(message.split(","));
            }
        }
    }
    private void setIcon(){
        btn_AddWorkType.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        btn_AddCareer.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        btn_AddLevel.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        btn_AddExperience.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        btn_AddSalary.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        btn_AddWorkPlace.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
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


