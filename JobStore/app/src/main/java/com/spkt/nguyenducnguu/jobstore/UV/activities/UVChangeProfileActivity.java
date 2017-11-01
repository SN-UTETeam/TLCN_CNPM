package com.spkt.nguyenducnguu.jobstore.UV.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Address;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.Models.CandidateDetail;
import com.spkt.nguyenducnguu.jobstore.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UVChangeProfileActivity extends AppCompatActivity {
    Button btn_Finish;
    TextView txt_Back;
    TextView txt_OldPassword, txt_Password, txt_ConfirmPassword;
    TextView txt_FullName, txt_Birthday, txt_Gender, txt_Career;
    TextView txt_Phone, txt_FacebookURL, txt_Address;
    Calendar cal = Calendar.getInstance();
    TextView txt_Description;

    private String Email = "";
    private Candidate candidate = null;
    CandidateDetail candidateDetail = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_change_profile);
        addView();
        addEvent();
        setIcon();
        loadData();
    }
    private void addView() {
        btn_Finish = (Button) findViewById(R.id.btn_Finish);
        txt_Back = (TextView) findViewById(R.id.txt_back);
        txt_OldPassword = (TextView)findViewById(R.id.txt_OldPassword);
        txt_Password = (TextView) findViewById(R.id.txt_Password);
        txt_ConfirmPassword = (TextView)findViewById(R.id.txt_ConfirmPassword);
        txt_FullName =(TextView)findViewById(R.id.txt_FullName);
        txt_Birthday =(TextView) findViewById(R.id.txt_BirthDay);
        txt_Gender = (TextView)findViewById(R.id.txt_Gender);
        txt_Description = (TextView)findViewById(R.id.txt_Description);
        txt_Career = (TextView) findViewById(R.id.txt_Career);
        txt_Phone =(TextView)findViewById(R.id.txt_Phone);
        txt_FacebookURL=(TextView)findViewById(R.id.txt_FacebookURL);
        txt_Address=(TextView)findViewById(R.id.txt_Address);
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (!intent.getStringExtra("Email").isEmpty()) {
                Email = intent.getStringExtra("Email");
            }
        }
        if (Email != "") {
            Database.getData(Node.CANDIDATES + "/" + Email, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    candidate = dataSnapshot.getValue(Candidate.class);
                    if (candidate == null) {
                        btn_Finish.setEnabled(false);
                        return;
                    }

                    txt_FullName.setText(candidate.getFullName());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    txt_Birthday.setText(sdf.format(new Date(candidate.getBirthday())));
                    txt_Career.setText(candidate.getCandidateDetail().getCareers());
                    txt_Gender.setText(candidate.getGender() == 0 ? "Nữ" : "Nam");
                    txt_Description.setText(candidate.getDescription());
                    txt_Phone.setText(candidate.getPhone());
                    txt_FacebookURL.setText(candidate.getFacebookURL());
                    txt_Address.setText(candidate.getAddress().getAddressStr());
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }
    }

    private void addEvent() {
        txt_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UVChangeProfileActivity.this.overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_out_right);
                finish();
            }
        });
        txt_Birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        txt_Gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoiceGenderDialog();
            }
        });
        btn_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

    }
    private void update() {
        //Phần update thông tin
        if (!ValidateInputData()) return;
        final ProgressDialog dialogUpdateInformation = ProgressDialog.show(this, "Update information",
                "Please wait...", true);
        dialogUpdateInformation.show();
        if (candidate != null) {
            candidate.setFullName(txt_FullName.getText().toString().trim());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                candidate.setBirthday(sdf.parse(txt_Birthday.getText().toString()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            candidate.setGender(txt_Gender.getText().toString() == "Nam" ? 0 : 1);
            candidate.getCandidateDetail().setCareers(txt_Career.getText().toString());
            candidate.setDescription(txt_Description.getText().toString());
            candidate.setPhone(txt_Phone.getText().toString());
            candidate.setFacebookURL(txt_FacebookURL.getText().toString());
            candidate.setAddress(Address.getAddressFromLocationName(txt_Address.getText().toString(), UVChangeProfileActivity.this));

            Database.updateData(Node.CANDIDATES, Email, candidate);

            dialogUpdateInformation.dismiss();
            Toast.makeText(UVChangeProfileActivity.this, "Thông tin đã được cập nhật!", Toast.LENGTH_SHORT).show();
        } else {
            dialogUpdateInformation.dismiss();
            Toast.makeText(UVChangeProfileActivity.this, "Không thể cập nhật thông tin!", Toast.LENGTH_SHORT).show();
        }

        //Phần đổi mật khẩu
        if (txt_OldPassword.getText().toString().isEmpty() || txt_OldPassword.getText().toString() == "")
            return;
        final ProgressDialog dialogChangePassword = ProgressDialog.show(this, "Change password",
                "Please wait...", true);
        dialogChangePassword.show();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), txt_OldPassword.getText().toString());
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(txt_Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    dialogChangePassword.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(UVChangeProfileActivity.this, "Mật khẩu đã được thay đổi!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(UVChangeProfileActivity.this, "Không thể đổi mật khẩu!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            dialogChangePassword.dismiss();
                            Toast.makeText(UVChangeProfileActivity.this, "Mật khẩu cũ không chính xác!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean ValidateInputData() {

        if (!txt_OldPassword.getText().toString().isEmpty() && txt_OldPassword.getText().toString() != "") {
            if (txt_OldPassword.getText().length() < 6) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
                txt_OldPassword.requestFocus();
                return false;
            }

            if (txt_Password.getText().length() < 6) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
                txt_Password.requestFocus();
                return false;
            }

            if (!txt_Password.getText().toString().equals(txt_ConfirmPassword.getText().toString())) {
                Toast.makeText(this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                txt_ConfirmPassword.setText("");
                txt_ConfirmPassword.requestFocus();
                return false;
            }
        }

        if (txt_FullName.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập họ tên của bạn!", Toast.LENGTH_SHORT).show();
            txt_FullName.requestFocus();
            return false;
        }
        if (txt_Birthday.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng chọn ngày sinh của bạn!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txt_Gender.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng chọn giới tính của bạn!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txt_Career.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập ngành nghề của bạn!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txt_Phone.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại của bạn!", Toast.LENGTH_SHORT).show();
            txt_Phone.requestFocus();
            return false;
        }
        if (txt_Address.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ cụ thể của công ty!", Toast.LENGTH_SHORT).show();
            txt_Address.requestFocus();
            return false;
        }
        return true;
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView
                txt_Birthday.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        if (txt_Birthday.getText().length() == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            txt_Birthday.setText(sdf.format(new Date()));
        }
        String s = txt_Birthday.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                UVChangeProfileActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày sinh");
        pic.show();
    }

    private void showChoiceGenderDialog() {
        // khởi tạo dialog
        final Dialog dialog = new Dialog(UVChangeProfileActivity.this);
        // xét layout cho dialog
        dialog.setContentView(R.layout.dialog_choice_gender);
        // xét tiêu đề cho dialog
        dialog.setTitle("Chọn giới tính của bạn");
        // khai báo control trong dialog để bắt sự kiện
        Button btn_Male = (Button) dialog.findViewById(R.id.btn_Male);
        Button btn_Female = (Button) dialog.findViewById(R.id.btn_Female);
        // bắt sự kiện
        btn_Male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_Gender.setText("Nam");
                dialog.dismiss();
            }
        });
        btn_Female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_Gender.setText("Nữ");
                dialog.dismiss();
            }
        });
        // hiển thị dialog
        dialog.show();
    }

    private void setIcon() {
        txt_Back.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
    }
}
