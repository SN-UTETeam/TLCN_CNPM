package com.spkt.nguyenducnguu.jobstore.Admin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Admin.activities.AdminViewWorkInfoActivity;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnFilterListener;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.AdminFilterWorkInfoSetting;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManageWorkInfoAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private ItemFilter mFilter = new ItemFilter();
    private List<WorkInfo> lstData;
    private List<WorkInfo> filteredData = new ArrayList<WorkInfo>();
    private Context context;
    private OnFilterListener onFilterListener;
    private AdminFilterWorkInfoSetting mSetting;

    public ManageWorkInfoAdapter(List<WorkInfo> lstData) {
        mSetting = new AdminFilterWorkInfoSetting();
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
                if(r == null) {
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
                Intent intent = new Intent(context, AdminViewWorkInfoActivity.class);
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

    public void FilterData(AdminFilterWorkInfoSetting adminFilterWorkInfoSetting) {
        mSetting = adminFilterWorkInfoSetting;
        String Query = "";
        Query += mSetting.getWorkTypes().toString().substring(1, mSetting.getWorkTypes().toString().length() - 1);
        Query += " " + mSetting.getWorkPlaces().toString().substring(1, mSetting.getWorkPlaces().toString().length() - 1);
        Query += " " + mSetting.getCareers().toString().substring(1, mSetting.getCareers().toString().length() - 1);
        Query += " " + mSetting.getQuery();

        mFilter.filter(Query);
    }

    private class ItemFilter extends Filter {
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
        TextView txt_WorkInfoKey, txt_TitlePost, txt_CompanyName, txt_Career, txt_Salary,
                txt_WorkPlace, txt_ExpirationTime, txt_PostTime;
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