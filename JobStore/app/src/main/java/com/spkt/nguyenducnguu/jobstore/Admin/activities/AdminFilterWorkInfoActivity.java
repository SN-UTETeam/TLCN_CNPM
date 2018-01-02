package com.spkt.nguyenducnguu.jobstore.Admin.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.Admin.fragments.ManageWorkInfoFragment;
import com.spkt.nguyenducnguu.jobstore.Const.RequestCode;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Models.AdminFilterWorkInfoSetting;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectCarrerActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectWorkPlaceActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectWorkTypeActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mabbas007.tagsedittext.TagsEditText;

public class AdminFilterWorkInfoActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgv_Back, imgv_ResetFrom, imgv_ResetTo;
    Button btn_Finish, btn_AddWorkPlace, btn_AddWorkType, btn_AddCareer;
    RadioButton rdb_All, rdb_Unexpired, rdb_Expired;
    TagsEditText txt_WorkPlace, txt_WorkType, txt_Career;
    TextView txt_From, txt_To;
    AdminFilterWorkInfoSetting mSetting = null;
    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_filter_work_info);

        mSetting = ManageWorkInfoFragment.mSettingSearch;
        addView();
        addEvent();
        loadData();
    }

    private void addView() {
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
        imgv_ResetFrom = (ImageView) findViewById(R.id.imgv_ResetFrom);
        imgv_ResetTo = (ImageView) findViewById(R.id.imgv_ResetTo);
        btn_Finish = (Button) findViewById(R.id.btn_Finish);
        btn_AddWorkPlace = (Button) findViewById(R.id.btn_AddWorkPlace);
        btn_AddWorkType = (Button) findViewById(R.id.btn_AddWorkType);
        btn_AddCareer = (Button) findViewById(R.id.btn_AddCareer);
        rdb_All = (RadioButton) findViewById(R.id.rdb_All);
        rdb_Unexpired = (RadioButton) findViewById(R.id.rdb_Unexpired);
        rdb_Expired = (RadioButton) findViewById(R.id.rdb_Expired);
        txt_WorkPlace = (TagsEditText) findViewById(R.id.txt_WorkPlace);
        txt_WorkType = (TagsEditText) findViewById(R.id.txt_WorkType);
        txt_Career = (TagsEditText) findViewById(R.id.txt_Career);
        txt_From = (TextView) findViewById(R.id.txt_From);
        txt_To = (TextView) findViewById(R.id.txt_To);

        //set icon
        btn_AddWorkPlace.setTypeface(FontManager.getTypeface(getBaseContext(), FontManager.FONTAWESOME));
        btn_AddWorkType.setTypeface(FontManager.getTypeface(getBaseContext(), FontManager.FONTAWESOME));
        btn_AddCareer.setTypeface(FontManager.getTypeface(getBaseContext(), FontManager.FONTAWESOME));
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(this);
        imgv_ResetFrom.setOnClickListener(this);
        imgv_ResetTo.setOnClickListener(this);
        btn_Finish.setOnClickListener(this);
        btn_AddWorkPlace.setOnClickListener(this);
        btn_AddWorkType.setOnClickListener(this);
        btn_AddCareer.setOnClickListener(this);
        txt_From.setOnClickListener(this);
        txt_To.setOnClickListener(this);
    }

    private void loadData() {
        if(mSetting == null) mSetting = new AdminFilterWorkInfoSetting();

        rdb_All.setChecked(mSetting.isAll());
        rdb_Unexpired.setChecked(mSetting.isUnexpired());
        rdb_Expired.setChecked(mSetting.isExpired());

        txt_WorkPlace.setTags(mSetting.getWorkPlacesArray());
        txt_WorkType.setTags(mSetting.getWorkTypesArray());
        txt_Career.setTags(mSetting.getCareersArray());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(mSetting.getFrom() != null)
            txt_From.setText(sdf.format(new Date(mSetting.getFrom())));
        if(mSetting.getTo() != null)
            txt_To.setText(sdf.format(new Date(mSetting.getTo())));
    }

    private void startActivitySelectForResult(Class selectScreen, TagsEditText tags, String keyData, int requestCode) {
        Intent intent = new Intent(this, selectScreen);
        String data = tags.getTags().toString().substring(1, tags.getTags().toString().length() - 1);
        intent.putExtra(keyData, data);
        startActivityForResult(intent, requestCode);
    }

    private void showDatePickerDialog(String title, final TextView txt) {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView
                txt.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        if (txt.getText().length() == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            txt.setText(sdf.format(new Date()));
        }
        String s = txt.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                this,
                callback, nam, thang, ngay);
        pic.setTitle(title);
        pic.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgv_Back:
                finish();
                break;
            case R.id.imgv_ResetFrom:
                txt_From.setText("");
                break;
            case R.id.imgv_ResetTo:
                txt_To.setText("");
                break;
            case R.id.btn_Finish:
                if(rdb_All.isChecked()) mSetting.setStatus(mSetting.ALL);
                else if(rdb_Unexpired.isChecked()) mSetting.setStatus(mSetting.UNEXPIRED);
                else if(rdb_Expired.isChecked()) mSetting.setStatus(mSetting.EXPIRED);

                mSetting.setWorkPlaces(txt_WorkPlace.getTags().toString().substring(1, txt_WorkPlace.getTags().toString().length() - 1));
                mSetting.setWorkTypes(txt_WorkType.getTags().toString().substring(1, txt_WorkType.getTags().toString().length() - 1));
                mSetting.setCareers(txt_Career.getTags().toString().substring(1, txt_Career.getTags().toString().length() - 1));

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    Date date = sdf.parse(txt_From.getText().toString());
                    date.setHours(0);
                    date.setMinutes(0);
                    date.setSeconds(0);
                    mSetting.setFrom(date.getTime());
                } catch (ParseException e) {
                    mSetting.setFrom(null);
                    e.printStackTrace();
                }

                try {
                    Date date = sdf.parse(txt_To.getText().toString());
                    date.setHours(0);
                    date.setMinutes(0);
                    date.setSeconds(0);
                    mSetting.setTo(date.getTime());
                } catch (ParseException e) {
                    mSetting.setTo(null);
                    e.printStackTrace();
                }

                ManageWorkInfoFragment.mSettingSearch = mSetting;
                finish();
                break;
            case R.id.btn_AddWorkPlace:
                startActivitySelectForResult(SelectWorkPlaceActivity.class, txt_WorkPlace,
                        "lstWorkPlaceSelected", RequestCode.SELECT_WORKPLACE);
                break;
            case R.id.btn_AddWorkType:
                startActivitySelectForResult(SelectWorkTypeActivity.class, txt_WorkType,
                        "lstWorkTypeSelected", RequestCode.SELECT_WORKTYPE);
                break;
            case R.id.btn_AddCareer:
                startActivitySelectForResult(SelectCarrerActivity.class, txt_Career,
                        "lstCareerSelected", RequestCode.SELECT_CAREER);
                break;
            case R.id.txt_From:
                showDatePickerDialog("Từ ngày", txt_From);
                break;
            case R.id.txt_To:
                showDatePickerDialog("Đến ngày", txt_To);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RequestCode.SELECT_WORKPLACE:
                if (data != null) {
                    String message = data.getStringExtra("lstWorkPlace");
                    txt_WorkPlace.setTags(message.split(","));
                }
                break;
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
        }
    }
}
