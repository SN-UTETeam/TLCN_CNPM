package com.spkt.nguyenducnguu.jobstore.Admin.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spkt.nguyenducnguu.jobstore.Admin.fragments.ManageAccountFragment;
import com.spkt.nguyenducnguu.jobstore.Models.AdminFilterAccountSetting;
import com.spkt.nguyenducnguu.jobstore.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AdminFilterAccountActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    ImageView imgv_Back;
    CheckBox cb_All, cb_Active, cb_Blocked, cb_Pending;
    EditText txt_BeginDate, txt_FinishDate;
    LinearLayout ln_FilterOption;

    Calendar cal = Calendar.getInstance();
    AdminFilterAccountSetting mSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_filter_account);

        mSetting = ManageAccountFragment.mSetting;
        addView();
        addEvent();
        loadData();
    }

    private void addView() {
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
        cb_All = (CheckBox) findViewById(R.id.cb_All);
        cb_Active = (CheckBox) findViewById(R.id.cb_Active);
        cb_Blocked = (CheckBox) findViewById(R.id.cb_Blocked);
        cb_Pending = (CheckBox) findViewById(R.id.cb_Pending);
        txt_BeginDate = (EditText) findViewById(R.id.txt_BeginDate);
        txt_FinishDate = (EditText) findViewById(R.id.txt_FinishDate);
        ln_FilterOption = (LinearLayout) findViewById(R.id.ln_FilterOption);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_BeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(txt_BeginDate);
            }
        });

        txt_FinishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(txt_FinishDate);
            }
        });

        txt_BeginDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    mSetting.setBeginDate(sdf.parse(txt_BeginDate.getText().toString()).getTime());
                    Toast.makeText(AdminFilterAccountActivity.this, mSetting.getBeginDate() + "", Toast.LENGTH_LONG).show();
                } catch (ParseException e) {
                    mSetting.setBeginDate(null);
                    e.printStackTrace();
                }
            }
        });

        txt_FinishDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    mSetting.setFinishDate(sdf.parse(txt_FinishDate.getText().toString()).getTime());
                } catch (ParseException e) {
                    mSetting.setFinishDate(null);
                    e.printStackTrace();
                }
            }
        });

        cb_All.setOnCheckedChangeListener(this);
        cb_Active.setOnCheckedChangeListener(this);
        cb_Blocked.setOnCheckedChangeListener(this);
        cb_Pending.setOnCheckedChangeListener(this);
    }

    private void disableEnableControls(boolean enable, ViewGroup vg) {
        vg.setEnabled(enable);
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            child.setClickable(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }

    private void loadData() {
        if (mSetting.isAll()) {
            disableEnableControls(false, ln_FilterOption);
        } else {
            disableEnableControls(true, ln_FilterOption);
        }
        cb_All.setChecked(mSetting.isAll());
        cb_Active.setChecked(mSetting.isActive());
        cb_Blocked.setChecked(mSetting.isBlocked());
        cb_Pending.setChecked(mSetting.isPending());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            txt_BeginDate.setText(sdf.format(mSetting.getBeginDate()));
        } catch (Exception e) {
            txt_BeginDate.setText("");
        }
        try {
            txt_FinishDate.setText(sdf.format(mSetting.getFinishDate()));
        } catch (Exception e) {
            txt_FinishDate.setText("");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_All:
                mSetting.setAll(isChecked);
                if (isChecked) {
                    mSetting.setActive(true);
                    mSetting.setBlocked(true);
                    mSetting.setPending(true);
                    mSetting.setBeginDate(null);
                    mSetting.setFinishDate(null);
                }
                loadData();
                break;
            case R.id.cb_Active:
                mSetting.setActive(isChecked);
                break;
            case R.id.cb_Blocked:
                mSetting.setBlocked(isChecked);
                break;
            case R.id.cb_Pending:
                mSetting.setPending(isChecked);
                break;
        }
    }

    private void showDatePickerDialog(final TextView txt_Date) {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView
                txt_Date.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        if (txt_Date.getText().length() == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            txt_Date.setText(sdf.format(new Date()));
        }
        String s = txt_Date.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày");
        pic.show();
    }
}
