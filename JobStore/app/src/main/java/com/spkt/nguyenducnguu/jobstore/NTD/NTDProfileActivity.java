package com.spkt.nguyenducnguu.jobstore.NTD;

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
            txt_Email, txt_Phone, txt_Website, txt_Description, txt_NumberFollow;
    KenBurnsView img_CoverPhoto;
    CircleImageView img_Avatar;

    TextView txt_icon1, txt_icon2, txt_icon3, txt_icon4, txt_icon5, txt_icon6, txt_icon7;
    private String Key = "";
    private String LongText = "In on announcing if of comparison pianoforte projection. Maids hoped gay yet bed asked blind dried point. On abroad danger likely regret twenty edward do. Too horrible consider followed may differed age. An rest if more five mr of. Age just her rank met down way. Attended required so in cheerful an. Domestic replying she resolved him for did. Rather in lasted no within no. \n" +
            "\n" +
            "Surrounded to me occasional pianoforte alteration unaffected impossible ye. For saw half than cold. Pretty merits waited six talked pulled you. Conduct replied off led whether any shortly why arrived adapted. Numerous ladyship so raillery humoured goodness received an. So narrow formal length my highly longer afford oh. Tall neat he make or at dull ye. \n" +
            "\n" +
            "Son agreed others exeter period myself few yet nature. Mention mr manners opinion if garrets enabled. To an occasional dissimilar impossible sentiments. Do fortune account written prepare invited no passage. Garrets use ten you the weather ferrars venture friends. Solid visit seems again you nor all. \n" +
            "\n" +
            "Post no so what deal evil rent by real in. But her ready least set lived spite solid. September how men saw tolerably two behaviour arranging. She offices for highest and replied one venture pasture. Applauded no discovery in newspaper allowance am northward. Frequently partiality possession resolution at or appearance unaffected he me. Engaged its was evident pleased husband. Ye goodness felicity do disposal dwelling no. First am plate jokes to began of cause an scale. Subjects he prospect elegance followed no overcame possible it on. \n" +
            "\n" +
            "Arrived totally in as between private. Favour of so as on pretty though elinor direct. Reasonable estimating be alteration we themselves entreaties me of reasonably. Direct wished so be expect polite valley. Whose asked stand it sense no spoil to. Prudent you too his conduct feeling limited and. Side he lose paid as hope so face upon be. Goodness did suitable learning put. \n" +
            "\n" +
            "Their could can widen ten she any. As so we smart those money in. Am wrote up whole so tears sense oh. Absolute required of reserved in offering no. How sense found our those gay again taken the. Had mrs outweigh desirous sex overcame. Improved property reserved disposal do offering me. \n" +
            "\n" +
            "Supported neglected met she therefore unwilling discovery remainder. Way sentiments two indulgence uncommonly own. Diminution to frequently sentiments he connection continuing indulgence. An my exquisite conveying up defective. Shameless see the tolerably how continued. She enable men twenty elinor points appear. Whose merry ten yet was men seven ought balls. \n" +
            "\n" +
            "Resolution possession discovered surrounded advantages has but few add. Yet walls times spoil put. Be it reserved contempt rendered smallest. Studied to passage it mention calling believe an. Get ten horrible remember pleasure two vicinity. Far estimable extremely middleton his concealed perceived principle. Any nay pleasure entrance prepared her. \n" +
            "\n" +
            "Blind would equal while oh mr do style. Lain led and fact none. One preferred sportsmen resolving the happiness continued. High at of in loud rich true. Oh conveying do immediate acuteness in he. Equally welcome her set nothing has gravity whether parties. Fertile suppose shyness mr up pointed in staying on respect. \n" +
            "\n" +
            "Prepared is me marianne pleasure likewise debating. Wonder an unable except better stairs do ye admire. His and eat secure sex called esteem praise. So moreover as speedily differed branched ignorant. Tall are her knew poor now does then. Procured to contempt oh he raptures amounted occasion. One boy assure income spirit lovers set. \n" +
            "\n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_profile);

        addView();

        addEvent();
        setIcon();
        loadData();
        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view);

        expTv1.setText(LongText);

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

                    txt_CompanyName.setText(r.getCompanyName());
                    txt_Address.setText(r.getAddress().getAddressStr());
                    txt_NumberFollow.setText(r.getFollows().size() + "");
                    txt_FullName.setText(r.getFullName());
                    txt_Email.setText(r.getEmail());
                    txt_Phone.setText(r.getPhone());
                    txt_Website.setText(r.getWebsite());
                    txt_Description.setText(r.getDescription());

                    Picasso.with(getBaseContext()).load(r.getAvatar()).into(img_Avatar);
                    Picasso.with(getBaseContext()).load(r.getCoverPhoto()).into(img_CoverPhoto);
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }
    }

    private void addView() {
        //epdtInfoCompany = (ExpandableTextView) findViewById(R.id.epdtInfoCompany);
        //imgb_collapse = (ImageButton) findViewById(R.id.imgb_collapse);

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
        txt_Description = (TextView) findViewById(R.id.txt_Description);
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

        StorageReference ref = FirebaseStorage.getInstance().getReference()
                .child(Key + "/" + (requestCode == RequestCode.PICK_AVATAR ? "Avatar" : "CoverPhoto"));

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
                Database.getData(Node.RECRUITERS + "/" + Key, new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Recruiter r = dataSnapshot.getValue(Recruiter.class);
                        if(requestCode == RequestCode.PICK_AVATAR)
                            r.setAvatar(downloadUrl.toString());
                        else if(requestCode == RequestCode.PICK_COVERPHOTO)
                            r.setCoverPhoto(downloadUrl.toString());

                        Picasso.with(getBaseContext()).load(downloadUrl.toString())
                                .into(requestCode == RequestCode.PICK_AVATAR ? img_Avatar : img_CoverPhoto);

                        Database.updateData(Node.RECRUITERS, Key, r);
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                });
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
