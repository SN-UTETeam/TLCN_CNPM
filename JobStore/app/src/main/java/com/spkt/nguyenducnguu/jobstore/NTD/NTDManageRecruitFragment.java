package com.spkt.nguyenducnguu.jobstore.NTD;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spkt.nguyenducnguu.jobstore.R;

/**
 * Created by TranAnhSon on 9/15/2017.
 */

public class NTDManageRecruitFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ntd_recruit_management_fragment, container, false);



        //Method để sử dụng font awesome trong fragment
       /* Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(rootView.findViewById(R.id.Manage_Recruit), iconFont);*/

        return rootView;
    }
}
