package com.spkt.nguyenducnguu.jobstore;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spkt.nguyenducnguu.jobstore.NTD.NTDRegisterActivity;
import com.spkt.nguyenducnguu.jobstore.UV.UVRegisterActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txt_Email, txt_Password;
    Button btn_Login, btn_UngVien, btn_NhaTuyenDung;
    TextView tv_ForgotPassword, tv_Register;

    private Dialog dialog;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UpdateUI.updateUI(this, mAuth.getCurrentUser());

        addView();
        addEvent();

    }
    public void addView()
    {
        txt_Email = (EditText) findViewById(R.id.txt_Email);
        txt_Password = (EditText) findViewById(R.id.txt_Password);

        btn_Login = (Button) findViewById(R.id.btn_Login);

        tv_ForgotPassword = (TextView) findViewById(R.id.tv_ForgotPassword);
        tv_Register = (TextView) findViewById(R.id.tv_Register);
    }
    public void addEvent()
    {
        btn_Login.setOnClickListener(this);
        tv_ForgotPassword.setOnClickListener(this);
        tv_Register.setOnClickListener(this);
    }
    private boolean ValidateInputData()
    {
        if(txt_Email.getText().toString().trim().length() == 0)
        {
            Toast.makeText(LoginActivity.this,"Vui lòng nhập email!",Toast.LENGTH_SHORT).show();
            txt_Email.requestFocus();
            return false;
        }

        String email = txt_Email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern))
        {
            Toast.makeText(LoginActivity.this,"Địa chỉ email không hợp lệ!",Toast.LENGTH_SHORT).show();
            txt_Email.requestFocus();
            return false;
        }

        if(txt_Password.getText().length() == 0)
        {
            Toast.makeText(LoginActivity.this,"Vui lòng nhập mật khẩu!",Toast.LENGTH_SHORT).show();
            txt_Password.requestFocus();
            return false;
        }
        return true;
    }
    private void Login()
    {
        if(!ValidateInputData()) return;

        String email = txt_Email.getText().toString();
        String password = txt_Password.getText().toString();

        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Please wait...", true);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = mAuth.getCurrentUser();
                            UpdateUI.updateUI(LoginActivity.this, user);
                            dialog.dismiss();
                        } else {
                            // If sign in fails
                            Toast.makeText(LoginActivity.this, "     Authentication failed!!!",
                                    Toast.LENGTH_LONG).show();
                            UpdateUI.updateUI(LoginActivity.this, null);
                            dialog.dismiss();
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btn_Login:
                Login();
                break;
            case R.id.btn_UngVien:
                dialog.dismiss();
                intent = new Intent(this, UVRegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_NhaTuyenDung:
                dialog.dismiss();
                intent = new Intent(this, NTDRegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_ForgotPassword:

                break;
            case R.id.tv_Register:
                // khởi tạo dialog
                dialog = new Dialog(LoginActivity.this);
                // xét layout cho dialog
                dialog.setContentView(R.layout.dialog_before_register);
                // xét tiêu đề cho dialog
                dialog.setTitle("Bạn là ai?");
                // khai báo control trong dialog để bắt sự kiện
                btn_UngVien = (Button) dialog.findViewById(R.id.btn_UngVien);
                btn_NhaTuyenDung = (Button) dialog.findViewById(R.id.btn_NhaTuyenDung);
                // bắt sự kiện
                btn_UngVien.setOnClickListener(this);
                btn_NhaTuyenDung.setOnClickListener(this);
                // hiển thị dialog
                dialog.show();
                break;
        }
    }
}
