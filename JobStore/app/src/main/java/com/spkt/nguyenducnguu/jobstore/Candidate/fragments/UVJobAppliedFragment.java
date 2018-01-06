package com.spkt.nguyenducnguu.jobstore.Candidate.fragments;

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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnFilterListener;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.SearchWorkInfoSetting;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Candidate.adapters.UVSearchWorkInfoAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UVJobAppliedFragment extends Fragment {
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    public static SearchWorkInfoSetting mSettingSearch;

    LinearLayout ln_filter;
    RecyclerView rv_WorkInfoSaved;
    UVSearchWorkInfoAdapter mAdapter;
    TextView txt_NumberResult;
    EditText txt_Query;
    List<WorkInfo> lstData = new ArrayList<WorkInfo>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_uv_jobapplied, container, false);

        mSettingSearch = new SearchWorkInfoSetting();
        addView(rootView);
        setmRecyclerView();
        addEvent();
        addOnScrolled();
        loadData();

        return rootView;
    }

    private void loadData() {
        lstData.clear();
        Database.getData(Node.WORKINFOS, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                lstData.clear();
                for (DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    WorkInfo w = mdata.getValue(WorkInfo.class);
                    if(w == null || w.getExpirationTime() == null) continue;
                    w.setKey(mdata.getKey());
                    if(!w.checkApply(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        continue;
                    lstData.add(w);
                    Collections.sort(lstData);
                    mAdapter.notifyDataSetChanged();
                    mAdapter.FilterData(mSettingSearch);
                    txt_NumberResult.setText(lstData.size() + "/" + lstData.size());
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    private void addView(View rootView) {
        rv_WorkInfoSaved = (RecyclerView) rootView.findViewById(R.id.rv_WorkInfoSaved);
        ln_filter = (LinearLayout) rootView.findViewById(R.id.ln_filter);
        txt_NumberResult = (TextView) rootView.findViewById(R.id.txt_NumberResult);
        txt_Query = (EditText) rootView.findViewById(R.id.txt_Query);
    }

    private void addEvent() {
        txt_Query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSettingSearch.setQuery(txt_Query.getText().toString());
                mAdapter.FilterData(mSettingSearch);
            }
        });
        mAdapter.setOnFilterListener(new OnFilterListener() {
            @Override
            public void onFilter(int numberResult) {
                txt_NumberResult.setText(numberResult + "/" + lstData.size());
            }
        });
    }

    private void addOnScrolled(){

        rv_WorkInfoSaved.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
        ln_filter.animate().translationY(-ln_filter.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        ln_filter.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    private void setmRecyclerView(){
        mAdapter = new UVSearchWorkInfoAdapter(lstData);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_WorkInfoSaved.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        rv_WorkInfoSaved.setHasFixedSize(true);
        rv_WorkInfoSaved.setAdapter(mAdapter);
    }
}
