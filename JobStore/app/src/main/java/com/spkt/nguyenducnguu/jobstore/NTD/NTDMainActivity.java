package com.spkt.nguyenducnguu.jobstore.NTD;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.spkt.nguyenducnguu.jobstore.LoginActivity;
import com.spkt.nguyenducnguu.jobstore.R;

public class NTDMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    RelativeLayout rl_headerNav;
    ImageView img_Avatar;
    Toolbar toolbar = null;
    View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_main);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.ntd_nav_header_main, null);
        rl_headerNav = (RelativeLayout) contentView.findViewById(R.id.rl_headerNav);

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
    private void addEvent(){
        rl_headerNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), NTDProfileActivity.class);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_slide_in_right,R.anim.anim_slide_out_right).toBundle();
                startActivity(myIntent,bndlanimation);
            }
        });
    }
    //Thiet lap view trong layout
    public void addView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        img_Avatar = (ImageView) findViewById(R.id.img_Avatar);
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
    public void setFragment(){
        //Thiet lap fragment ban dau
        NTDMainFragment fragment = new NTDMainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void setNavigationOpenClose(){
        //Thiet lap drawer trong navigation khi dong, mo
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Bat su kien khi click vao item trong navigation view
        int id = item.getItemId();

        if (id == R.id.nav_list) {

            NTDPostRecruitmentFragment fragment = new NTDPostRecruitmentFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit );
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            toolbar.setTitle("Đăng tin");

        }else if (id == R.id.nav_home) {

            NTDMainFragment fragment = new NTDMainFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit );
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            toolbar.setTitle("Trang chủ");

        }
        else if (id == R.id.nav_search) {

            NTDSearchFilterFragment fragment = new NTDSearchFilterFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit );
            fragmentTransaction.replace(R.id.fragment_container, fragment);
           // fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            toolbar.setTitle("Lọc và tìm kiếm");

        } else if (id == R.id.nav_manage) {

            NTDPostManagerFragment fragment = new NTDPostManagerFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit );
            fragmentTransaction.replace(R.id.fragment_container, fragment);
           // fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            toolbar.setTitle("Quản lý thông tin tuyển dụng");

        }else if (id == R.id.nav_notification) {
            NTDNotificationFragment fragment = new NTDNotificationFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit );
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            toolbar.setTitle("Thông báo");

        } else if (id == R.id.nav_logout) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
