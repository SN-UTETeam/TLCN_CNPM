package com.spkt.nguyenducnguu.jobstore.Recruiter.activities;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Const.RequestCode;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class NTDProfileActivity extends AppCompatActivity {
    TextView txt_back, txt_changeProfile, txt_CompanyName, txt_Address, txt_FullName,
            txt_Email, txt_Phone, txt_Website, txt_NumberFollow;
    ExpandableTextView txt_Description;
    KenBurnsView img_CoverPhoto;
    CircleImageView img_Avatar;

    TextView txt_icon1, txt_icon2, txt_icon3, txt_icon4, txt_icon5;
    private String Key = "";
    private Recruiter recruiter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_profile);

        addView();

        addEvent();
        setIcon();
        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (!intent.getStringExtra("Key").isEmpty()) {
                Key = intent.getStringExtra("Key");
            }
        }
        if (Key != "") {
            Database.getData(Node.RECRUITERS + "/" + Key, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Recruiter r = dataSnapshot.getValue(Recruiter.class);
                    if (r == null) return;

                    recruiter = r;

                    txt_CompanyName.setText(r.getCompanyName() == null ? "-- Chưa cập nhật --" : r.getCompanyName());
                    if(r.getAddress() != null)
                        txt_Address.setText(r.getAddress().getAddressStr() == null ? "-- Chưa cập nhật --" : r.getAddress().getAddressStr());
                    else txt_Address.setText("-- Chưa cập nhật --");
                    txt_NumberFollow.setText(r.getFollows() == null ? "0" : (r.getFollows().size() + ""));
                    txt_FullName.setText(r.getFullName() == null ? "-- Chưa cập nhật --" : r.getFullName());
                    txt_Email.setText(r.getEmail() == null ? "-- Chưa cập nhật --" : r.getEmail());
                    txt_Phone.setText(r.getPhone() == null ? "-- Chưa cập nhật --" : r.getPhone());
                    txt_Website.setText(r.getWebsite() == null ? "-- Chưa cập nhật --" : r.getWebsite());
                    txt_Description.setText(r.getDescription() == null ? "-- Chưa cập nhật --" : r.getDescription());

                    if (r.getAvatar() != null)
                        Picasso.with(getBaseContext()).load(r.getAvatar()).into(img_Avatar);
                    if (r.getCoverPhoto() != null)
                        Picasso.with(getBaseContext()).load(r.getCoverPhoto()).into(img_CoverPhoto);
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }
    }

    private void addView() {
        img_CoverPhoto = (KenBurnsView) findViewById(R.id.img_CoverPhoto);
        img_Avatar = (CircleImageView) findViewById(R.id.img_Avatar);
        txt_back = (TextView) findViewById(R.id.txt_back);
        txt_changeProfile = (TextView) findViewById(R.id.txt_changeProfile);
        txt_CompanyName = (TextView) findViewById(R.id.txt_CompanyName);
        txt_Address = (TextView) findViewById(R.id.txt_Address);
        txt_FullName = (TextView) findViewById(R.id.txt_FullName);
        txt_Email = (TextView) findViewById(R.id.txt_Email);
        txt_Phone = (TextView) findViewById(R.id.txt_Phone);
        txt_Website = (TextView) findViewById(R.id.txt_Website);
        txt_Description = (ExpandableTextView) findViewById(R.id.txt_Description);
        txt_NumberFollow = (TextView) findViewById(R.id.txt_NumberFollow);
        txt_icon1 = (TextView) findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) findViewById(R.id.txt_icon3);
        txt_icon4 = (TextView) findViewById(R.id.txt_icon4);
        txt_icon5 = (TextView) findViewById(R.id.txt_icon5);
    }

    private void addEvent() {
        img_CoverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseImage(RequestCode.PICK_COVERPHOTO, "Select cover photo");
            }
        });
        img_Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseImage(RequestCode.PICK_AVATAR, "Select avatar");
            }
        });
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NTDProfileActivity.this.overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_out_right);
                finish();
            }
        });
        txt_changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NTDProfileActivity.this, NTDChangeProfileActivity.class);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_slide_in_right, R.anim.anim_slide_out_right).toBundle();
                intent.putExtra("Key", Key);
                startActivity(intent, bndlanimation);
            }
        });
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
                Toast.makeText(NTDProfileActivity.this, "Không thể lưu hình ảnh!", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                if (requestCode == RequestCode.PICK_AVATAR)
                    recruiter.setAvatar(downloadUrl.toString());
                else if (requestCode == RequestCode.PICK_COVERPHOTO)
                    recruiter.setCoverPhoto(downloadUrl.toString());

                Picasso.with(getBaseContext()).load(downloadUrl.toString())
                        .into(requestCode == RequestCode.PICK_AVATAR ? img_Avatar : img_CoverPhoto);

                Database.updateData(Node.RECRUITERS, Key, recruiter);
                dialog.dismiss();
            }
        });

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

    private void setIcon() {
        txt_changeProfile.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_back.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon1.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon4.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon5.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
    }
}
