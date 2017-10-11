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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference rolesRef = FirebaseDatabase.getInstance().getReference("Recruiters");
                Query query = rolesRef.orderByChild("email").equalTo(user.getEmail());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot mdata : dataSnapshot.getChildren())
                        {
                            Log.d("Key", mdata.getKey());
                            Intent myIntent = new Intent(NTDMainActivity.this, NTDProfileActivity.class);
                            myIntent.putExtra("Key", mdata.getKey());
                            Bundle bndlanimation =
                                    ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_slide_in_right,R.anim.anim_slide_out_right).toBundle();
                            startActivity(myIntent,bndlanimation);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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

    private void trannsFragment(Fragment fragment,String title) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit );
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        toolbar.setTitle(title);
    }

}
