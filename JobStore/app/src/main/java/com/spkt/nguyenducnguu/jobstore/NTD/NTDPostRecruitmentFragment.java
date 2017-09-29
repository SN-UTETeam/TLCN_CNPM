package com.spkt.nguyenducnguu.jobstore.NTD;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.SelectCarrerActivity;
import com.spkt.nguyenducnguu.jobstore.SelectExperienceActivity;
import com.spkt.nguyenducnguu.jobstore.SelectLevelActivity;
import com.spkt.nguyenducnguu.jobstore.SelectSalaryActivity;
import com.spkt.nguyenducnguu.jobstore.SelectWorkTypeActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mabbas007.tagsedittext.TagsEditText;

public class NTDPostRecruitmentFragment extends Fragment {

    Calendar cal = Calendar.getInstance();
    Button btn_Complete;
    TagsEditText txt_WorkType, txt_Career, txt_Level, txt_Experience, txt_Salary;
    TextView txt_ExpirationTime, txt_Email;
    Button btn_AddWorkType, btn_AddCareer, btn_AddLevel, btn_AddExperience, btn_AddSalary;

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
    }
    private void addView(View rootView)
    {
        txt_Email = (TextView) rootView.findViewById(R.id.txt_Email);
        txt_ExpirationTime = (TextView) rootView.findViewById(R.id.txt_ExpirationTime);

        txt_WorkType = (TagsEditText) rootView.findViewById(R.id.txt_WorkType);
        txt_Career= (TagsEditText) rootView.findViewById(R.id.txt_Career);
        txt_Level= (TagsEditText) rootView.findViewById(R.id.txt_Level);
        txt_Experience= (TagsEditText) rootView.findViewById(R.id.txt_Experience);
        txt_Salary= (TagsEditText) rootView.findViewById(R.id.txt_Salary);

        btn_AddWorkType = (Button) rootView.findViewById(R.id.btn_AddWorkType);
        btn_AddCareer = (Button) rootView.findViewById(R.id.btn_AddCareer);
        btn_AddLevel = (Button) rootView.findViewById(R.id.btn_AddLevel);
        btn_AddExperience = (Button) rootView.findViewById(R.id.btn_AddExperience);
        btn_AddSalary = (Button) rootView.findViewById(R.id.btn_AddSalary);
    }
    private void addEvent()
    {
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
    }
    private void setIcon(){
        btn_AddWorkType.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        btn_AddCareer.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        btn_AddLevel.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        btn_AddExperience.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        btn_AddSalary.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
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


