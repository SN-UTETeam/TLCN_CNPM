package com.spkt.nguyenducnguu.jobstore.Candidate.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spkt.nguyenducnguu.jobstore.Adaper.ViewPagerInFragmentAdapter;
import com.spkt.nguyenducnguu.jobstore.R;

/**
 * Created by sontr on 10/27/17.
 */

public class UVJobFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_uv_job, container, false);

        addView(rootView);
        addEvent();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        /*
        Tạo 1 lớp adapter để chứa 2 fragment
         */
        ViewPagerInFragmentAdapter adapter = new ViewPagerInFragmentAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new UVJobSavedFragment(), "Đã lưu");
        adapter.addFragment(new UVJobAppliedFragment(), "Đã ứng tuyển");
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
