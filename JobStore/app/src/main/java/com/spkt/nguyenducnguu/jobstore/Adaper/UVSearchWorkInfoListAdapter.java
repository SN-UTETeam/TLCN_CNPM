package com.spkt.nguyenducnguu.jobstore.Adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnFilterListener;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UVSearchWorkInfoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private ItemFilter mFilter = new ItemFilter();
    private List<WorkInfo> lstData;
    private List<String> lstKey;
    public List<WorkInfo> filteredData;
    private List<String> filteredKey;
    private Context context;
    private OnFilterListener onFilterListener;

    public UVSearchWorkInfoListAdapter(List<WorkInfo> lstData, List<String> lstKey) {
        this.lstData = lstData;
        this.lstKey = lstKey;
        this.filteredData = lstData;
        this.filteredKey = lstKey;
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
        Log.d("CheckPosition", "Position: " + position + " - Size: " + filteredData.size());
        WorkInfo w = filteredData.get(position);
        vh.txt_WorkInfoKey.setText(filteredKey.get(position));
        vh.txt_TitlePost.setText(w.getTitlePost());
        vh.txt_Career.setText(w.getWorkDetail().getCarrers());
        vh.txt_Salary.setText(w.getWorkDetail().getSalary());
        vh.txt_WorkPlace.setText(w.getWorkPlace());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String ExpirationTime = sdf.format(new Date(w.getExpirationTime()));
        vh.txt_ExpirationTime.setText(ExpirationTime);

        Database.getData(Node.RECRUITERS + "/" + w.getUserId(), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Recruiter r = dataSnapshot.getValue(Recruiter.class);
                vh.txt_CompanyName.setText(r.getCompanyName());
                if(r.getAvatar() != null)
                    Picasso.with(context).load(r.getAvatar()).into(vh.imgv_Avatar);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

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
    public void setOnFilterListener(OnFilterListener onFilterListener)
    {
        this.onFilterListener = onFilterListener;
    }
    private class ItemFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toUpperCase();
            String[] filterArr = filterString.split(" ");

            FilterResults results = new FilterResults();
            final List<WorkInfo> lstWorkInfo = lstData;
            int count = lstWorkInfo.size();
            List<WorkInfo> nlist = new ArrayList<WorkInfo>();

            if(filterString.length() > 0){
                for(int i=0; i < count; i++){
                    WorkInfo w = lstWorkInfo.get(i);
                    for (String str : filterArr) {
                        if(str.trim().isEmpty() || str.trim() == "") continue;
                        if (w.getTitlePost().toUpperCase().contains(str.trim())
                                || w.getCompanyName().toUpperCase().contains(str.trim()))
                        {
                            nlist.add(w);
                            filteredKey.add(lstKey.get(i));
                            break;
                        }
                    }
                }
            } else {
                nlist = lstWorkInfo;
            }
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<WorkInfo>) results.values;
            onFilterListener.onFilter(filteredData.size());
            notifyDataSetChanged();
        }
    }
    public class WorkInfoViewHolder extends RecyclerView.ViewHolder {
        TextView txt_WorkInfoKey, txt_TitlePost, txt_CompanyName, txt_Career, txt_Salary, txt_WorkPlace, txt_ExpirationTime;
        ImageView imgv_Avatar;

        public WorkInfoViewHolder(View itemView) {
            super(itemView);
            imgv_Avatar = (ImageView) itemView.findViewById(R.id.imgv_Avatar);

            txt_WorkInfoKey = (TextView) itemView.findViewById(R.id.txt_WorkInfoKey);
            txt_TitlePost = (TextView) itemView.findViewById(R.id.txt_TitlePost);
            txt_CompanyName = (TextView) itemView.findViewById(R.id.txt_CompanyName);
            txt_Career = (TextView) itemView.findViewById(R.id.txt_Career);
            txt_Salary = (TextView) itemView.findViewById(R.id.txt_Salary);
            txt_WorkPlace = (TextView) itemView.findViewById(R.id.txt_WorkPlace);
            txt_ExpirationTime = (TextView) itemView.findViewById(R.id.txt_ExpirationTime);
        }
    }
}

