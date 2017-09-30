package com.spkt.nguyenducnguu.jobstore.NTD;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.Adaper.RecyclerViewSearchListAdapter;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

public class NTDSearchFilterFragment extends Fragment {
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    LinearLayout linearLayout;
    RecyclerView mRecyclerView;
    ScrollView scrollView;
    RecyclerViewSearchListAdapter mRcvAdapter;
    TextView txt_Filter;
    List<String> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ntd_search_filer_fragment, container, false);

        //Method để sử dụng font awesome trong fragment
        Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(rootView.findViewById(R.id.txt_Filter), iconFont);

        addView(rootView);
        addOnScrolled();
        addData();
        addEvent();
        setmRecyclerView();

        return rootView;
    }
    private void addView(View rootView){
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list_uv);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.ln_filter);
        txt_Filter = (TextView) rootView.findViewById(R.id.txt_Filter);
    }
    private void addEvent(){
        txt_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), NTDFilterActivity.class);
                startActivity(myIntent);
            }
        });
    }
    private void addOnScrolled(){

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                    hideViews();
                    controlsVisible = false;
                    scrolledDistance = 0;
                } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                    showViews();
                    controlsVisible = true;
                    scrolledDistance = 0;
                }

                if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
                    scrolledDistance += dy;
                }
            }
        });

    }
    private void hideViews() {
        linearLayout.animate().translationY(-linearLayout.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        linearLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }
    private void addData() {
        data = new ArrayList<>();

        data.add("Sang Sang Nguyễn");
        data.add("Hoàng Minh Lợi");
        data.add("Nguyễn Duy Bảo");
        data.add("Nguyễn Ngọc Doanh");
        data.add("Nguyễn Phạm Thế Hà");
        data.add("Trần Anh Đức");
        data.add("Trần Minh Hải");
        data.add("Trần Minh Hải");
        data.add("Trần Minh Hải");
        data.add("Trần Minh Hải");

        /*data1.add("Android Developer");
        data1.add("Web Developer");
        data1.add("IOS Developer");
        data1.add("Design");
        data1.add("HTML5/CSS");
        data1.add(".....");
        data1.add(".....");
        data1.add(".....");
        data1.add(".....");
        data1.add(".....");

        data2.add("Trên 1 năm");
        data2.add("Dưới 1 năm");
        data2.add("3 năm");
        data2.add("3 - 5 năm");
        data2.add("5 - 8 năm");
        data2.add(".....");
        data2.add(".....");
        data2.add(".....i");
        data2.add(".....");
        data2.add(".....");

        data3.add("Thanh Hóa");
        data3.add("Nha Trang");
        data3.add("Quảng Ngãi");
        data3.add("Đăk Lắc");
        data3.add("Huế");
        data3.add(".....");
        data3.add(".....");
        data3.add(".....");
        data3.add(".....");
        data3.add(".....");*/

        mRcvAdapter = new RecyclerViewSearchListAdapter(data);
    }
    private void setmRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRcvAdapter);
    }
}
