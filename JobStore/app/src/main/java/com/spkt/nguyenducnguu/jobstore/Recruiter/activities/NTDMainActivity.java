package com.spkt.nguyenducnguu.jobstore.Recruiter.activities;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Const.Status;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.LoginActivity;
import com.spkt.nguyenducnguu.jobstore.Models.Block;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Recruiter.fragments.NTDMainFragment;
import com.spkt.nguyenducnguu.jobstore.Recruiter.fragments.NTDNotificationFragment;
import com.spkt.nguyenducnguu.jobstore.Recruiter.fragments.NTDPostManagerFragment;
import com.spkt.nguyenducnguu.jobstore.Recruiter.fragments.NTDPostRecruitmentFragment;
import com.spkt.nguyenducnguu.jobstore.Recruiter.fragments.NTDSearchFilterFragment;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Service.ListenNewApplyService;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class NTDMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    RelativeLayout rl_headerNav;
    CircleImageView img_Avatar;
    TextView txt_CompanyName, txt_Email;
    ImageView img_CoverPhoto;
    Toolbar toolbar = null;
    View contentView;
    Intent service;
    Recruiter recruiter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_main);

        //Register Service
        service = new Intent(this, ListenNewApplyService.class);
        startService(service);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.activity_ntd_nav_header_main, null);

        //Thiet lap view trong layout
        addView();
        addEvent();
        //Thiet lap fragment ban dau
        setFragment();

        //Thiet lap toolBar
        setSupportActionBar(toolbar);

        //Thiet lap drawer trong navigation khi dong, mo
        setNavigationOpenClose();

        navigationView.addHeaderView(contentView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Database.getData(Node.RECRUITERS + "/" + user.getUid(), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Recruiter r = dataSnapshot.getValue(Recruiter.class);

                if (r == null) return;
                r.setKey(dataSnapshot.getKey());
                recruiter = r;

                txt_CompanyName.setText(r.getCompanyName() == null ? "-- Chưa cập nhật --" : r.getCompanyName());
                txt_Email.setText(r.getEmail() == null ? "-- Chưa cập nhật --" : r.getEmail());

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

    private void addEvent() {
        rl_headerNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(NTDMainActivity.this, NTDProfileActivity.class);
                myIntent.putExtra("Key", FirebaseAuth.getInstance().getCurrentUser().getUid());
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_slide_in_right, R.anim.anim_slide_out_right).toBundle();
                startActivity(myIntent, bndlanimation);
            }
        });
    }

    public void addView() {
        rl_headerNav = (RelativeLayout) contentView.findViewById(R.id.rl_headerNav);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        img_Avatar = (CircleImageView) contentView.findViewById(R.id.img_Avatar);
        img_CoverPhoto = (ImageView) contentView.findViewById(R.id.img_CoverPhoto);
        txt_CompanyName = (TextView) contentView.findViewById(R.id.txt_CompanyName);
        txt_Email = (TextView) contentView.findViewById(R.id.txt_Email);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setFragment() {
        //Thiet lap fragment ban dau //Framelayout
        NTDMainFragment fragment = new NTDMainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void setNavigationOpenClose() {
        //Thiet lap drawer trong navigation khi dong, mo
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(toolbar.getTitle());
                //getSupportActionBar().setTitle("JobStore");
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(toolbar.getTitle());
                // calling onPrepareOptionsMenu() to hide action bar icons
                loadData();
                invalidateOptionsMenu();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Bat su kien khi click vao item trong navigation view
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                trannsFragment(new NTDMainFragment(), "Trang chủ");
                break;
            case R.id.nav_list:
                if(checkStatus())
                    trannsFragment(new NTDPostRecruitmentFragment(), "Đăng tin");
                break;
            case R.id.nav_search:
                if(checkStatus())
                    trannsFragment(new NTDSearchFilterFragment(), "Lọc và tìm kiếm");
                break;
            case R.id.nav_manage:
                trannsFragment(new NTDPostManagerFragment(), "Quản lý thông tin tuyển dụng");
                break;
            case R.id.nav_notification:
                trannsFragment(new NTDNotificationFragment(), "Thông báo");
                break;
            case R.id.nav_logout:
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                stopService(service);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void trannsFragment(Fragment fragment, String title) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        toolbar.setTitle(title);
    }

    private void showNotificationBlockedDialog() {
        if(recruiter == null) return;
        // khởi tạo dialog
        final Dialog dialog = new Dialog(this);
        // xét layout cho dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_block_notification);

        // khai báo control trong dialog để bắt sự kiện
        final TextView txt_Content = (TextView) dialog.findViewById(R.id.txt_Content);
        final TextView txt_Status = (TextView) dialog.findViewById(R.id.txt_Status);
        txt_Content.setText(recruiter.getBlocked().getContent() + "\nMọi thắc mắc xin liên hệ jobstore.ad@gmail.com");
        if(recruiter.getBlocked().isPermanent())
            txt_Status.setText("Đã bị khóa vĩnh viễn");
        else txt_Status.setText("Mở khóa sau" + Block.getDistanceTime(recruiter.getBlocked().getFinishDate()));
        Button btn_Ok = (Button) dialog.findViewById(R.id.btn_Ok);
        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        // hiển thị dialog
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private boolean checkStatus() {
        if(recruiter == null) return false;

        if(recruiter.getStatus() == Status.ACTIVE)
            return true;
        else if(recruiter.getStatus() == Status.PENDING)
        {
            Toast.makeText(this, "Tài khoản của bạn đang chờ xác nhận!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(recruiter.getStatus() == Status.BLOCKED)
        {
            if(!recruiter.getBlocked().isActive())
            {
                showNotificationBlockedDialog();
                return false;
            }
            recruiter.setStatus(Status.ACTIVE);
            recruiter.setBlocked(null);
            Database.updateData(Node.RECRUITERS, recruiter.getKey(), recruiter);
            return true;
        }
        return false;
    }
}
