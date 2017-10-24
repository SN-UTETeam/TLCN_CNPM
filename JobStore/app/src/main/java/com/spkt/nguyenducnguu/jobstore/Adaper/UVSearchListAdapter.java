package com.spkt.nguyenducnguu.jobstore.Adaper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TranAnhSon on 10/23/2017.
 */

public class UVSearchListAdapter extends RecyclerView.Adapter<UVSearchListAdapter.RecyclerViewHolder> {
    private List<String> data = new ArrayList<>();

    public UVSearchListAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_recruiter_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txt_CompanyName;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txt_CompanyName = (TextView) itemView.findViewById(R.id.txt_CompanyName);
        }
    }
}

