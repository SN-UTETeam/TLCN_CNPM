package com.spkt.nguyenducnguu.jobstore.Admin.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Const.RequestCode;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Admin;
import com.spkt.nguyenducnguu.jobstore.R;
import com.squareup.picasso.Picasso;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminProfileActivity extends AppCompatActivity {
    TextView txt_back, txt_changeProfile;
    TextView txt_icon1, txt_icon2, txt_icon3, txt_icon4;
    TextView txt_FullName, txt_Address, txt_Gender, txt_BirthDay, txt_Email, txt_Phone;
    CircleImageView img_Avatar;
    KenBurnsView img_CoverPhoto;
    Admin admin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        addView();
        addEvent();
        setIcon();
        loadData();
    }

    private void addView(){
        txt_back = (TextView) findViewById(R.id.txt_back);
        txt_changeProfile = (TextView) findViewById(R.id.txt_changeProfile);
        img_Avatar = (CircleImageView) findViewById(R.id.img_Avatar);
        img_CoverPhoto = (KenBurnsView) findViewById(R.id.img_CoverPhoto);
        txt_FullName = (TextView) findViewById(R.id.txt_FullName);
        txt_Address = (TextView) findViewById(R.id.txt_Address);
        txt_Gender = (TextView) findViewById(R.id.txt_Gender);
        txt_BirthDay = (TextView) findViewById(R.id.txt_BirthDay);
        txt_Email = (TextView) findViewById(R.id.txt_Email);
        txt_Phone = (TextView) findViewById(R.id.txt_Phone);
    }

    private void addEvent(){
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(admin == null) return;
                Intent intent = new Intent(AdminProfileActivity.this, AdminChangeProfileActivity.class);
                intent.putExtra("Key", admin.getKey());
                startActivity(intent);
            }
        });

        img_Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(admin == null) return;
                showChooseImage(RequestCode.PICK_AVATAR, "Select avatar");
            }
        });

        img_CoverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(admin == null) return;
                showChooseImage(RequestCode.PICK_COVERPHOTO, "Select cover photo");
            }
        });
    }

    private void loadData(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Database.getData(Node.ADMIN + "/" + user.getUid(), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                admin = dataSnapshot.getValue(Admin.class);
                if(admin == null) return;
                admin.setKey(dataSnapshot.getKey());

                txt_FullName.setText(admin.getFullName() == null ? "-- Chưa cập nhật --" : admin.getFullName());
                txt_Address.setText(admin.getAddress() == null ? "-- Chưa cập nhật --" : admin.getAddress().getAddressStr());
                txt_Gender.setText(admin.getGender() == 1 ? "Nam" : "Nữ");
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                if(admin.getBirthDay() != null)
                    txt_BirthDay.setText(sdf.format(new Date(admin.getBirthDay())));
                else txt_BirthDay.setText("-- Chưa cập nhật --");
                txt_Email.setText(admin.getEmail() == null ? "-- Chưa cập nhật --" : admin.getEmail());
                txt_Phone.setText(admin.getPhone() == null ? "-- Chưa cập nhật --" : admin.getPhone());

                if(admin.getAvatar() != null)
                    Picasso.with(getBaseContext()).load(admin.getAvatar()).into(img_Avatar);
                if(admin.getCoverPhoto() != null)
                    Picasso.with(getBaseContext()).load(admin.getCoverPhoto()).into(img_CoverPhoto);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    private void setIcon() {
        txt_icon1 = (TextView) findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) findViewById(R.id.txt_icon3);
        txt_icon4 = (TextView) findViewById(R.id.txt_icon4);

        txt_changeProfile.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_back.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon1.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon4.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
    }

    private void showChooseImage(int requestCode, String title) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, title);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) return;

        switch (requestCode) {
            case RequestCode.PICK_AVATAR:
                storageImage(RequestCode.PICK_AVATAR, data);
                break;
            case RequestCode.PICK_COVERPHOTO:
                storageImage(RequestCode.PICK_COVERPHOTO, data);
                break;
        }
    }

    private void storageImage(final int requestCode, Intent data) {
        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Please wait...", true);
        Uri selectedImage = data.getData();
        String[] arrString = selectedImage.toString().split(".");
        String ExtendImage = "";
        if (arrString.length > 0)
            ExtendImage = "." + arrString[arrString.length - 1];

        StorageReference ref = FirebaseStorage.getInstance().getReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + (requestCode == RequestCode.PICK_AVATAR ? "Avatar" : "CoverPhoto") + ExtendImage);

        UploadTask uploadTask = ref.putFile(selectedImage);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(AdminProfileActivity.this, "Không thể lưu hình ảnh!", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                if (requestCode == RequestCode.PICK_AVATAR)
                    admin.setAvatar(downloadUrl.toString());
                else if (requestCode == RequestCode.PICK_COVERPHOTO)
                    admin.setCoverPhoto(downloadUrl.toString());

                Picasso.with(getBaseContext()).load(downloadUrl.toString())
                        .into(requestCode == RequestCode.PICK_AVATAR ? img_Avatar : img_CoverPhoto);

                Database.updateData(Node.ADMIN, admin.getKey(), admin);
                dialog.dismiss();
            }
        });

    }
}
