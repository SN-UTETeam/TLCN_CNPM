package com.spkt.nguyenducnguu.jobstore.Candidate.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnFilterListener;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.SearchWorkInfoSetting;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Candidate.activities.UVViewWorkInfoActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UVSearchWorkInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private ItemFilter mFilter = new ItemFilter();
    private List<WorkInfo> lstData;
    private List<WorkInfo> filteredData = new ArrayList<WorkInfo>();
    private Context context;
    private OnFilterListener onFilterListener;
    private SearchWorkInfoSetting mSetting;
    private boolean Sorted = false;

    public UVSearchWorkInfoAdapter(List<WorkInfo> lstData) {
        mSetting = new SearchWorkInfoSetting();
        this.lstData = lstData;
        this.filteredData.addAll(lstData);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_workinfo_layout, parent, false);
        viewHolder = new WorkInfoViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final WorkInfoViewHolder vh = (WorkInfoViewHolder) holder;
        WorkInfo w = filteredData.get(position);
        if(w == null) return;
        vh.txt_WorkInfoKey.setText(w.getKey() == null ? "" : w.getKey());
        vh.txt_TitlePost.setText(w.getTitlePost() == null ? "-- Chưa cập nhật --" : w.getTitlePost());
        if(w.getWorkDetail() != null) {
            vh.txt_Career.setText(w.getWorkDetail().getCarrers() == null ? "-- Chưa cập nhật --" : w.getWorkDetail().getCarrers());
            vh.txt_Salary.setText(w.getWorkDetail().getSalary() == null ? "-- Chưa cập nhật --" : w.getWorkDetail().getSalary());
        }
        vh.txt_WorkPlace.setText(w.getWorkPlace() == null ? "-- Chưa cập nhật --" : w.getWorkPlace());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(w.getExpirationTime() != null) {
            String ExpirationTime = sdf.format(new Date(w.getExpirationTime()));
            vh.txt_ExpirationTime.setText(ExpirationTime);
        }
        else vh.txt_ExpirationTime.setText("null");

        if(w.getPostTime() != null) {
            String PostTime = sdf.format(new Date(w.getPostTime()));
            vh.txt_PostTime.setText(PostTime);
        }
        else vh.txt_PostTime.setText("null");

        Database.getData(Node.RECRUITERS + "/" + w.getUserId(), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Recruiter r = dataSnapshot.getValue(Recruiter.class);
                if(r == null){
                    vh.txt_CompanyName.setText("-- Chưa cập nhật --");
                    Picasso.with(context).load(R.drawable.ic_default_avatar).into(vh.imgv_Avatar);
                    return;
                }
                vh.txt_CompanyName.setText(r.getCompanyName() == null ? "-- Chưa cập nhật --" : r.getCompanyName());
                if (r.getAvatar() != null)
                    Picasso.with(context).load(r.getAvatar()).into(vh.imgv_Avatar);
                else
                    Picasso.with(context).load(R.drawable.ic_default_avatar).into(vh.imgv_Avatar);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
        vh.ln_WorkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt = (TextView) v.findViewById(R.id.txt_WorkInfoKey);
                Intent intent = new Intent(context, UVViewWorkInfoActivity.class);
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

    public void setOnFilterListener(OnFilterListener onFilterListener) {
        this.onFilterListener = onFilterListener;
    }

    public void FilterData(SearchWorkInfoSetting searchWorkInfoSetting) {
        mSetting = searchWorkInfoSetting;
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

    private class ItemFilter extends Filter {

        private class ItemSort implements Comparable<ItemSort> {
            private int Index;
            private int Rank;

            public ItemSort() {
            }

            public ItemSort(int index, int score) {
                Index = index;
                Rank = score;
            }

            public int getIndex() {
                return Index;
            }

            public void setIndex(int index) {
                Index = index;
            }

            public int getRank() {
                return Rank;
            }

            public void setRank(int score) {
                Rank = score;
            }

            @Override
            public int compareTo(@NonNull ItemSort o) {
                if (Rank < o.getRank()) return 1;
                if (Rank > o.getRank()) return -1;
                return 0;
            }
        }

        private void sortDataWithProfile() {
            Database.getData(Node.CANDIDATES + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid(), new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if(Sorted) return;
                    Sorted = true;
                    Candidate can = dataSnapshot.getValue(Candidate.class);
                    if (can == null) return;
                    List<ItemSort> lstSort = new ArrayList<>();
                    for (int i = 0; i < filteredData.size(); i++) {
                        int Rank = 0;
                        WorkInfo w = filteredData.get(i);
                        String Tags = w.getWorkDetail().getTitle() + "," + w.getTitlePost() + ","
                                + w.getWorkDetail().getCarrers() + "," + can.getCandidateDetail().getWorkPlaces()
                                + "," + w.getWorkDetail().getWorkTypes() + "," + w.getWorkDetail().getLevel()
                                + "," + w.getWorkDetail().getExperience() + "," + w.getWorkDetail().getSalary();
                        String[] arr = Tags.split(",");
                        for (String str : arr) {
                            //Lọc theo tags
                            if (can.getCandidateDetail().getTag().toUpperCase().contains(str.trim().toUpperCase()))
                                Rank += 50;
                            //Lọc theo ngành nghề
                            if (can.getCandidateDetail().getCareers().toUpperCase().contains(str.trim().toUpperCase()))
                                Rank += 30;
                            //Lọc theo nơi có thể làm việc
                            if (can.getCandidateDetail().getWorkPlaces().toUpperCase().contains(str.trim().toUpperCase()))
                                Rank += 5;
                            //Lọc theo loại công việc
                            if (can.getCandidateDetail().getWorkTypes().toUpperCase().contains(str.trim().toUpperCase()))
                                Rank += 5;
                            //Lọc theo trình độ
                            if (can.getCandidateDetail().getLevel().toUpperCase().contains(str.trim().toUpperCase()))
                                Rank += 3;
                            //Lọc theo kinh nghiệm
                            if (can.getCandidateDetail().getExperience().toUpperCase().contains(str.trim().toUpperCase()))
                                Rank += 2;
                            //Lọc theo lương
                            if (can.getCandidateDetail().getSalary().toUpperCase().contains(str.trim().toUpperCase()))
                                Rank += 5;
                        }
                        ItemSort item = new ItemSort(i, Rank);
                        lstSort.add(item);
                        Collections.sort(lstSort);
                        Collections.rotate(filteredData.subList(lstSort.indexOf(item), i + 1), 1);
                    }
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d("ABCDEF", "Query: '" + constraint.toString().trim().toUpperCase() + "'");
            String filterString = constraint.toString().trim().toUpperCase();
            String[] filterArr = filterString.split(" ");
            filteredData.clear();

            FilterResults filterResults = new FilterResults();

            if (filterString.length() > 0) {
                for (int i = 0; i < lstData.size(); i++) {
                    WorkInfo w = lstData.get(i);
                    for (String str : filterArr) {
                        str = str.trim();
                        if (str.isEmpty() || str == "") continue;
                        if (w.getTitlePost().toUpperCase().contains(str)
                                || w.getCompanyName().toUpperCase().contains(str)
                                || w.getWorkPlace().toUpperCase().contains(str)
                                || w.getWorkDetail().getCarrers().toUpperCase().contains(str)
                                || w.getWorkDetail().getWorkTypes().toUpperCase().contains(str)
                                || w.getWorkDetail().getSalary().toUpperCase().contains(str)
                                || w.getWorkDetail().getExperience().toUpperCase().contains(str)
                                || w.getWorkDetail().getLevel().toUpperCase().contains(str)) {
                            filteredData.add(w);
                            break;
                        }
                    }
                }
            } else {
                filteredData.addAll(lstData);
            }
            Sorted = false;
            sortDataWithProfile();
            filterResults.values = filteredData;
            filterResults.count = filteredData.size();

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (onFilterListener != null)
                onFilterListener.onFilter(filteredData.size());
            notifyDataSetChanged();
        }
    }

    public class WorkInfoViewHolder extends RecyclerView.ViewHolder {
        TextView txt_WorkInfoKey, txt_TitlePost, txt_CompanyName, txt_Career,
                txt_Salary, txt_WorkPlace, txt_ExpirationTime, txt_PostTime;
        CircleImageView imgv_Avatar;
        LinearLayout ln_WorkInfo;

        public WorkInfoViewHolder(View itemView) {
            super(itemView);
            ln_WorkInfo = (LinearLayout) itemView.findViewById(R.id.ln_WorkInfo);
            imgv_Avatar = (CircleImageView) itemView.findViewById(R.id.imgv_Avatar);

            txt_WorkInfoKey = (TextView) itemView.findViewById(R.id.txt_WorkInfoKey);
            txt_TitlePost = (TextView) itemView.findViewById(R.id.txt_TitlePost);
            txt_CompanyName = (TextView) itemView.findViewById(R.id.txt_CompanyName);
            txt_Career = (TextView) itemView.findViewById(R.id.txt_Career);
            txt_Salary = (TextView) itemView.findViewById(R.id.txt_Salary);
            txt_WorkPlace = (TextView) itemView.findViewById(R.id.txt_WorkPlace);
            txt_ExpirationTime = (TextView) itemView.findViewById(R.id.txt_ExpirationTime);
            txt_PostTime = (TextView) itemView.findViewById(R.id.txt_PostTime);
        }
    }
}

