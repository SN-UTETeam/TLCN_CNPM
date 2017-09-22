package com.spkt.nguyenducnguu.jobstore.NTD;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;

public class NTDMainFragment extends Fragment {
    Button btnNext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ntd_main_fragment, container, false);

        //Method để sử dụng font awesome trong fragment
        Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(rootView.findViewById(R.id.textview_1), iconFont);
        FontManager.markAsIconContainer(rootView.findViewById(R.id.textview_2), iconFont);
        FontManager.markAsIconContainer(rootView.findViewById(R.id.textview_3), iconFont);

        btnNext = (Button) rootView.findViewById(R.id.buttonNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), NTDPostRecruitmentActivity.class);
                startActivity(myIntent);
            }
        });
        return rootView;
    }
}
