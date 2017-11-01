package com.spkt.nguyenducnguu.jobstore.UV.activities;

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
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sontr on 10/25/17.
 */

public class UVProfileActivity extends AppCompatActivity {
    TextView txt_Career, txt_changeProfile, txt_UVName, txt_BirthDay,
            txt_Phone, txt_Email, txt_FacebookURL, txt_Gender;
    KenBurnsView img_CoverPhotoUV;
    ExpandableTextView txt_Description;
    CircleImageView img_AvatarUV;
    TextView txt_icon1, txt_icon2, txt_icon3, txt_icon4, txt_icon5, txt_icon6, txt_iconCareer;
    TextView txt_Back;
    private String Email = "";
    private Candidate candidate = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_profile);

        addView();
        addEvent();
        setIcon();
        loadData();
    }
    private void loadData(){
        Intent intent = getIntent();
        if(intent != null){
            if(!intent.getStringExtra("Email").isEmpty())
            {
                Email = intent.getStringExtra("Email");
            }
        }
        if (Email != "") {
            Database.getData(Node.CANDIDATES + "/" + Email, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Candidate c = dataSnapshot.getValue(Candidate.class);
                    if (c == null) return;

                    candidate = c;

                    txt_UVName.setText(c.getFullName());
                    txt_Career.setText(c.getCandidateDetail().getCareers());
                    if (c.getGender() == 0)
                        txt_Gender.setText("Nữ");
                    else
                        txt_Gender.setText("Nam");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    txt_BirthDay.setText(sdf.format(new Date(c.getBirthday())));
                    txt_Phone.setText(c.getPhone());
                    txt_Email.setText(c.getEmail());
                    txt_FacebookURL.setText(c.getFacebookURL());
                    txt_Description.setText(c.getDescription());

                    if (c.getAvatar() != null)
                        Picasso.with(getBaseContext()).load(c.getAvatar()).into(img_AvatarUV);
                    if (c.getCoverPhoto() != null)
                        Picasso.with(getBaseContext()).load(c.getCoverPhoto()).into(img_CoverPhotoUV);
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }
    }
    private void addView() {
        img_CoverPhotoUV = (KenBurnsView) findViewById(R.id.img_CoverPhotoUV);
        img_AvatarUV = (CircleImageView) findViewById(R.id.img_AvatarUV);
        txt_UVName = (TextView) findViewById(R.id.txt_UVName);
        txt_Career = (TextView) findViewById(R.id.txt_Career);
        txt_Gender = (TextView) findViewById(R.id.txt_Gender);
        txt_BirthDay = (TextView) findViewById(R.id.txt_BirthDay);
        txt_Phone = (TextView) findViewById(R.id.txt_Phone);
        txt_Email = (TextView) findViewById(R.id.txt_Email);
        txt_FacebookURL = (TextView) findViewById(R.id.txt_FacebookURL);
        txt_Description = (ExpandableTextView) findViewById(R.id.txt_Description);

        txt_Back = (TextView) findViewById(R.id.txt_Back);
        txt_changeProfile = (TextView) findViewById(R.id.txt_changeProfile);
        txt_iconCareer = (TextView) findViewById(R.id.txt_iconCareer);

        txt_icon1 = (TextView) findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) findViewById(R.id.txt_icon3);
        txt_icon4 = (TextView) findViewById(R.id.txt_icon4);
        txt_icon5 = (TextView) findViewById(R.id.txt_icon5);
        txt_icon6 = (TextView) findViewById(R.id.txt_icon6);
    }
    private void addEvent(){
        txt_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(UVProfileActivity.this, "CLCKED", Toast.LENGTH_SHORT).show();
                UVProfileActivity.this.overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_out_right);
                finish();
            }
        });
        txt_changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UVProfileActivity.this, UVChangeProfileActivity.class);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_slide_in_right, R.anim.anim_slide_out_right).toBundle();
                intent.putExtra("Email", Email);
                startActivity(intent, bndlanimation);
            }
        });
        img_CoverPhotoUV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseImage(RequestCode.PICK_COVERPHOTO, "Select cover photo");
            }
        });
        img_AvatarUV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseImage(RequestCode.PICK_AVATAR, "Select avatar");
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
        txt_Back.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_changeProfile.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_iconCareer.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon1.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon4.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon5.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon6.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));

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
                Toast.makeText(UVProfileActivity.this, "Không thể lưu hình ảnh!", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                if (requestCode == RequestCode.PICK_AVATAR)
                    candidate.setAvatar(downloadUrl.toString());
                else if (requestCode == RequestCode.PICK_COVERPHOTO)
                    candidate.setCoverPhoto(downloadUrl.toString());

                Picasso.with(getBaseContext()).load(downloadUrl.toString())
                        .into(requestCode == RequestCode.PICK_AVATAR ? img_AvatarUV : img_CoverPhotoUV);

                Database.updateData(Node.CANDIDATES, Email, candidate);
                dialog.dismiss();
            }
        });

    }
}
