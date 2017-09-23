package com.spkt.nguyenducnguu.jobstore.NTD;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spkt.nguyenducnguu.jobstore.R;

public class NTDPostRecruitmentFragment extends Fragment {
    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ntd_post_recruitment_fragment, container, false);

        btn = (Button) rootView.findViewById(R.id.buttonNext);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext() , NTDPostRecruitmentActivity.class);
                startActivity(myIntent);
            }
        });
        return rootView;
    }

}
