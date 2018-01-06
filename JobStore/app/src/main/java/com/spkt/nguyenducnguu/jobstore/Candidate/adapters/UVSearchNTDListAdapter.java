package com.spkt.nguyenducnguu.jobstore.Candidate.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.spkt.nguyenducnguu.jobstore.Models.Follow;
import com.spkt.nguyenducnguu.jobstore.Models.Parameter;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.Models.SearchWorkInfoSetting;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.Candidate.activities.UVSeeDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UVSearchNTDListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private ItemFilter mFilter = new ItemFilter();
    private List<Recruiter> lstData;
    private List<Recruiter> filteredData = new ArrayList<Recruiter>();
    private Context context;
    private OnFilterListener onFilterListener;
    private SearchWorkInfoSetting mSetting;

    public UVSearchNTDListAdapter(List<Recruiter> lstData) {
        mSetting = new SearchWorkInfoSetting();
        this.lstData = lstData;
        this.filteredData.addAll(lstData);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_recruiter_layout, parent, false);
        viewHolder = new RecruiterViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecruiterViewHolder vh = (RecruiterViewHolder) holder;
        final Recruiter r = filteredData.get(position);
        if(r == null) return;
        vh.txt_Key.setText(r.getKey() == null ? "" : r.getKey());
        vh.txt_CompanyName.setText(r.getCompanyName() == null ? "-- Chưa cập nhật --" : r.getCompanyName());
        vh.txt_Email.setText(r.getEmail() == null ? "-- Chưa cập nhật --" : r.getEmail());
        vh.txt_Website.setText(r.getWebsite() == null ? "-- Chưa cập nhật --" : r.getWebsite());
        if(r.getAddress() != null)
            vh.txt_Address.setText(r.getAddress().getAddressStr() == null ? "-- Chưa cập nhật --" : r.getAddress().getAddressStr());
        else vh.txt_Address.setText("-- Chưa cập nhật --");

        if(r.getAvatar() != null)
            Picasso.with(context).load(r.getAvatar()).into(vh.imgv_Avatar);
        else
            Picasso.with(context).load(R.drawable.ic_default_avatar).into(vh.imgv_Avatar);

        vh.ln_Recruiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(r);
            }
        });
    }

    private void ShowPopup(final Recruiter r){
        if(r == null) return;
        final Dialog dialogFollow;
        CircleImageView imgv_Avatar;
        final TextView txtClose, txt_CompanyName, txt_Email, txt_Website, txt_Address,
                txt_NumberFollow, txt_NunberApply, txt_NumberPost;
        final Button btnFollow, btnSeeDetail;
        dialogFollow = new Dialog(context);
        dialogFollow.setContentView(R.layout.dialog_see_ntd_detail);

        imgv_Avatar = (CircleImageView) dialogFollow.findViewById(R.id.imgv_Avatar);
        txtClose = (TextView) dialogFollow.findViewById(R.id.txtClose);
        txt_CompanyName = (TextView) dialogFollow.findViewById(R.id.txt_CompanyName);
        txt_Email = (TextView) dialogFollow.findViewById(R.id.txt_Email);
        txt_Website = (TextView) dialogFollow.findViewById(R.id.txt_Website);
        txt_Address = (TextView) dialogFollow.findViewById(R.id.txt_Address);
        txt_NumberFollow = (TextView) dialogFollow.findViewById(R.id.txt_NumberFollow);
        txt_NumberPost = (TextView) dialogFollow.findViewById(R.id.txt_NumberPost);
        txt_NunberApply = (TextView) dialogFollow.findViewById(R.id.txt_NunberApply);
        btnFollow = (Button) dialogFollow.findViewById(R.id.btnFollow);
        btnSeeDetail = (Button) dialogFollow.findViewById(R.id.btnSeeDetail);

        txt_CompanyName.setText(r.getCompanyName() == null ? "-- Chưa cập nhật --" : r.getCompanyName());
        txt_Email.setText(r.getEmail() == null ? "-- Chưa cập nhật --" : r.getEmail());
        txt_Website.setText(r.getWebsite() == null ? "-- Chưa cập nhật --" : r.getWebsite());
        if(r.getAddress() != null)
            txt_Address.setText(r.getAddress().getAddressStr() == null ? "-- Chưa cập nhật --" : r.getAddress().getAddressStr());
        else txt_Address.setText("-- Chưa cập nhật --");
        if(r.getFollows() != null)
            txt_NumberFollow.setText(r.getFollows().size() + "");
        else txt_NumberFollow.setText("-- Chưa cập nhật --");

        Database.getData(Node.WORKINFOS, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int numberPost = 0, numberApply = 0;
                for(DataSnapshot mdata : dataSnapshot.getChildren())
                {
                    WorkInfo w = mdata.getValue(WorkInfo.class);
                    if(w == null) continue;
                    numberPost++;
                    numberApply += w.getApplies().size();
                }
                txt_NumberPost.setText(numberPost + "");
                txt_NunberApply.setText(numberApply + "");
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, new Parameter("userId", r.getKey()));

        if(r.getAvatar() != null)
            Picasso.with(dialogFollow.getContext()).load(r.getAvatar()).into(imgv_Avatar);
        else
            Picasso.with(dialogFollow.getContext()).load(R.drawable.ic_default_avatar).into(imgv_Avatar);

        if(r.checkFollow(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            btnFollow.setText("Unfollow");
        else btnFollow.setText("Follow me");

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFollow.dismiss();
            }
        });
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r == null) return;

                if(btnFollow.getText().equals("Follow me"))
                {
                    Follow p = new Follow(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    r.getFollows().put(FirebaseAuth.getInstance().getCurrentUser().getUid(), p);
                    btnFollow.setText("Unfollow");
                }
                else if(btnFollow.getText().equals("Unfollow"))
                {
                    r.getFollows().remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    btnFollow.setText("Follow me");
                }
                txt_NumberFollow.setText(r.getFollows().size() + "");
                Database.updateData(Node.RECRUITERS, r.getKey(), r);
            }
        });
        btnSeeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r == null) return;
                Intent intent = new Intent(context, UVSeeDetailActivity.class);
                intent.putExtra("Key", r.getKey());
                context.startActivity(intent);
            }
        });
        dialogFollow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFollow.show();
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

    public void FilterData(SearchWorkInfoSetting searchSetting){
        mSetting = searchSetting;
        String Query = "";
        Query += " " + mSetting.getCareers().toString().substring(1, mSetting.getCareers().toString().length() - 1);
        Query += " " + mSetting.getQuery();

        mFilter.filter(Query.trim());
    }

    private class ItemFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d("ABCDEF", "Query: '" + constraint.toString().trim().toUpperCase() + "'");
            String filterString = constraint.toString().trim().toUpperCase();
            String[] filterArr = filterString.split(" ");
            filteredData.clear();

            FilterResults filterResults = new FilterResults();

            if(filterString.length() > 0){
                for(int i=0; i < lstData.size(); i++){
                    Recruiter r = lstData.get(i);
                    for (String str : filterArr) {
                        str = str.trim();
                        if(str.isEmpty() || str == "") continue;
                        if (r.getCompanyName().toUpperCase().contains(str) || r.getEmail().toUpperCase().contains(str)
                                || r.getWebsite().toUpperCase().contains(str)
                                || r.getAddress().getAddressStr().toUpperCase().contains(str))
                        {
                            filteredData.add(r);
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

        private void checkCareer(final String UserId, final String str)
        {
            Database.getData(Node.WORKINFOS, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    for(DataSnapshot mdata : dataSnapshot.getChildren())
                    {
                        WorkInfo w = mdata.getValue(WorkInfo.class);
                        if(w != null && w.getWorkDetail().getCarrers().contains(str))
                        {

                        }
                        else ;
                    }
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            }, new Parameter("UserId", UserId));
        }
    }

    public class RecruiterViewHolder extends RecyclerView.ViewHolder {
        TextView txt_Key, txt_CompanyName, txt_Email, txt_Website, txt_Address;
        CircleImageView imgv_Avatar;
        LinearLayout ln_Recruiter;

        public RecruiterViewHolder(View itemView) {
            super(itemView);
            ln_Recruiter = (LinearLayout) itemView.findViewById(R.id.ln_Recruiter);
            imgv_Avatar = (CircleImageView) itemView.findViewById(R.id.imgv_Avatar);

            txt_Key = (TextView) itemView.findViewById(R.id.txt_Key);
            txt_CompanyName = (TextView) itemView.findViewById(R.id.txt_CompanyName);
            txt_Email = (TextView) itemView.findViewById(R.id.txt_Email);
            txt_Website = (TextView) itemView.findViewById(R.id.txt_Website);
            txt_Address = (TextView) itemView.findViewById(R.id.txt_Address);
        }
    }
}