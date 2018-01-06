package com.spkt.nguyenducnguu.jobstore.Candidate.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spkt.nguyenducnguu.jobstore.R;

public class UVMainFragment extends Fragment {


    public UVMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_uv_main, container, false);

        return rootView;
    }

}
