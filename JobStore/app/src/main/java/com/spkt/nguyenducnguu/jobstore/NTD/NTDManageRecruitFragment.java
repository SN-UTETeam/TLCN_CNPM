package com.spkt.nguyenducnguu.jobstore.NTD;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spkt.nguyenducnguu.jobstore.R;

/**
 * Created by TranAnhSon on 9/15/2017.
 */

public class NTDManageRecruitFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ntd_recruit_management_fragment, container, false);

        addView(rootView);

        //Method để sử dụng font awesome trong fragment
       /* Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(rootView.findViewById(R.id.Manage_Recruit), iconFont);*/
        viewPager.setAdapter(new CustomAdapter(getFragmentManager(),getContext()));
        tabLayout.setupWithViewPager(viewPager);

        addEvent();
        return rootView;
    }
    private void addView(View rootView){
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_Pager);
    }
    private void addEvent()
    {
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
    //Hàm dùng để viewpager có thể hoạt động
    private class CustomAdapter extends FragmentPagerAdapter {

        private String fragments [] = {"Còn hạn","Hết hạn"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new NTDResumesFragment();
                case 1:
                    return new NTDResumesExpiredFragment();
                default:
                    return null;
            }
        }

        //Xác định độ dài của fragments
        @Override
        public int getCount() {
            return fragments.length;
        }
        //Trả về vị trí của fragment đó
        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }
}
