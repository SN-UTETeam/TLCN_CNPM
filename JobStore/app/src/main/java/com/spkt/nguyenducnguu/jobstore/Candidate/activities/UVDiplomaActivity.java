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

public class UVDiplomaActivity extends AppCompatActivity {
    Calendar cal = Calendar.getInstance();
    public final static String ID = "Id";
    public final static String NAME = "Name";
    public final static String ISSUEDDATE = "IssuedDate";
    public final static String ISSUEDBY = "IssuedBy";
    public final static String SCORES = "Scores";
    public final static String RANKING = "Ranking";
    public final static String IS_UPDATE = "isUpdate";

    private Long Id = -1L;
    private boolean isUpdate = false;

    ImageView imgv_Back;
    Button btn_Finish;
    EditText txt_Name, txt_IssuedBy, txt_Scores, txt_Ranking;
    TextView txt_IssuedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_diploma);

        addView();
        addEvent();
        loadData();
    }

    private boolean ValidateInputData() {
        if (txt_Name.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập tên bằng!", Toast.LENGTH_SHORT).show();
            txt_Name.requestFocus();
            return false;
        }
        if (txt_IssuedBy.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập nơi cấp!", Toast.LENGTH_SHORT).show();
            txt_IssuedBy.requestFocus();
            return false;
        }
        if (txt_IssuedDate.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng chọn ngày cấp!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txt_Scores.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập điểm số của bạn!", Toast.LENGTH_SHORT).show();
            txt_Scores.requestFocus();
            return false;
        }
        if (txt_Ranking.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập xếp loại của bạn!", Toast.LENGTH_SHORT).show();
            txt_Ranking.requestFocus();
            return false;
        }
        return true;
    }

    private void addView() {
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
        btn_Finish = (Button) findViewById(R.id.btn_Finish);
        txt_Name = (EditText) findViewById(R.id.txt_Name);
        txt_IssuedBy = (EditText) findViewById(R.id.txt_IssuedBy);
        txt_Scores = (EditText) findViewById(R.id.txt_Scores);
        txt_Ranking = (EditText) findViewById(R.id.txt_Ranking);
        txt_IssuedDate = (TextView) findViewById(R.id.txt_IssuedDate);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_IssuedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        btn_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ValidateInputData()) return;

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Intent intent = new Intent();
                intent.putExtra(IS_UPDATE, isUpdate);
                intent.putExtra(ID, Id);
                intent.putExtra(NAME, txt_Name.getText().toString());
                intent.putExtra(ISSUEDBY, txt_IssuedBy.getText().toString());
                try {
                    intent.putExtra(ISSUEDDATE, sdf.parse(txt_IssuedDate.getText().toString()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                intent.putExtra(SCORES, Float.parseFloat(txt_Scores.getText().toString()));
                intent.putExtra(RANKING, txt_Ranking.getText().toString());
                setResult(RequestCode.ADD_DIPLOMA, intent);

                finish();
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
            if (!intent.getStringExtra(ISSUEDDATE).isEmpty()) {
                try {
                    txt_IssuedDate.setText(sdf.format(new Date(Long.parseLong(intent.getStringExtra(ISSUEDDATE)))));
                    String strArrtmpBegin[] = txt_IssuedDate.getText().toString().split("/");
                    cal.set(Integer.parseInt(strArrtmpBegin[2]), Integer.parseInt(strArrtmpBegin[1]), Integer.parseInt(strArrtmpBegin[0]));
                } catch (Exception ex) {

                }
            }
            if (!intent.getStringExtra(NAME).isEmpty()) {
                try {
                    txt_Name.setText(intent.getStringExtra(NAME));
                } catch (Exception ex) {

                }
            }
            if (!intent.getStringExtra(ISSUEDBY).isEmpty()) {
                try {
                    txt_IssuedBy.setText(intent.getStringExtra(ISSUEDBY));
                } catch (Exception ex) {

                }
            }
            if (!intent.getStringExtra(SCORES).isEmpty()) {
                try {
                    txt_Scores.setText(intent.getStringExtra(SCORES));
                } catch (Exception ex) {

                }
            }
            if (!intent.getStringExtra(RANKING).isEmpty()) {
                try {
                    txt_Ranking.setText(intent.getStringExtra(RANKING));
                } catch (Exception ex) {

                }
            }
            isUpdate = true;
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView
                txt_IssuedDate.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        if (txt_IssuedDate.getText().length() == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            txt_IssuedDate.setText(sdf.format(new Date()));
        }
        String s = txt_IssuedDate.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày cấp");
        pic.show();
    }
}
