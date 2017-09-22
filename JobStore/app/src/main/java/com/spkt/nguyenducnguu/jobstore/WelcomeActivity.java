package com.spkt.nguyenducnguu.jobstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.spkt.nguyenducnguu.jobstore.ADMIN.AdminMainActivity;
import com.spkt.nguyenducnguu.jobstore.Models.Roles;
import com.spkt.nguyenducnguu.jobstore.NTD.NTDMainActivity;
import com.spkt.nguyenducnguu.jobstore.UV.UVMainActivity;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Context context = this;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    updateUI(currentUser);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    private void updateUI(FirebaseUser user)
    {
        //Đã xác thực tài khoản
        if(user != null)
        {
            //Kiểm tra quyền của tài khoản
            DatabaseReference rolesRef = FirebaseDatabase.getInstance().getReference("Roles");
            Query query = rolesRef.orderByChild("email").equalTo(user.getEmail());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Intent intent = null;
                        for (DataSnapshot mdata : dataSnapshot.getChildren()) {
                            Roles role = mdata.getValue(Roles.class);

                            if(role.getRole() == 0) { //NTD
                                intent = new Intent(WelcomeActivity.this, NTDMainActivity.class);
                                break;
                            }
                            else if(role.getRole() == 1) {//UV
                                intent = new Intent(WelcomeActivity.this, UVMainActivity.class);
                                break;
                            }
                            else { //Admin
                                intent = new Intent(WelcomeActivity.this, AdminMainActivity.class);
                                break;
                            }
                        }
                        finish();
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        else
        {
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
