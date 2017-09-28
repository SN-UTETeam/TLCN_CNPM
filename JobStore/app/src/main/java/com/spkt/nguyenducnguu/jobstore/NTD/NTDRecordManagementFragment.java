package com.spkt.nguyenducnguu.jobstore.NTD;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;

/**
 * Created by TranAnhSon on 9/15/2017.
 */

public class NTDRecordManagementFragment extends Fragment {
    ImageView img_CoverPhoto;
    TextView txt_icon1,txt_icon2,txt_icon3,txt_icon4,txt_icon5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ntd_record_management_fragment, container, false);

        addView(rootView);
        setIcon();
        return rootView;
    }

    private void addView(View rootView){
        img_CoverPhoto = (ImageView) rootView.findViewById(R.id.img_CoverPhoto);
        txt_icon1 = (TextView) rootView.findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) rootView.findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) rootView.findViewById(R.id.txt_icon3);
        txt_icon4 = (TextView) rootView.findViewById(R.id.txt_icon4);
        txt_icon5 = (TextView) rootView.findViewById(R.id.txt_icon5);
    }
    private void setIcon(){
        txt_icon1.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        txt_icon4.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        txt_icon5.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
    }
}
