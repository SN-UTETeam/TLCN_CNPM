package com.spkt.nguyenducnguu.jobstore.Recruiter.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.spkt.nguyenducnguu.jobstore.Admin.activities.AdminChangeProfileActivity;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Models.Address;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.Roles;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.ResultRegisterActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NTDRegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Calendar cal = Calendar.getInstance();
    TextView txt_BirthDay, txt_Gender;
    TextView txt_Email, txt_Password, txt_ConfirmPassword, txt_FullName, txt_CompanyName,
            txt_Description, txt_Phone, txt_Website, txt_Address;
    Button btn_Register;
    ImageView imgv_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_register);

        addView();
        addEvent();
    }

    private void addView() {
        txt_BirthDay = (TextView) findViewById(R.id.txt_BirthDay);
        txt_Gender = (TextView) findViewById(R.id.txt_Gender);

        txt_Email = (TextView) findViewById(R.id.txt_Email);
        txt_Password = (TextView) findViewById(R.id.txt_Password);
        txt_ConfirmPassword = (TextView) findViewById(R.id.txt_ConfirmPassword);
        txt_FullName = (TextView) findViewById(R.id.txt_FullName);
        txt_CompanyName = (TextView) findViewById(R.id.txt_CompanyName);
        txt_Description = (TextView) findViewById(R.id.txt_Description);
        txt_Phone = (TextView) findViewById(R.id.txt_Phone);
        txt_Website = (TextView) findViewById(R.id.txt_Website);
        txt_Address = (TextView) findViewById(R.id.txt_Address);

        btn_Register = (Button) findViewById(R.id.btn_Register);
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
    }

    private void addEvent() {
        txt_BirthDay.setOnClickListener(new View.OnClickListener() {
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

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean ValidateInputData() {
        String email = txt_Email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern))
        {
            Toast.makeText(NTDRegisterActivity.this,"Địa chỉ email không hợp lệ!",Toast.LENGTH_SHORT).show();
            txt_Email.requestFocus();
            return false;
        }

        if(txt_Password.getText().length() < 6)
        {
            Toast.makeText(NTDRegisterActivity.this,"Mật khẩu phải có ít nhất 6 ký tự!",Toast.LENGTH_SHORT).show();
            txt_Password.requestFocus();
            return false;
        }

        if(!txt_Password.getText().toString().equals(txt_ConfirmPassword.getText().toString()))
        {
            Toast.makeText(NTDRegisterActivity.this,"Mật khẩu nhập lại không khớp!",Toast.LENGTH_SHORT).show();
            txt_ConfirmPassword.setText("");
            txt_ConfirmPassword.requestFocus();
            return false;
        }

        if(txt_FullName.getText().toString().trim().length() == 0)
        {
            Toast.makeText(NTDRegisterActivity.this,"Vui lòng nhập họ tên của bạn!",Toast.LENGTH_SHORT).show();
            txt_FullName.requestFocus();
            return false;
        }
        if(txt_BirthDay.getText().toString().trim().length() == 0)
        {
            Toast.makeText(NTDRegisterActivity.this,"Vui lòng chọn ngày sinh của bạn!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txt_Gender.getText().toString().trim().length() == 0)
        {
            Toast.makeText(NTDRegisterActivity.this,"Vui lòng chọn giới tính của bạn!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txt_CompanyName.getText().toString().trim().length() == 0)
        {
            Toast.makeText(NTDRegisterActivity.this,"Vui lòng nhập tên công ty!",Toast.LENGTH_SHORT).show();
            txt_CompanyName.requestFocus();
            return false;
        }
        if(txt_Address.getText().toString().trim().length() == 0)
        {
            Toast.makeText(NTDRegisterActivity.this,"Vui lòng nhập địa chỉ cụ thể của công ty!",Toast.LENGTH_SHORT).show();
            txt_Address.requestFocus();
            return false;
        }
        return true;
    }

    private void Register() {
        if(!ValidateInputData()) return;

        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Please wait...", true);

        final Recruiter r = new Recruiter();
        r.setEmail(txt_Email.getText().toString().trim());
        r.setFullName(txt_FullName.getText().toString().trim());
        r.setBirthDay(cal.getTime().getTime());
        r.setGender(txt_Gender.getText().toString() == "Nam"? 0 : 1);
        r.setCompanyName(txt_CompanyName.getText().toString().trim());
        r.setDescription(txt_Description.getText().toString());
        r.setPhone(txt_Phone.getText().toString());
        r.setWebsite(txt_Website.getText().toString());
        r.setAddress(Address.getAddressFromLocationName(txt_Address.getText().toString(), this));
        r.setCreateAt((new Date()).getTime());
        r.setStatus(1);

        mAuth.createUserWithEmailAndPassword(txt_Email.getText().toString().trim(), txt_Password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Thêm dữ liệu
                            Database.addDataWithKey(Node.RECRUITERS, mAuth.getCurrentUser().getUid(), r);
                            //Phân quyền
                            Roles role = new Roles(r.getEmail(), 0);
                            Database.addDataWithKey(Node.ROLES, mAuth.getCurrentUser().getUid(),role);
                            //Thông báo
                            Intent intent = new Intent(NTDRegisterActivity.this, ResultRegisterActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("title", "Congratulations");
                            bundle.putString("content", "Tài khoản của bạn đã được tạo thành công, bạn có thể đăng nhập ngay bây giờ." +
                                    " Cảm ơn bạn đã sử dụng Jobstore!");
                            bundle.putString("team", "SN UTE Team");
                            bundle.putBoolean("success", true);
                            intent.putExtra("bundle", bundle);
                            dialog.dismiss();
                            startActivity(intent);
                        } else {
                            //Thông báo
                            Intent intent = new Intent(NTDRegisterActivity.this, ResultRegisterActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("title", "Failed");
                            bundle.putString("content", "Đã có lỗi trong quá trình đăng ký, có thể do tài khoản này đã được đăng ký " +
                                    "trước đó. Bạn vui lòng kiểm tra lại, nếu quên mật khẩu bạn có thể lấy lại mật khẩu ở màn hình Login " +
                                    "và chọn Quên mật khẩu. Cảm ơn bạn đã sử dụng Jobstore!");
                            bundle.putString("team", "SN UTE Team");
                            bundle.putBoolean("success", false);
                            intent.putExtra("bundle", bundle);
                            dialog.dismiss();
                            startActivity(intent);
                        }
                    }
                });
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView
                txt_BirthDay.setText((dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        if(txt_BirthDay.getText().length() == 0)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            txt_BirthDay.setText(sdf.format(new Date()));
        }
        String s= txt_BirthDay.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                NTDRegisterActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày sinh");
        pic.show();
    }

    private void showChoiceGenderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NTDRegisterActivity.this);
        builder.setMessage("Giới tính của bạn là gì?")
                .setPositiveButton("Nam", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        txt_Gender.setText("Nam");
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Nữ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        txt_Gender.setText("Nữ");
                        dialog.dismiss();
                    }
                }).create().show();
    }
}
