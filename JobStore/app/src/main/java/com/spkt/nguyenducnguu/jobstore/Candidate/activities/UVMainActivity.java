package com.spkt.nguyenducnguu.jobstore.Candidate.activities;

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
import com.spkt.nguyenducnguu.jobstore.Candidate.fragments.UVMainFragment;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Const.Status;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.LoginActivity;
import com.spkt.nguyenducnguu.jobstore.Models.Block;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Service.ListenNewWorkInfoService;
import com.spkt.nguyenducnguu.jobstore.Candidate.fragments.UVFollowFragment;
import com.spkt.nguyenducnguu.jobstore.Candidate.fragments.UVJobFragment;
import com.spkt.nguyenducnguu.jobstore.Candidate.fragments.UVSearchWorkInfoFragment;
import com.spkt.nguyenducnguu.jobstore.Candidate.fragments.UVNotificationFragment;
import com.spkt.nguyenducnguu.jobstore.Candidate.fragments.UVSearchRecruiterFragment;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UVMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationViewUV = null;
    RelativeLayout rl_UV_headerNav;
    CircleImageView img_AvatarUV;
    TextView txt_NameUV, txt_EmailUV;
    ImageView img_CoverPhotoUV;
    Toolbar toolbarUV = null;
    View contentView;
    Intent service;
    Candidate candidate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_main);

        //Register Service
        service = new Intent(this, ListenNewWorkInfoService.class);
        startService(service);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.activity_uv_nav_header_main, null);

        addView();
        addEvent();

        //Thiet lap toolBar
        setSupportActionBar(toolbarUV);

        //Thiet lap drawer trong navigation khi dong, mo
        setNavigationOpenClose();

        navigationViewUV.addHeaderView(contentView);
        navigationViewUV.setNavigationItemSelectedListener(this);

        //Thiet lap fragment ban dau
        trannsFragment(new UVMainFragment(), "Job Store");
    }

    private void loadData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Database.getData(Node.CANDIDATES + "/" + user.getUid(), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Candidate c = dataSnapshot.getValue(Candidate.class);

                if (c == null) return;
                c.setKey(dataSnapshot.getKey());
                candidate = c;

                txt_NameUV.setText(c.getFullName() == null ? "-- Chưa cập nhật --" : c.getFullName());
                txt_EmailUV.setText(c.getEmail() == null ? "-- Chưa cập nhật --" : c.getEmail());

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
                myIntent.putExtra("Key", FirebaseAuth.getInstance().getCurrentUser().getUid());
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

    public void setNavigationOpenClose() {
        //Thiet lap drawer trong navigation khi dong, mo
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutUV);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarUV, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(toolbarUV.getTitle());
                //abc
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(toolbarUV.getTitle());
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
            case R.id.navUV_home:
                if(checkStatus())
                    trannsFragment(new UVMainFragment(), "Job Store");
                break;
            case R.id.navUV_searchWorkInfo:
                if(checkStatus())
                    trannsFragment(new UVSearchWorkInfoFragment(), "Tìm kiếm công việc");
                break;
            case R.id.navUV_searchRecruiter:
                if(checkStatus())
                    trannsFragment(new UVSearchRecruiterFragment(), "Tìm kiếm nhà tuyển dụng");
                break;
            case R.id.navUV_follow:
                if(checkStatus())
                    trannsFragment(new UVFollowFragment(), "Theo dõi");
                break;
            case R.id.navUV_job:
                if(checkStatus())
                    trannsFragment(new UVJobFragment(), "Công việc");
                break;
            case R.id.navUV_notification:
                trannsFragment(new UVNotificationFragment(), "Thông báo");
                break;
            case R.id.navUV_logout:
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                stopService(service);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutUV);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void trannsFragment(Fragment fragment, String title) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.fragment_containerUV, fragment);
        //if(fragment instanceof  UVMainFragment)
            //fragmentTransaction.addToBackStack("UVMainFragment");
        fragmentTransaction.commit();

        toolbarUV.setTitle(title);
    }

    private void showNotificationBlockedDialog() {
        if(candidate == null) return;
        // khởi tạo dialog
        final Dialog dialog = new Dialog(this);
        // xét layout cho dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_block_notification);

        // khai báo control trong dialog để bắt sự kiện
        final TextView txt_Content = (TextView) dialog.findViewById(R.id.txt_Content);
        final TextView txt_Status = (TextView) dialog.findViewById(R.id.txt_Status);
        txt_Content.setText(candidate.getBlocked().getContent() + "\nMọi thắc mắc xin liên hệ jobstore.ad@gmail.com");
        if(candidate.getBlocked().isPermanent())
            txt_Status.setText("Đã bị khóa vĩnh viễn");
        else txt_Status.setText("Mở khóa sau" + Block.getDistanceTime(candidate.getBlocked().getFinishDate()));
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
        if(candidate == null) return false;

        if(candidate.getStatus() == Status.ACTIVE)
            return true;
        else if(candidate.getStatus() == Status.PENDING)
        {
            Toast.makeText(this, "Tài khoản của bạn đang chờ xác nhận!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(candidate.getStatus() == Status.BLOCKED)
        {
            if(!candidate.getBlocked().isActive())
            {
                showNotificationBlockedDialog();
                return false;
            }
            candidate.setStatus(Status.ACTIVE);
            candidate.setBlocked(null);
            Database.updateData(Node.CANDIDATES, candidate.getKey(), candidate);
            return true;
        }
        return false;
    }
}
