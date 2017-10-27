package com.spkt.nguyenducnguu.jobstore.UV;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.Adaper.UVSearchWorkInfoListAdapter;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

public class UVSearchFragment extends Fragment {
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    LinearLayout linearLayout;
    RecyclerView rv_WorkInfo;
    UVSearchWorkInfoListAdapter mRcvAdapter;
    TextView txt_Filter;
    EditText txt_Search;
    List<WorkInfo> lstData = new ArrayList<WorkInfo>();
    List<String> lstKey = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_uv_search, container, false);

        addView(rootView);
        setmRecyclerView();
        addEvent();
        setIcon();
        addOnScrolled();

        return rootView;
    }

    private void addView(View rootView){
        rv_WorkInfo = (RecyclerView) rootView.findViewById(R.id.rv_WorkInfo);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.ln_filter);
        txt_Filter = (TextView) rootView.findViewById(R.id.txt_Filter);
        txt_Search = (EditText) rootView.findViewById(R.id.txt_Search);
    }
    private void addEvent(){
        txt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void addOnScrolled(){

        rv_WorkInfo.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
    private void setmRecyclerView(){
        mRcvAdapter = new UVSearchWorkInfoListAdapter(lstData, lstKey);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_WorkInfo.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        rv_WorkInfo.setHasFixedSize(true);
        rv_WorkInfo.setAdapter(mRcvAdapter);
    }
    private void setIcon(){
        txt_Filter.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
    }
}
