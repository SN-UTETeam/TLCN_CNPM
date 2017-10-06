package com.spkt.nguyenducnguu.jobstore.Adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.List;

public class CalidateListAdapter extends BaseAdapter {
    private List<Candidate> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CalidateListAdapter(Context aContext,  List<Candidate> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_candidate_layout, null);
            holder = new ViewHolder();
            holder.imgv_Avatar = (ImageView) view.findViewById(R.id.imgv_Avatar);
            holder.txt_Email = (TextView) view.findViewById(R.id.txt_Email);
            holder.txt_FullName = (TextView) view.findViewById(R.id.txt_FullName);
            holder.txt_Tag = (TextView) view.findViewById(R.id.txt_Tag);
            holder.txt_Experience = (TextView) view.findViewById(R.id.txt_Experience);
            holder.txt_WorkPlace = (TextView) view.findViewById(R.id.txt_WorkPlace);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.txt_Email.setText(this.listData.get(i).getEmail());
        holder.txt_FullName.setText(this.listData.get(i).getFullName());
        holder.txt_Tag.setText(this.listData.get(i).getCandidateDetail().getTag());
        holder.txt_Experience.setText(this.listData.get(i).getCandidateDetail().getExperience());
        holder.txt_WorkPlace.setText(this.listData.get(i).getCandidateDetail().getWorkPlaces());
        return view;
    }
    static class ViewHolder {
        ImageView imgv_Avatar;
        TextView txt_Email;
        TextView txt_FullName;
        TextView txt_Tag;
        TextView txt_Experience;
        TextView txt_WorkPlace;
    }
}
