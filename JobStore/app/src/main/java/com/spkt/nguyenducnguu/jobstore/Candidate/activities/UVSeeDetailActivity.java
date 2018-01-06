package com.spkt.nguyenducnguu.jobstore.Candidate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Adaper.ViewPagerInFragmentAdapter;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Follow;
import com.spkt.nguyenducnguu.jobstore.Models.Parameter;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Candidate.fragments.RecruiterInfoFragment;
import com.spkt.nguyenducnguu.jobstore.Candidate.fragments.RecruiterWorkInfoFragment;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UVSeeDetailActivity extends AppCompatActivity {
    ImageView imgv_CoverPhoto;
    CircleImageView imgv_Avatar;
    Toolbar toolbar;
    TextView txt_NumberPost, txt_NumberFollow, txt_NumberApply, txt_Follow;
    TabLayout tabLayout;
    ViewPager view_Pager;
    private String Key = "";
    private Recruiter recruiter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_see_detail);

        Intent intent = getIntent();
        if(intent != null){
            if(intent.getStringExtra("Key") != null && !intent.getStringExtra("Key").isEmpty())
            {
                Key = intent.getStringExtra("Key");
            }
        }

        addView();
        setupViewPager();
        tabLayout.setupWithViewPager(view_Pager);
        addEvent();
        loadData();
    }

    private void addView(){
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        view_Pager = (ViewPager) findViewById(R.id.view_Pager);

        imgv_CoverPhoto = (ImageView) findViewById(R.id.imgv_CoverPhoto);
        imgv_Avatar = (CircleImageView) findViewById(R.id.imgv_Avatar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_NumberPost = (TextView) findViewById(R.id.txt_NumberPost);
        txt_NumberFollow = (TextView) findViewById(R.id.txt_NumberFollow);
        txt_NumberApply = (TextView) findViewById(R.id.txt_NumberApply);
        txt_Follow = (TextView) findViewById(R.id.txt_Follow);
    }

    private void addEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //Hàm tab được chọn
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_Pager.setCurrentItem(tab.getPosition());
            }

            //Hàm tab không được chọn
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                view_Pager.setCurrentItem(tab.getPosition());
            }

            //Hàm reselected tab
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                view_Pager.setCurrentItem(tab.getPosition());
            }
        });

        txt_Follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recruiter == null) return;

                if(txt_Follow.getText().equals("Follow me"))
                {
                    Toast.makeText(UVSeeDetailActivity.this, "You've follow", Toast.LENGTH_SHORT).show();
                    Follow p = new Follow(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    recruiter.getFollows().put(FirebaseAuth.getInstance().getCurrentUser().getUid(), p);
                    txt_Follow.setText("Unfollow");
                }
                else if(txt_Follow.getText().equals("Unfollow"))
                {
                    recruiter.getFollows().remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    txt_Follow.setText("Follow me");
                }
                txt_NumberFollow.setText(recruiter.getFollows().size() + "");
                Database.updateData(Node.RECRUITERS, recruiter.getKey(), recruiter);
            }
        });
    }

    private void loadData() {
        if(Key.isEmpty() || Key.trim() == "") return;

        Database.getData(Node.RECRUITERS + "/" + Key, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Recruiter r = dataSnapshot.getValue(Recruiter.class);
                if(r == null) return;
                recruiter = r;

                toolbar.setTitle(r.getCompanyName() == null ? "-- Chưa cập nhật --" : r.getCompanyName());
                if(r.getFollows() != null)
                    txt_NumberFollow.setText(r.getFollows().size() + "");
                else txt_NumberFollow.setText("0");

                if(r.checkFollow(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    txt_Follow.setText("Unfollow");
                else txt_Follow.setText("Follow me");

                if(r.getCoverPhoto() != null)
                    Picasso.with(getBaseContext()).load(r.getCoverPhoto()).into(imgv_CoverPhoto);
                if(r.getAvatar() != null)
                    Picasso.with(getBaseContext()).load(r.getAvatar()).into(imgv_Avatar);

                Database.getData(Node.WORKINFOS, new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        int numberPost = 0, numberApply = 0;
                        for(DataSnapshot mdata : dataSnapshot.getChildren())
                        {
                            WorkInfo w = mdata.getValue(WorkInfo.class);
                            if(w == null) continue;
                            numberPost++;
                            numberApply += w.getApplies().size();
                        }
                        txt_NumberPost.setText(numberPost + "");
                        txt_NumberApply.setText(numberApply + "");
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                }, new Parameter("userId", r.getKey()));
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    private void setupViewPager() {
        /*
        Tạo 1 lớp adapter để chứa 2 fragment
         */
        Bundle bundle = new Bundle();
        bundle.putString("Key", Key);

        RecruiterInfoFragment fragmentRecruiterInfo = new RecruiterInfoFragment();
        fragmentRecruiterInfo.setArguments(bundle);

        RecruiterWorkInfoFragment fragmentRecruiterWorkInfo = new RecruiterWorkInfoFragment();
        fragmentRecruiterWorkInfo.setArguments(bundle);

        ViewPagerInFragmentAdapter adapter = new ViewPagerInFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(fragmentRecruiterInfo, "Thông tin");
        adapter.addFragment(fragmentRecruiterWorkInfo, "Công việc");
        view_Pager.setAdapter(adapter);
    }
}
