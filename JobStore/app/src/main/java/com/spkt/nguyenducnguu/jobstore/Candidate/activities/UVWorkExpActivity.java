package com.spkt.nguyenducnguu.jobstore.Candidate.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spkt.nguyenducnguu.jobstore.Const.RequestCode;
import com.spkt.nguyenducnguu.jobstore.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UVWorkExpActivity extends AppCompatActivity {
    Calendar cal = Calendar.getInstance();
    public final static String ID = "Id";
    public final static String BEGIN = "Begin";
    public final static String FINISH = "Finish";
    public final static String COMPANYNAME = "CompanyName";
    public final static String TITLE = "Title";
    public final static String IS_UPDATE = "isUpdate";
    private Long Id = -1L;
    private boolean isUpdate = false;
    ImageView imgv_Back;
    Button btn_Finish;
    TextView txt_Begin, txt_Finish;
    EditText txt_CompanyName, txt_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_work_exp);

        addView();
        addEvent();
        loadData();
    }

    private boolean ValidateInputData() {
        if (txt_CompanyName.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập tên công ty!", Toast.LENGTH_SHORT).show();
            txt_CompanyName.requestFocus();
            return false;
        }
        if (txt_Title.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập chức vụ!", Toast.LENGTH_SHORT).show();
            txt_Title.requestFocus();
            return false;
        }
        if (txt_Begin.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng chọn ngày bắt đầu!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txt_Finish.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng chọn ngày kết thúc!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addView() {
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
        btn_Finish = (Button) findViewById(R.id.btn_Finish);
        txt_CompanyName = (EditText) findViewById(R.id.txt_CompanyName);
        txt_Title = (EditText) findViewById(R.id.txt_Title);
        txt_Begin = (TextView) findViewById(R.id.txt_Begin);
        txt_Finish = (TextView) findViewById(R.id.txt_Finish);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ValidateInputData()) return;

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Intent intent = new Intent();
                intent.putExtra(IS_UPDATE, isUpdate);
                intent.putExtra(ID, Id);
                try {
                    intent.putExtra(BEGIN, sdf.parse(txt_Begin.getText().toString()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    intent.putExtra(FINISH, sdf.parse(txt_Finish.getText().toString()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                intent.putExtra(COMPANYNAME, txt_CompanyName.getText().toString());
                intent.putExtra(TITLE, txt_Title.getText().toString());
                setResult(RequestCode.ADD_WORKEXP, intent);

                finish();
            }
        });
        txt_Begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(txt_Begin);
            }
        });
        txt_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(txt_Finish);
            }
        });
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent != null) {
            try {
                Id = Long.parseLong(intent.getStringExtra(ID));
            } catch (Exception ex) {
                Id = -1L;
            }
        }
        if (Id != -1L) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (!intent.getStringExtra(BEGIN).isEmpty()) {
                try {
                    txt_Begin.setText(sdf.format(new Date(Long.parseLong(intent.getStringExtra(BEGIN)))));
                    String strArrtmpBegin[] = txt_Begin.getText().toString().split("/");
                    cal.set(Integer.parseInt(strArrtmpBegin[2]), Integer.parseInt(strArrtmpBegin[1]), Integer.parseInt(strArrtmpBegin[0]));
                } catch (Exception ex) {

                }
            }
            if (!intent.getStringExtra(FINISH).isEmpty()) {
                try {
                    txt_Finish.setText(sdf.format(new Date(Long.parseLong(intent.getStringExtra(FINISH)))));
                    String strArrtmpFinish[] = txt_Finish.getText().toString().split("/");
                    cal.set(Integer.parseInt(strArrtmpFinish[2]), Integer.parseInt(strArrtmpFinish[1]), Integer.parseInt(strArrtmpFinish[0]));
                } catch (Exception ex) {

                }
            }
            if (!intent.getStringExtra(COMPANYNAME).isEmpty()) {
                try {
                    txt_CompanyName.setText(intent.getStringExtra(COMPANYNAME));
                } catch (Exception ex) {

                }
            }
            if (!intent.getStringExtra(TITLE).isEmpty()) {
                try {
                    txt_Title.setText(intent.getStringExtra(TITLE));
                } catch (Exception ex) {

                }
            }
            isUpdate = true;
        }
    }

    private void showDatePickerDialog(final TextView textView) {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView
                textView.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        if (textView.getText().length() == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            textView.setText(sdf.format(new Date()));
        }
        String s = textView.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn tháng và năm");
        pic.show();
    }
}
