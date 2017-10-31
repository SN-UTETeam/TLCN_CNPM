package com.spkt.nguyenducnguu.jobstore.NTD.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TranAnhSon on 9/16/2017.
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.RecyclerViewHolder> {
    private List<String> data = new ArrayList<>();

    public SearchListAdapter(List<String> data) {
        this.data = data;
    }
    //class này giúp kiểm soát các view tốt hơn
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //dùng để gán giao diện cho một phần tử của RecyclerView
        View view = inflater.inflate(R.layout.list_item_candidate_layout, parent, false);
        return new RecyclerViewHolder(view);
    }
    //Là phương thức để gán dữ liệu từ listData vào viewHolder
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.txt_FullName.setText(data.get(position));
    }

    //trả về số lượng phần tử
    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txt_FullName;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txt_FullName = (TextView) itemView.findViewById(R.id.txt_FullName);
        }
    }
}
