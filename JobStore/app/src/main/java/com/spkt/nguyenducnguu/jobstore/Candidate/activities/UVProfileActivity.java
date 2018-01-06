package com.spkt.nguyenducnguu.jobstore.Candidate.activities;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.spkt.nguyenducnguu.jobstore.Models.Diploma;
import com.spkt.nguyenducnguu.jobstore.Models.WorkExp;
import com.spkt.nguyenducnguu.jobstore.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UVProfileActivity extends AppCompatActivity {
    TextView txt_Tags, txt_changeProfile, txt_UVName, txt_BirthDay,
            txt_Phone, txt_Email, txt_FacebookURL, txt_Gender, txt_Address;
    KenBurnsView img_CoverPhotoUV;
    ExpandableTextView txt_Description;
    CircleImageView img_AvatarUV;
    TextView txt_icon1, txt_icon2, txt_icon3, txt_icon4, txt_icon5, txt_icon6, txt_icon7, txt_icon8, txt_icon9,
            txt_icon10, txt_icon11, txt_icon12, txt_icon13, txt_icon14, txt_icon15, txt_iconCareer;
    TextView txt_Back;
    TextView txt_WorkPlace, txt_WorkType, txt_Career, txt_Level, txt_Experience, txt_Salary;
    ViewGroup vg_WorkExp, vg_Diploma;
    LinearLayout ln_WorkExps, ln_Diplomas;
    Button btn_DownloadCV;

    private String Key = "";
    private Candidate candidate = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_profile);

        Intent intent = getIntent();
        if(intent != null){
            if(intent.getStringExtra("Key") != null && !intent.getStringExtra("Key").isEmpty())
            {
                Key = intent.getStringExtra("Key");
            }
        }

        addView();
        addEvent();
        setIcon();
        loadData();
    }

    private boolean isOwner() {
        if(Key != "" && Key.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            return true;
        return false;
    }

    private void loadData(){
        if (Key != "") {
            Database.getData(Node.CANDIDATES + "/" + Key, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Candidate c = dataSnapshot.getValue(Candidate.class);
                    if (c == null) return;

                    candidate = c;

                    txt_UVName.setText(c.getFullName() == null ? "-- Chưa cập nhật --" : c.getFullName());
                    txt_Tags.setText(c.getCandidateDetail().getTag() == null ? "-- Chưa cập nhật --" : c.getCandidateDetail().getTag());
                    if (c.getGender() == 0)
                        txt_Gender.setText("Nữ");
                    else
                        txt_Gender.setText("Nam");

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    if(c.getBirthday() != null)
                        txt_BirthDay.setText(sdf.format(new Date(c.getBirthday())));
                    else txt_BirthDay.setText("-- Chưa cập nhật --");
                    txt_Phone.setText(c.getPhone() == null ? "-- Chưa cập nhật --" : c.getPhone());
                    txt_Email.setText(c.getEmail() == null ? "-- Chưa cập nhật --" : c.getEmail());
                    txt_FacebookURL.setText(c.getFacebookURL() == null ? "-- Chưa cập nhật --" : c.getFacebookURL());
                    if(c.getAddress() != null)
                        txt_Address.setText(c.getAddress().getAddressStr() == null ? "-- Chưa cập nhật --" : c.getAddress().getAddressStr());
                    else txt_Address.setText("-- Chưa cập nhật --");
                    txt_Description.setText(c.getDescription() == null ? "-- Chưa cập nhật --" : c.getDescription());
                    if(c.getCandidateDetail() != null) {
                        txt_WorkPlace.setText(c.getCandidateDetail().getWorkPlaces() == null ? "-- Chưa cập nhật --" : c.getCandidateDetail().getWorkPlaces());
                        txt_WorkType.setText(c.getCandidateDetail().getWorkTypes() == null ? "-- Chưa cập nhật --" : c.getCandidateDetail().getWorkTypes());
                        txt_Career.setText(c.getCandidateDetail().getCareers() == null ? "-- Chưa cập nhật --" : c.getCandidateDetail().getCareers());
                        txt_Level.setText(c.getCandidateDetail().getLevel() == null ? "-- Chưa cập nhật --" : c.getCandidateDetail().getLevel());
                        txt_Experience.setText(c.getCandidateDetail().getExperience() == null ? "-- Chưa cập nhật --" : c.getCandidateDetail().getExperience());
                        txt_Salary.setText(c.getCandidateDetail().getSalary() == null ? "-- Chưa cập nhật --" : c.getCandidateDetail().getSalary());
                    }

                    loadWorkExp();
                    loadDiploma();

                    if(c.getCandidateDetail().getCV() != null && !c.getCandidateDetail().getCV().isEmpty()
                            && !c.getCandidateDetail().getCV().equals(""))
                        btn_DownloadCV.setVisibility(View.VISIBLE);
                    else btn_DownloadCV.setVisibility(View.GONE);

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
        txt_Tags = (TextView) findViewById(R.id.txt_Tags);
        txt_Gender = (TextView) findViewById(R.id.txt_Gender);
        txt_BirthDay = (TextView) findViewById(R.id.txt_BirthDay);
        txt_Phone = (TextView) findViewById(R.id.txt_Phone);
        txt_Email = (TextView) findViewById(R.id.txt_Email);
        txt_FacebookURL = (TextView) findViewById(R.id.txt_FacebookURL);
        txt_Address = (TextView) findViewById(R.id.txt_Address);
        txt_Description = (ExpandableTextView) findViewById(R.id.txt_Description);

        txt_Back = (TextView) findViewById(R.id.txt_Back);
        txt_changeProfile = (TextView) findViewById(R.id.txt_changeProfile);
        txt_iconCareer = (TextView) findViewById(R.id.txt_iconCareer);

        txt_WorkPlace = (TextView) findViewById(R.id.txt_WorkPlace);
        txt_WorkType = (TextView) findViewById(R.id.txt_WorkType);
        txt_Career = (TextView) findViewById(R.id.txt_Career);
        txt_Level = (TextView) findViewById(R.id.txt_Level);
        txt_Experience = (TextView) findViewById(R.id.txt_Experience);
        txt_Salary = (TextView) findViewById(R.id.txt_Salary);

        vg_WorkExp = (ViewGroup) findViewById(R.id.ln_WorkExps);
        ln_WorkExps = (LinearLayout) findViewById(R.id.ln_WorkExps);

        vg_Diploma = (ViewGroup) findViewById(R.id.ln_Diplomas);
        ln_Diplomas = (LinearLayout) findViewById(R.id.ln_Diplomas);

        btn_DownloadCV = (Button) findViewById(R.id.btn_DownloadCV);
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

        if(isOwner()) {
            txt_changeProfile.setVisibility(View.VISIBLE);

            txt_changeProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(UVProfileActivity.this, UVChangeProfileActivity.class);
                    Bundle bndlanimation =
                            ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_slide_in_right, R.anim.anim_slide_out_right).toBundle();
                    intent.putExtra("Key", Key);
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
        else txt_changeProfile.setVisibility(View.GONE);

        btn_DownloadCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(candidate.getCandidateDetail().getCV());
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Open file with user selected app
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, getContentResolver().getType(uri));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UVProfileActivity.this, "Không thể tải file", Toast.LENGTH_SHORT).show();
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
        txt_icon1 = (TextView) findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) findViewById(R.id.txt_icon3);
        txt_icon4 = (TextView) findViewById(R.id.txt_icon4);
        txt_icon5 = (TextView) findViewById(R.id.txt_icon5);
        txt_icon6 = (TextView) findViewById(R.id.txt_icon6);
        txt_icon7 = (TextView) findViewById(R.id.txt_icon7);
        txt_icon8 = (TextView) findViewById(R.id.txt_icon8);
        txt_icon9 = (TextView) findViewById(R.id.txt_icon9);
        txt_icon10 = (TextView) findViewById(R.id.txt_icon10);
        txt_icon11 = (TextView) findViewById(R.id.txt_icon11);
        txt_icon12 = (TextView) findViewById(R.id.txt_icon12);
        txt_icon13 = (TextView) findViewById(R.id.txt_icon13);
        txt_icon14 = (TextView) findViewById(R.id.txt_icon14);
        txt_icon15 = (TextView) findViewById(R.id.txt_icon15);

        txt_Back.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_changeProfile.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_iconCareer.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon1.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon4.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon5.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon6.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon7.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon8.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon9.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon10.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon11.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon12.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon13.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon14.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        txt_icon15.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
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

                Database.updateData(Node.CANDIDATES, Key, candidate);
                dialog.dismiss();
            }
        });

    }

    private void loadWorkExp() {
        if(candidate == null) return;

        vg_WorkExp.removeAllViews();
        List<WorkExp> lstWorkExp = new ArrayList<>();
        lstWorkExp.addAll(candidate.getCandidateDetail().getWorkExps().values());

        LinearLayout linearLayout = (LinearLayout) ln_WorkExps.getParent();
        if(lstWorkExp.size() <= 0) linearLayout.setVisibility(View.GONE);
        else linearLayout.setVisibility(View.VISIBLE);

        for (int i = 0; i < lstWorkExp.size(); i++) {
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = vi.inflate(R.layout.list_item_workexp_layout, null);

            LinearLayout ln_Parent = (LinearLayout) view.findViewById(R.id.ln_Parent);
            LinearLayout ln_Remove = (LinearLayout) view.findViewById(R.id.ln_Remove);
            Button btn_YearNumber = (Button) view.findViewById(R.id.btn_YearNumber);
            TextView txt_Id = (TextView) view.findViewById(R.id.txt_Id);
            TextView txt_Begin = (TextView) view.findViewById(R.id.txt_Begin);
            TextView txt_Finish = (TextView) view.findViewById(R.id.txt_Finish);
            TextView txt_CompanyName = (TextView) view.findViewById(R.id.txt_CompanyName);
            TextView txt_Time = (TextView) view.findViewById(R.id.txt_Time);
            TextView txt_Title = (TextView) view.findViewById(R.id.txt_Title);

            ln_Remove.setVisibility(View.GONE);
            float YEAR = 365 * 24 * 60 * 60 * 1000;
            btn_YearNumber.setText((lstWorkExp.get(i).getFinish() - lstWorkExp.get(i).getBegin()) / YEAR + "");
            txt_Id.setText(lstWorkExp.get(i).getId() + "");
            txt_Begin.setText(lstWorkExp.get(i).getBegin() + "");
            txt_Finish.setText(lstWorkExp.get(i).getFinish() + "");
            txt_CompanyName.setText(lstWorkExp.get(i).getCompanyName());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            txt_Time.setText("Từ " + sdf.format(new Date(lstWorkExp.get(i).getBegin()))
                    + " đến " + sdf.format(new Date(lstWorkExp.get(i).getFinish())));
            txt_Title.setText(lstWorkExp.get(i).getTitle());

            vg_WorkExp.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    private void loadDiploma() {
        if(candidate == null) return;

        vg_Diploma.removeAllViews();
        List<Diploma> lstDiploma = new ArrayList<>();
        lstDiploma.addAll(candidate.getCandidateDetail().getDiplomas().values());

        LinearLayout linearLayout = (LinearLayout) ln_Diplomas.getParent();
        if(lstDiploma.size() <= 0) linearLayout.setVisibility(View.GONE);
        else linearLayout.setVisibility(View.VISIBLE);

        for (int i = 0; i < lstDiploma.size(); i++) {
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = vi.inflate(R.layout.list_item_diploma_layout, null);

            LinearLayout ln_Parent = (LinearLayout) view.findViewById(R.id.ln_Parent);
            LinearLayout ln_Remove = (LinearLayout) view.findViewById(R.id.ln_Remove);
            Button btn_Scores = (Button) view.findViewById(R.id.btn_Scores);
            TextView txt_Id = (TextView) view.findViewById(R.id.txt_Id);
            TextView txt_Name = (TextView) view.findViewById(R.id.txt_Name);
            TextView txt_IssuedBy = (TextView) view.findViewById(R.id.txt_IssuedBy);
            TextView txt_IssuedDate = (TextView) view.findViewById(R.id.txt_IssuedDate);
            TextView txt_Ranking = (TextView) view.findViewById(R.id.txt_Ranking);

            ln_Remove.setVisibility(View.GONE);
            txt_Id.setText(lstDiploma.get(i).getId() + "");
            btn_Scores.setText(lstDiploma.get(i).getScores() + "");
            txt_Name.setText(lstDiploma.get(i).getName());
            txt_IssuedBy.setText(lstDiploma.get(i).getIssuedBy());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            txt_IssuedDate.setText(sdf.format((new Date(lstDiploma.get(i).getIssuedDate())).getTime()));
            txt_Ranking.setText(lstDiploma.get(i).getRanking());

            vg_Diploma.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }
}
