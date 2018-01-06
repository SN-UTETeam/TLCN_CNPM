package com.spkt.nguyenducnguu.jobstore.Candidate.fragments;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Const.RequestCode;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnFilterListener;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.SearchWorkInfoSetting;
import com.spkt.nguyenducnguu.jobstore.Candidate.adapters.UVSearchNTDListAdapter;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

public class UVSearchRecruiterFragment extends Fragment {
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    LinearLayout linearLayout;
    RecyclerView rv_Recruiter;
    UVSearchNTDListAdapter mRcvAdapter;
    TextView txt_Filter, txt_NumberResult;
    EditText txt_Query;
    List<Recruiter> lstData = new ArrayList<Recruiter>();
    public static SearchWorkInfoSetting mSettingSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_uv_search_recruiter, container, false);

        mSettingSearch = new SearchWorkInfoSetting();
        addView(rootView);
        setmRecyclerView();
        addEvent();
        setIcon();
        addOnScrolled();
        loadDefaultData();

        return rootView;
    }

    private void addView(View rootView) {
        rv_Recruiter = (RecyclerView) rootView.findViewById(R.id.rv_Recruiter);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.ln_filter);
        txt_Filter = (TextView) rootView.findViewById(R.id.txt_Filter);
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
                mRcvAdapter.FilterData(mSettingSearch);
            }
        });

        mRcvAdapter.setOnFilterListener(new OnFilterListener() {
            @Override
            public void onFilter(int numberResult) {
                txt_NumberResult.setText(numberResult + "/" + lstData.size());
            }
        });

        txt_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Chưa lọc được :))", Toast.LENGTH_LONG).show();
                return;

                //Intent intent = new Intent(getContext(), SelectCarrerActivity.class);
                //intent.putExtra("lstCareerSelected", mSettingSearch.getCareers().toString().substring(1, mSettingSearch.getCareers().toString().length() - 1).trim());
                //startActivityForResult(intent, RequestCode.SELECT_CAREER);
            }
        });
    }

    private void loadDefaultData() {
        lstData.clear();
        Database.getData(Node.RECRUITERS, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                lstData.clear();
                for (DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    Recruiter r = mdata.getValue(Recruiter.class);
                    if(r == null) continue;
                    r.setKey(mdata.getKey());
                    lstData.add(r);
                    mRcvAdapter.notifyDataSetChanged();
                    mRcvAdapter.FilterData(mSettingSearch);
                    txt_NumberResult.setText(lstData.size() + "/" + lstData.size());
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RequestCode.SELECT_CAREER)
        {
            if (data != null) {
                String message = data.getStringExtra("lstCareer");
                mSettingSearch.setCareers(message);
                mRcvAdapter.FilterData(mSettingSearch);
            }
        }
    }

    private void setmRecyclerView() {
        mRcvAdapter = new UVSearchNTDListAdapter(lstData);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_Recruiter.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        rv_Recruiter.setHasFixedSize(true);
        rv_Recruiter.setAdapter(mRcvAdapter);
    }

    private void addOnScrolled(){

        rv_Recruiter.addOnScrollListener(new RecyclerView.OnScrollListener() {

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

    private void setIcon(){
        txt_Filter.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
    }
}
