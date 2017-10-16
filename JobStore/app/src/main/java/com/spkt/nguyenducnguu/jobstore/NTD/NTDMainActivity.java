package com.spkt.nguyenducnguu.jobstore.NTD;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.LoginActivity;
import com.spkt.nguyenducnguu.jobstore.Models.Parameter;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntd_main);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.ntd_nav_header_main, null);

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
        Database.getData(Node.RECRUITERS, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot mdata : dataSnapshot.getChildren()) {
                    Recruiter r = mdata.getValue(Recruiter.class);

                    if (r == null) return;

                    txt_CompanyName.setText(r.getCompanyName());
                    txt_Email.setText(r.getEmail());

                    Picasso.with(getBaseContext()).load(r.getAvatar()).into(img_Avatar);
                    Picasso.with(getBaseContext()).load(r.getCoverPhoto()).into(img_CoverPhoto);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, new Parameter("email", user.getEmail()));
    }

    private void addEvent() {
        rl_headerNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Database.getData(Node.RECRUITERS, new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        for (DataSnapshot mdata : dataSnapshot.getChildren()) {
                            Log.d("Key", mdata.getKey());
                            Intent myIntent = new Intent(NTDMainActivity.this, NTDProfileActivity.class);
                            myIntent.putExtra("Key", mdata.getKey());
                            Bundle bndlanimation =
                                    ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_slide_in_right, R.anim.anim_slide_out_right).toBundle();
                            startActivity(myIntent, bndlanimation);
                        }
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                }, new Parameter("email", user.getEmail()));
            }
        });
    }

    //Thiet lap view trong layout
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
        //Thiet lap fragment ban dau
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
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle("JobStore");
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("JobStore");
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
            case R.id.nav_list:
                trannsFragment(new NTDPostRecruitmentFragment(), "Đăng tin");
                break;
            case R.id.nav_home:
                trannsFragment(new NTDMainFragment(), "Trang chủ");
                break;
            case R.id.nav_search:
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

}
