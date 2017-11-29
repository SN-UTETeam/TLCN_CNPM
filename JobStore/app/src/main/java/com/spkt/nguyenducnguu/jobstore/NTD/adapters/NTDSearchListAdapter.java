package com.spkt.nguyenducnguu.jobstore.NTD.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnFilterListener;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.SearchCandidateSetting;
import com.spkt.nguyenducnguu.jobstore.Models.SearchWorkInfoSetting;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.UV.activities.UVProfileActivity;
import com.spkt.nguyenducnguu.jobstore.UV.activities.UVViewWorkInfoActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NTDSearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private ItemFilter mFilter = new ItemFilter();
    private List<Candidate> lstData;
    private List<Candidate> filteredData = new ArrayList<Candidate>();
    private Context context;
    private OnFilterListener onFilterListener;
    private SearchCandidateSetting mSetting;

    public NTDSearchListAdapter(List<Candidate> lstData) {
        mSetting = new SearchCandidateSetting();
        this.lstData = lstData;
        this.filteredData.addAll(lstData);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_candidate_layout, parent, false);
        viewHolder = new CandidateViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CandidateViewHolder vh = (CandidateViewHolder) holder;
        Log.d("CheckPosition", "Position: " + position + " - Size: " + filteredData.size());
        Candidate can = filteredData.get(position);
        vh.txt_Key.setText(can.getKey());
        vh.txt_FullName.setText(can.getFullName());
        vh.txt_Tag.setText(can.getCandidateDetail().getTag());
        vh.txt_Experience.setText(can.getCandidateDetail().getExperience());
        vh.txt_WorkPlace.setText(can.getCandidateDetail().getWorkPlaces());

        if(can.getAvatar() != null)
            Picasso.with(context).load(can.getAvatar()).into(vh.imgv_Avatar);
        else
            Picasso.with(context).load(R.drawable.ic_default_avatar).into(vh.imgv_Avatar);

        vh.ln_Candidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt = (TextView) v.findViewById(R.id.txt_Key);
                Intent intent = new Intent(context, UVProfileActivity.class);
                intent.putExtra("Key", txt.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void setOnFilterListener(OnFilterListener onFilterListener){
        this.onFilterListener = onFilterListener;
    }

    public void FilterData(SearchCandidateSetting searchCandidateSetting){
        mSetting = searchCandidateSetting;
        String Query = "";
        Query += mSetting.getWorkTypes().toString().substring(1, mSetting.getWorkTypes().toString().length() - 1);
        Query += " " + mSetting.getWorkPlaces().toString().substring(1, mSetting.getWorkPlaces().toString().length() - 1);
        Query += " " + mSetting.getCareers().toString().substring(1, mSetting.getCareers().toString().length() - 1);
        Query += " " + mSetting.getSalary();
        Query += " " + mSetting.getExperience();
        Query += " " + mSetting.getLevel();
        Query += " " + mSetting.getQuery();

        mFilter.filter(Query);
    }

    private class ItemFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().trim().toUpperCase();
            String[] filterArr = filterString.split(" ");
            filteredData.clear();

            FilterResults filterResults = new FilterResults();

            if(filterString.length() > 0){
                for(int i=0; i < lstData.size(); i++){
                    Candidate can = lstData.get(i);
                    for (String str : filterArr) {
                        str = str.trim();
                        if(str.isEmpty() || str == "") continue;
                        if (can.getFullName().toUpperCase().contains(str)
                                || can.getCandidateDetail().getTag().toUpperCase().contains(str)
                                || can.getCandidateDetail().getWorkPlaces().toUpperCase().contains(str)
                                || can.getCandidateDetail().getCareers().toUpperCase().contains(str)
                                || can.getCandidateDetail().getWorkTypes().toUpperCase().contains(str)
                                || can.getCandidateDetail().getSalary().toUpperCase().contains(str)
                                || can.getCandidateDetail().getExperience().toUpperCase().contains(str)
                                || can.getCandidateDetail().getLevel().toUpperCase().contains(str))
                        {
                            filteredData.add(can);
                            break;
                        }
                    }
                }
            } else {
                filteredData.addAll(lstData);
            }

            filterResults.values = filteredData;
            filterResults.count = filteredData.size();

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(onFilterListener != null)
                onFilterListener.onFilter(filteredData.size());
            notifyDataSetChanged();
        }
    }

    public class CandidateViewHolder extends RecyclerView.ViewHolder {
        TextView txt_Key, txt_FullName, txt_Tag, txt_Experience, txt_WorkPlace;
        CircleImageView imgv_Avatar;
        LinearLayout ln_Candidate;

        public CandidateViewHolder(View itemView) {
            super(itemView);
            imgv_Avatar = (CircleImageView) itemView.findViewById(R.id.imgv_Avatar);
            ln_Candidate = (LinearLayout) itemView.findViewById(R.id.ln_Candidate);

            txt_Key = (TextView) itemView.findViewById(R.id.txt_Key);
            txt_FullName = (TextView) itemView.findViewById(R.id.txt_FullName);
            txt_Tag = (TextView) itemView.findViewById(R.id.txt_Tag);
            txt_Experience = (TextView) itemView.findViewById(R.id.txt_Experience);
            txt_WorkPlace = (TextView) itemView.findViewById(R.id.txt_WorkPlace);
        }
    }
}
