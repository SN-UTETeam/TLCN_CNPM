package com.spkt.nguyenducnguu.jobstore.Candidate.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.R;

public class RecruiterInfoFragment extends Fragment {
    private String Key = "";
    TextView txt_Address, txt_FullName, txt_Email, txt_Phone, txt_Website;
    ExpandableTextView txt_Description;
    TextView txt_icon1, txt_icon2, txt_icon3, txt_icon4, txt_icon5, txt_icon6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recruiter_info_fragment, container, false);
        if(getArguments().getString("Key") != null && !getArguments().getString("Key").isEmpty())
        {
            Key = getArguments().getString("Key");
        }
        addView(rootView);
        loadData();
        setIcon(rootView);
        return rootView;
    }
    private void addView(View view){
        txt_Address = (TextView) view.findViewById(R.id.txt_Address);
        txt_FullName = (TextView) view.findViewById(R.id.txt_FullName);
        txt_Email = (TextView) view.findViewById(R.id.txt_Email);
        txt_Phone = (TextView) view.findViewById(R.id.txt_Phone);
        txt_Website = (TextView) view.findViewById(R.id.txt_Website);
        txt_Description = (ExpandableTextView) view.findViewById(R.id.txt_Description);
    }

    private void loadData(){
        Database.getData(Node.RECRUITERS + "/" + Key, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Recruiter r = dataSnapshot.getValue(Recruiter.class);
                if(r == null) return;
                txt_FullName.setText(r.getFullName() == null ? "-- Chưa cập nhật --" : r.getFullName());
                txt_Email.setText(r.getEmail() == null ? "-- Chưa cập nhật --" : r.getEmail());
                txt_Phone.setText(r.getPhone() == null ? "-- Chưa cập nhật --" : r.getPhone());
                txt_Website.setText(r.getWebsite() == null ? "-- Chưa cập nhật --" : r.getWebsite());
                if(r.getAddress() != null)
                    txt_Address.setText(r.getAddress().getAddressStr() == null ? "-- Chưa cập nhật --" : r.getAddress().getAddressStr());
                else txt_Address.setText("-- Chưa cập nhật --");
                txt_Description.setText(r.getDescription() == null ? "-- Chưa cập nhật --" : r.getDescription());
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    private void setIcon(View view) {
        txt_icon1 = (TextView) view.findViewById(R.id.txt_icon1);
        txt_icon2 = (TextView) view.findViewById(R.id.txt_icon2);
        txt_icon3 = (TextView) view.findViewById(R.id.txt_icon3);
        txt_icon4 = (TextView) view.findViewById(R.id.txt_icon4);
        txt_icon5 = (TextView) view.findViewById(R.id.txt_icon5);
        txt_icon6 = (TextView) view.findViewById(R.id.txt_icon6);

        txt_icon1.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        txt_icon2.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        txt_icon3.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        txt_icon4.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        txt_icon5.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        txt_icon6.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
    }
}
