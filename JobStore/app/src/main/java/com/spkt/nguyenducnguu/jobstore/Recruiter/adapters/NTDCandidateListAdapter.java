package com.spkt.nguyenducnguu.jobstore.Recruiter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NTDCandidateListAdapter extends BaseAdapter {
    private List<Candidate> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public NTDCandidateListAdapter(Context aContext, List<Candidate> listData) {
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
        if(this.listData.get(i) == null) return null;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_candidate_layout, null);
            holder = new ViewHolder();
            holder.imgv_Avatar = (CircleImageView) view.findViewById(R.id.imgv_Avatar);
            holder.txt_Key = (TextView) view.findViewById(R.id.txt_Key);
            holder.txt_FullName = (TextView) view.findViewById(R.id.txt_FullName);
            holder.txt_Tag = (TextView) view.findViewById(R.id.txt_Tag);
            holder.txt_Experience = (TextView) view.findViewById(R.id.txt_Experience);
            holder.txt_WorkPlace = (TextView) view.findViewById(R.id.txt_WorkPlace);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.txt_Key.setText(this.listData.get(i).getKey() == null ? "" : this.listData.get(i).getKey());
        holder.txt_FullName.setText(this.listData.get(i).getFullName() == null ? "-- Chưa cập nhật --" : this.listData.get(i).getFullName());
        if(this.listData.get(i).getCandidateDetail() != null) {
            holder.txt_Tag.setText(this.listData.get(i).getCandidateDetail().getTag() == null ? "-- Chưa cập nhật --" : this.listData.get(i).getCandidateDetail().getTag());
            holder.txt_Experience.setText(this.listData.get(i).getCandidateDetail().getExperience() == null ? "-- Chưa cập nhật --" : this.listData.get(i).getCandidateDetail().getExperience());
            holder.txt_WorkPlace.setText(this.listData.get(i).getCandidateDetail().getWorkPlaces() == null ? "-- Chưa cập nhật --" : this.listData.get(i).getCandidateDetail().getWorkPlaces());
        }
        if(this.listData.get(i).getAvatar() != null)
            Picasso.with(context).load(this.listData.get(i).getAvatar()).into(holder.imgv_Avatar);
        else Picasso.with(context).load(R.drawable.ic_default_avatar).into(holder.imgv_Avatar);

        return view;
    }
    static class ViewHolder {
        CircleImageView imgv_Avatar;
        TextView txt_Key;
        TextView txt_FullName;
        TextView txt_Tag;
        TextView txt_Experience;
        TextView txt_WorkPlace;
    }
}
