package com.spkt.nguyenducnguu.jobstore.Admin.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.Admin.activities.AdminViewCandidateAccountActivity;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Const.Status;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnFilterListener;
import com.spkt.nguyenducnguu.jobstore.Models.Block;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.Models.AdminFilterAccountSetting;
import com.spkt.nguyenducnguu.jobstore.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManageAccountCandidateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private static final int RECRUITER = 0;
    private static final int CANDIDATE = 1;
    private ItemFilter mFilter = new ItemFilter();
    private List<Candidate> lstData;
    private List<Candidate> filteredData = new ArrayList<>();
    private Context context;
    private OnFilterListener onFilterListener;
    private AdminFilterAccountSetting mSetting;

    public ManageAccountCandidateAdapter(List<Candidate> lstData) {
        mSetting = new AdminFilterAccountSetting();
        this.lstData = lstData;
        this.filteredData.addAll(lstData);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v2 = inflater.inflate(R.layout.list_item_account_candidate_layout, parent, false);
        viewHolder = new ViewHolderCandidate(v2);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolderCandidate vh2 = (ViewHolderCandidate) viewHolder;
        configureViewHolderCandiate(vh2, position);
    }

    private void configureViewHolderCandiate(ViewHolderCandidate vh2, int position) {
        final Candidate can = lstData.get(position);
        if (can != null) {

            if(can.getStatus() == Status.BLOCKED && can.getBlocked().isActive())
            {
                can.setStatus(Status.ACTIVE);
                can.setBlocked(null);
                Database.updateData(Node.CANDIDATES, can.getKey(), can);
            }

            vh2.txt_Key.setText(can.getKey() == null ? "" : can.getKey());
            vh2.txt_FullName.setText(can.getFullName() == null ? "-- Chưa cập nhật --" : can.getFullName());
            if(can.getCandidateDetail() != null)
                vh2.txt_Tag.setText(can.getCandidateDetail().getTag() == null ? "-- Chưa cập nhật --" : can.getCandidateDetail().getTag());
            else vh2.txt_Tag.setText("-- Chưa cập nhật --");
            vh2.txt_Email.setText(can.getEmail() == null ? "-- Chưa cập nhật --" : can.getEmail());
            if(can.getAddress() != null)
                vh2.txt_Address.setText(can.getAddress().getAddressStr() == null ? "-- Chưa cập nhật --" : can.getAddress().getAddressStr());
            else vh2.txt_Address.setText("-- Chưa cập nhật --");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if(can.getCreateAt() != null) {
                String CreateAt = sdf.format(new Date(can.getCreateAt()));
                vh2.txt_CreateAt.setText(CreateAt);
            }
            else vh2.txt_CreateAt.setText("null");

            if (can.getStatus() == Status.ACTIVE)
                vh2.txt_Status.setText("Đang hoạt động");
            else if (can.getStatus() == Status.BLOCKED)
            {
                vh2.txt_Status.setText("Đã khóa vĩnh viễn");
                if(!can.getBlocked().isPermanent())
                    vh2.txt_Status.setText("Mở khóa sau " + Block.getDistanceTime(can.getBlocked().getFinishDate()));
            }
            else if (can.getStatus() == Status.PENDING)
                vh2.txt_Status.setText("Đang chờ duyệt");
            else vh2.txt_Status.setText("null");

            if (can.getAvatar() != null) {
                Picasso.with(context).load(can.getAvatar()).into(vh2.imgv_Avatar);
            } else Picasso.with(context).load(R.drawable.ic_default_avatar).into(vh2.imgv_Avatar);

            vh2.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    TextView txt = (TextView) view.findViewById(R.id.txt_Key);
                    Intent intent = new Intent(context, AdminViewCandidateAccountActivity.class);
                    intent.putExtra("Key", txt.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
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

    public void FilterData(AdminFilterAccountSetting adminFilterAccountSetting) {
        mSetting = adminFilterAccountSetting;
        mFilter.filter(mSetting.getQuery());
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().trim().toUpperCase();
            String[] filterArr = filterString.split(" ");
            filteredData.clear();

            FilterResults filterResults = new FilterResults();

            if (filterString.length() > 0) {
                for (int i = 0; i < lstData.size(); i++) {
                    Candidate can = lstData.get(i);
                    for (String str : filterArr) {
                        str = str.trim();
                        if (str.isEmpty() || str == "") continue;
                        if (can.getFullName().toUpperCase().contains(str)
                                || can.getEmail().toUpperCase().contains(str)
                                || can.getCandidateDetail().getTag().toUpperCase().contains(str)) {
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
            if (onFilterListener != null)
                onFilterListener.onFilter(filteredData.size());
            notifyDataSetChanged();
        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    public static class ViewHolderCandidate extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public TextView txt_Key;
        public TextView txt_FullName;
        public TextView txt_Tag;
        public TextView txt_Email;
        public TextView txt_Address;
        public TextView txt_CreateAt;
        public TextView txt_Status;
        public LinearLayout ln_Candidate;
        public CircleImageView imgv_Avatar;
        private ItemClickListener clickListener;

        public ViewHolderCandidate(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            txt_Key = (TextView) itemView.findViewById(R.id.txt_Key);
            txt_FullName = (TextView) itemView.findViewById(R.id.txt_FullName);
            txt_Tag = (TextView) itemView.findViewById(R.id.txt_Tag);
            txt_Email = (TextView) itemView.findViewById(R.id.txt_Email);
            txt_Address = (TextView) itemView.findViewById(R.id.txt_Address);
            txt_CreateAt = (TextView) itemView.findViewById(R.id.txt_CreateAt);
            txt_Status = (TextView) itemView.findViewById(R.id.txt_Status);
            imgv_Avatar = (CircleImageView) itemView.findViewById(R.id.imgv_Avatar);
            ln_Candidate = (LinearLayout) itemView.findViewById(R.id.ln_Candidate);

            itemView.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}
