package com.spkt.nguyenducnguu.jobstore.NTD;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spkt.nguyenducnguu.jobstore.Adaper.RecyclerViewAdapter;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

public class NTDSearchFilterFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRcvAdapter;
    List<String> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ntd_search_filer_fragment, container, false);

        //Method để sử dụng font awesome trong fragment
        /*Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(rootView.findViewById(R.id.Main), iconFont);*/

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list_uv);
        data = new ArrayList<>();

        data.add("Nguyễn Minh Hưng");
        data.add("Hoàng Minh Lợi");
        data.add("Nguyễn Duy Bảo");
        data.add("Nguyễn Ngọc Doanh");
        data.add("Nguyễn Phạm Thế Hà");
        data.add("Trần Anh Đức");
        data.add("Trần Minh Hải");

        mRcvAdapter = new RecyclerViewAdapter(data);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        // ta sẽ set setHasFixedSize bằng True để tăng performance
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRcvAdapter);

        return rootView;
    }
}
