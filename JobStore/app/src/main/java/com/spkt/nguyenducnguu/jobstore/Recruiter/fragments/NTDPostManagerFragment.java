package com.spkt.nguyenducnguu.jobstore.Recruiter.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Adaper.ViewPagerInFragmentAdapter;

public class NTDPostManagerFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ntd_post_manager, container, false);
        addView(rootView);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        addEvent();

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        /*
        Tạo 1 lớp adapter để chứa 2 fragment
         */
        ViewPagerInFragmentAdapter adapter = new ViewPagerInFragmentAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new NTDPostFragment(), "Còn hạn");
        adapter.addFragment(new NTDPostExpiredFragment(), "Hết hạn");
        viewPager.setAdapter(adapter);
    }

    private void addView(View rootView) {
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_Pager);
    }

    private void addEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //Hàm tab được chọn
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            //Hàm tab không được chọn
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            //Hàm reselected tab
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

}
