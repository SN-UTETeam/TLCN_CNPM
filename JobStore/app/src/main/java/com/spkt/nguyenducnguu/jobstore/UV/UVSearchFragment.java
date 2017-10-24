package com.spkt.nguyenducnguu.jobstore.UV;

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

import com.spkt.nguyenducnguu.jobstore.Adaper.UVSearchListAdapter;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TranAnhSon on 10/21/2017.
 */

public class UVSearchFragment extends Fragment {
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    LinearLayout linearLayout;
    RecyclerView mRecyclerView;
    ScrollView scrollView;
    UVSearchListAdapter mRcvAdapter;
    TextView txt_Filter;
    List<String> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_uv_search, container, false);

        addView(rootView);
        setIcon();
        addOnScrolled();
        addData();

        setmRecyclerView();

        return rootView;
    }
    private void setIcon(){
        txt_Filter.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
    }
    private void addView(View rootView){
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list_ntd);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.ln_filter);
        txt_Filter = (TextView) rootView.findViewById(R.id.txt_Filter);
    }
    private void addEvent(){

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

        data.add("VNG COOPERATION");
        data.add("VNG COOPERATION1");
        data.add("VNG COOPERATION2");
        data.add("VNG COOPERATION3");
        data.add("VNG COOPERATION4");
        data.add("VNG COOPERATION5");
        data.add("VNG COOPERATION6");
        data.add("VNG COOPERATION7");
        data.add("VNG COOPERATION8");
        data.add("VNG COOPERATION9");
        data.add("VNG COOPERATION10");

        mRcvAdapter = new UVSearchListAdapter(data);
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
