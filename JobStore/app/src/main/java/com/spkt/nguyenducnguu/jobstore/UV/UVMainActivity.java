package com.spkt.nguyenducnguu.jobstore.UV;

import android.app.ActivityOptions;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.spkt.nguyenducnguu.jobstore.LoginActivity;
import com.spkt.nguyenducnguu.jobstore.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UVMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationViewUV = null;
    RelativeLayout rl_UV_headerNav;
    CircleImageView img_AvatarUV;
    TextView txt_NameUV, txt_EmailUV;
    ImageView img_CoverPhotoUV;
    Toolbar toolbarUV = null;
    View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_main);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.activity_uv_nav_header_main, null);

        addView();
        addEvent();
        //Thiet lap fragment ban dau
        setFragment();

        //Thiet lap toolBar
        setSupportActionBar(toolbarUV);

        //Thiet lap drawer trong navigation khi dong, mo
        setNavigationOpenClose();

        navigationViewUV.addHeaderView(contentView);
        navigationViewUV.setNavigationItemSelectedListener(this);
    }
    public void addView() {
        rl_UV_headerNav = (RelativeLayout) contentView.findViewById(R.id.rl_UV_headerNav);
        toolbarUV = (Toolbar) findViewById(R.id.toolbarUV);
        navigationViewUV = (NavigationView) findViewById(R.id.nav_viewUV);
        img_AvatarUV = (CircleImageView) contentView.findViewById(R.id.img_AvatarUV);
        img_CoverPhotoUV = (ImageView) contentView.findViewById(R.id.img_CoverPhotoUV);
        txt_NameUV = (TextView) contentView.findViewById(R.id.txt_UVName);
        txt_EmailUV = (TextView) contentView.findViewById(R.id.txt_EmailUV);
    }
    private void addEvent() {
        rl_UV_headerNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(UVMainActivity.this, UVProfileActivity.class);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(UVMainActivity.this, R.anim.anim_slide_in_right, R.anim.anim_slide_out_right).toBundle();
                startActivity(myIntent, bndlanimation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutUV);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setFragment() {
        //Thiet lap fragment ban dau //Framelayout
        UVMainFragment fragment = new UVMainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_containerUV, fragment);
        fragmentTransaction.commit();
    }

    public void setNavigationOpenClose() {
        //Thiet lap drawer trong navigation khi dong, mo
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutUV);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarUV, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            public void onDrawerClosed(View view) {
                //getSupportActionBar().setTitle("JobStore");
                //abc
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("JobStore");
                // calling onPrepareOptionsMenu() to hide action bar icons
                //loadData();
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
            case R.id.navUV_home:
                trannsFragment(new UVMainFragment(), "Trang chủ");
                break;
            case R.id.navUV_search:
                trannsFragment(new UVSearchFragment(), "Tìm kiếm");
                break;
            case R.id.navUV_save:
                trannsFragment(new UVJobSavedFragment(), "Việc đã lưu");
                break;
            case R.id.navUV_apply:
                trannsFragment(new UVJobAppliedFragment(), "Việc đã ứng tuyển");
                break;
            case R.id.navUV_notification:
                trannsFragment(new UVNotificationFragment(), "Thông báo");
                break;
            case R.id.navUV_logout:
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutUV);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void trannsFragment(Fragment fragment, String title) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.fragment_containerUV, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        toolbarUV.setTitle(title);
    }

}
