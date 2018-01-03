package com.spkt.nguyenducnguu.jobstore.Candidate.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.spkt.nguyenducnguu.jobstore.Const.RequestCode;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Models.SearchWorkInfoSetting;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectCarrerActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectExperienceActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectLevelActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectSalaryActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectWorkPlaceActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectWorkTypeActivity;
import com.spkt.nguyenducnguu.jobstore.Candidate.fragments.UVSearchWorkInfoFragment;

import mabbas007.tagsedittext.TagsEditText;

public class UVFilterSearchActivity extends AppCompatActivity {

    Button btn_Finish;
    TagsEditText txt_WorkType, txt_Career, txt_Level, txt_Experience, txt_Salary, txt_WorkPlace;
    Button btn_AddWorkType, btn_AddCareer, btn_AddLevel, btn_AddExperience, btn_AddSalary, btn_AddWorkPlace;
    ImageView imgv_Back;
    private SearchWorkInfoSetting mSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_filter_search);

        addView();
        addEvent();
        setIcon();
        loadData();
    }

    private void loadData() {
        mSetting = UVSearchWorkInfoFragment.mSettingSearch;

        txt_WorkType.setTags(mSetting.getWorkTypes().toString().substring(1, mSetting.getWorkTypes().toString().length() - 1).split(", "));
        txt_WorkPlace.setTags(mSetting.getWorkPlaces().toString().substring(1, mSetting.getWorkPlaces().toString().length() - 1).split(", "));
        txt_Career.setTags(mSetting.getCareers().toString().substring(1, mSetting.getCareers().toString().length() - 1).split(", "));
        txt_Salary.setTags(mSetting.getSalary());
        txt_Experience.setTags(mSetting.getExperience());
        txt_Level.setTags(mSetting.getLevel());
    }

    private void addView() {
        txt_WorkType = (TagsEditText) findViewById(R.id.txt_WorkType);
        txt_Career = (TagsEditText) findViewById(R.id.txt_Career);
        txt_Level = (TagsEditText) findViewById(R.id.txt_Level);
        txt_Experience = (TagsEditText) findViewById(R.id.txt_Experience);
        txt_Salary = (TagsEditText) findViewById(R.id.txt_Salary);
        txt_WorkPlace = (TagsEditText) findViewById(R.id.txt_WorkPlace);

        btn_AddWorkType = (Button) findViewById(R.id.btn_AddWorkType);
        btn_AddCareer = (Button) findViewById(R.id.btn_AddCareer);
        btn_AddLevel = (Button) findViewById(R.id.btn_AddLevel);
        btn_AddExperience = (Button) findViewById(R.id.btn_AddExperience);
        btn_AddSalary = (Button) findViewById(R.id.btn_AddSalary);
        btn_AddWorkPlace = (Button) findViewById(R.id.btn_AddWorkPlace);

        btn_Finish = (Button) findViewById(R.id.btn_Finish);
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
    }

    private void addEvent() {
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetting.setWorkPlaces(txt_WorkPlace.getTags().toString().substring(1, txt_WorkPlace.getTags().toString().length() - 1));
                mSetting.setWorkTypes(txt_WorkType.getTags().toString().substring(1, txt_WorkType.getTags().toString().length() - 1));
                mSetting.setCareers(txt_Career.getTags().toString().substring(1, txt_Career.getTags().toString().length() - 1));
                mSetting.setLevel(txt_Level.getTags().toString().substring(1, txt_Level.getTags().toString().length() - 1));
                mSetting.setExperience(txt_Experience.getTags().toString().substring(1, txt_Experience.getTags().toString().length() - 1));
                mSetting.setSalary(txt_Salary.getTags().toString().substring(1, txt_Salary.getTags().toString().length() - 1));

                UVSearchWorkInfoFragment.mSettingSearch = mSetting;
                finish();
            }
        });

        btn_AddWorkType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectWorkTypeActivity.class, txt_WorkType, "lstWorkTypeSelected", RequestCode.SELECT_WORKTYPE);
            }
        });
        btn_AddCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectCarrerActivity.class, txt_Career, "lstCareerSelected", RequestCode.SELECT_CAREER);
            }
        });
        btn_AddLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectLevelActivity.class, txt_Level, "lstLevelSelected", RequestCode.SELECT_LEVEL);
            }
        });
        btn_AddExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectExperienceActivity.class, txt_Experience, "lstExperienceSelected", RequestCode.SELECT_EXPERIENCE);
            }
        });
        btn_AddSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectSalaryActivity.class, txt_Salary, "lstSalarySelected", RequestCode.SELECT_SALARY);
            }
        });
        btn_AddWorkPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectWorkPlaceActivity.class, txt_WorkPlace, "lstWorkPlaceSelected", RequestCode.SELECT_WORKPLACE);
            }
        });
    }

    private void startActivitySelectForResult(Class selectScreen, TagsEditText tags, String keyData, int requestCode) {
        Intent intent = new Intent(this, selectScreen);
        String data = tags.getTags().toString().substring(1, tags.getTags().toString().length() - 1);
        intent.putExtra(keyData, data);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RequestCode.SELECT_WORKTYPE:
                if (data != null) {
                    String message = data.getStringExtra("lstWorkType");
                    txt_WorkType.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_CAREER:
                if (data != null) {
                    String message = data.getStringExtra("lstCareer");
                    txt_Career.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_LEVEL:
                if (data != null) {
                    String message = data.getStringExtra("lstLevel");
                    txt_Level.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_EXPERIENCE:
                if (data != null) {
                    String message = data.getStringExtra("lstExperience");
                    txt_Experience.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_SALARY:
                if (data != null) {
                    String message = data.getStringExtra("lstSalary");
                    txt_Salary.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_WORKPLACE:
                if (data != null) {
                    String message = data.getStringExtra("lstWorkPlace");
                    txt_WorkPlace.setTags(message.split(","));
                }
                break;
        }
    }

    private void setIcon() {
        btn_AddWorkType.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddCareer.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddLevel.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddExperience.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddSalary.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddWorkPlace.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
    }
}
