package com.spkt.nguyenducnguu.jobstore.NTD;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.R;

/**
 * Created by TranAnhSon on 9/15/2017.
 */

public class NTDRecordManagementFragment extends Fragment {
    ImageView img_CoverPhoto;
    Button btn_ChangeProfile, btn_UpLoadImage;
    TextView txt_icon1,txt_icon2,txt_icon3,txt_icon4,txt_icon5, txt_icon6, txt_icon7;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ntd_record_management_fragment, container, false);

        addView(rootView);
        addEvent();
        setIcon();
        return rootView;
    }

    private void addView(View rootView){
        img_CoverPhoto = (ImageView) rootView.findViewById(R.id.img_CoverPhoto);
        btn_ChangeProfile = (Button) rootView.findViewById(R.id.btn_ChangeProfile);
        btn_UpLoadImage = (Button) rootView.findViewById(R.id.btn_UpLoadImage);
        txt_icon1 = (TextView) rootView.findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) rootView.findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) rootView.findViewById(R.id.txt_icon3);
        txt_icon4 = (TextView) rootView.findViewById(R.id.txt_icon4);
        txt_icon5 = (TextView) rootView.findViewById(R.id.txt_icon5);
        txt_icon6 = (TextView) rootView.findViewById(R.id.txt_icon6);
        txt_icon7 = (TextView) rootView.findViewById(R.id.txt_icon7);

    }
    private void addEvent()
    {
        btn_ChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myItent = new Intent(getActivity(), NTDChangeProfileActivity.class);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getActivity(), R.anim.anim_slide_in_left,R.anim.anim_slide_out_left).toBundle();
                startActivity(myItent,bndlanimation);
            }

        });
    }
    private void setIcon(){
        txt_icon1.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        txt_icon4.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        txt_icon5.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        btn_ChangeProfile.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
        btn_UpLoadImage.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
    }
}
