package com.spkt.nguyenducnguu.jobstore;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.ADMIN.activities.AdminMainActivity;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Roles;
import com.spkt.nguyenducnguu.jobstore.NTD.activities.NTDMainActivity;
import com.spkt.nguyenducnguu.jobstore.Service.ListenNewWorkInfoService;
import com.spkt.nguyenducnguu.jobstore.UV.activities.UVMainActivity;

public class UpdateUI {

    public UpdateUI() {
    }

    public void updateUI(final Activity context, FirebaseUser user) {
        //Đã xác thực tài khoản
        if (user != null) {
            //Kiểm tra quyền của tài khoản
            Database.getData(Node.ROLES + "/" + user.getUid(), new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Intent intent = null;
                        Roles role = dataSnapshot.getValue(Roles.class);
                        if (role.getRole() == Roles.RECRUITER) { //NTD
                            intent = new Intent(context, NTDMainActivity.class);
                        } else if (role.getRole() == Roles.CANDIDATE) {//UV
                            intent = new Intent(context, UVMainActivity.class);
                        } else { //Admin
                            intent = new Intent(context, AdminMainActivity.class);
                        }
                        context.finish();
                        context.startActivity(intent);
                    }
                }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Toast.makeText(context, "Authentication role failed.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Intent intent = new Intent(context, LoginActivity.class);
            context.finish();
            context.startActivity(intent);
        }
    }
}
