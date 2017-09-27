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
import android.widget.EditText;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mabbas007.tagsedittext.TagsEditText;

public class NTDPostRecruitmentFragment extends Fragment {

    Calendar cal = Calendar.getInstance();
    Button btn_Complete;
    TagsEditText txt_WorkType;
    TagsEditText txt_Career;
    TextView txt_ExpirationTime;
    Button btn_AddWorkType, btn_AddCareer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ntd_post_recruitment_fragment, container, false);

        addView(rootView);
        addEvent();
        setIcon();
        return rootView;
    }
    private void addView(View rootView)
    {
        txt_ExpirationTime = (EditText) rootView.findViewById(R.id.txt_ExpirationTime);
        txt_WorkType = (TagsEditText) rootView.findViewById(R.id.txt_WorkType);
        txt_Career= (TagsEditText) rootView.findViewById(R.id.txt_Career);
        btn_AddWorkType = (Button) rootView.findViewById(R.id.btn_AddWorkType);
        btn_AddCareer = (Button) rootView.findViewById(R.id.btn_AddCareer);
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
                String data = "";
                for(int i = 0; i < txt_WorkType.getTags().size(); i++)
                {
                    data += txt_WorkType.getTags().get(i) + ",";
                }
                if(data.length() > 0) data = data.substring(0, data.length() - 1);
                intent.putExtra("lstWorkTypeSelected", data);
                startActivityForResult(intent, 1);
            }
        });
        btn_AddCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectCarrerActivity.class);
                startActivityForResult(intent,2);
            }
        });
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
    }
    private void setIcon(){
        btn_AddWorkType.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        btn_AddCareer.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
    }
}


