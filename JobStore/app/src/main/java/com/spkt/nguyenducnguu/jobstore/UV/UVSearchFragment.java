package com.spkt.nguyenducnguu.jobstore.UV;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spkt.nguyenducnguu.jobstore.Adaper.RCVListNTDAdapter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TranAnhSon on 10/21/2017.
 */

public class UVSearchFragment extends Fragment {

    List<WorkInfo> lstWorkInfo = new ArrayList<WorkInfo>();
    List<String> lstKey = new ArrayList<String>();
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_uv_search, container, false);

        addView(rootView);
        setmRecyclerView();

        mRecyclerView.setAdapter(new RCVListNTDAdapter(lstWorkInfo, lstKey));

        return rootView;
    }
    private void addView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list_ntd);
    }
    private void setmRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new RCVListNTDAdapter(lstWorkInfo, lstKey));
    }
    private void loadData()
    {
    }
}
