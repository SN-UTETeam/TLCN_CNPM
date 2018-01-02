package com.spkt.nguyenducnguu.jobstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText txt_Email;
    private Button btn_ResetPassword, btn_Back;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        auth = FirebaseAuth.getInstance();
        addView();
        addEvent();
    }

    private void addView() {
        txt_Email = (EditText) findViewById(R.id.txt_Email);
        btn_ResetPassword = (Button) findViewById(R.id.btn_ResetPassword);
        btn_Back = (Button) findViewById(R.id.btn_Back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void addEvent() {
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_Email.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Nhập email mà bạn đã đăng ký!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    showNotificationDialog("Chúng tôi đã gửi cho bạn hướng dẫn để đặt lại mật khẩu, vui lòng kiểm tra email của bạn!", LoginActivity.class);
                                } else {
                                    showNotificationDialog("Email vừa nhập không đúng hoặc chưa đăng ký, vui lòng kiểm tra lại!", null);
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

    private void showNotificationDialog(String Message, final Class activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
        builder.setMessage(Message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        if(activity != null) {
                            Intent intent;
                            intent = new Intent(ResetPasswordActivity.this, activity);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        dialog.dismiss();
                    }
                }).create().show();
    }
}
