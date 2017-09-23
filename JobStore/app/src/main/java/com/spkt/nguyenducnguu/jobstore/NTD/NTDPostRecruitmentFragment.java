package com.spkt.nguyenducnguu.jobstore.NTD;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NTDPostRecruitmentFragment extends Fragment {

    Calendar cal = Calendar.getInstance();
    Button btnDangTai;
    TextView edtExpirationTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ntd_post_recruitment_fragment, container, false);


        edtExpirationTime = (EditText) rootView.findViewById(R.id.edt_ExpirationTime);
         addEvent();
        /*btn = (Button) rootView.findViewById(R.id.buttonNext);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext() , NTDPostRecruitmentActivity.class);
                startActivity(myIntent);
            }
        });*/
        return rootView;
    }
    private void addEvent()
    {
        edtExpirationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }
    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView
                edtExpirationTime.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        if (edtExpirationTime.getText().length() == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            edtExpirationTime.setText(sdf.format(new Date()));
        }
        String s = edtExpirationTime.getText() + "";
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


