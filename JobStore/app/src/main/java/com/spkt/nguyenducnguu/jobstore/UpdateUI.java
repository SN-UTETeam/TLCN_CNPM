package com.spkt.nguyenducnguu.jobstore;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spkt.nguyenducnguu.jobstore.Admin.activities.AdminMainActivity;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Roles;
import com.spkt.nguyenducnguu.jobstore.Recruiter.activities.NTDMainActivity;
import com.spkt.nguyenducnguu.jobstore.Candidate.activities.UVMainActivity;

import java.util.Date;

public class UpdateUI {

    public UpdateUI() {
    }

    public void updateUI(final Activity context, final FirebaseUser user) {
        //Đã xác thực tài khoản
        if (user != null) {
            //Kiểm tra quyền của tài khoản
            Database.getData(Node.ROLES + "/" + user.getUid(), new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Intent intent = null;
                        DatabaseReference ref = null;
                        Roles role = dataSnapshot.getValue(Roles.class);
                        if (role.getRole() == Roles.RECRUITER) { //NTD
                            intent = new Intent(context, NTDMainActivity.class);
                            ref = FirebaseDatabase.getInstance().getReference(Node.RECRUITERS + "/" + user.getUid());
                        } else if (role.getRole() == Roles.CANDIDATE) {//UV
                            intent = new Intent(context, UVMainActivity.class);
                            ref = FirebaseDatabase.getInstance().getReference(Node.CANDIDATES + "/" + user.getUid());
                        } else { //Admin
                            intent = new Intent(context, AdminMainActivity.class);
                            ref = FirebaseDatabase.getInstance().getReference(Node.ADMIN + "/" + user.getUid());
                        }
                        ref.child("lastLogin").setValue((new Date()).getTime());
                        context.finish();
                        context.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
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
            context.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            context.startActivity(intent);
        }
    }
}
