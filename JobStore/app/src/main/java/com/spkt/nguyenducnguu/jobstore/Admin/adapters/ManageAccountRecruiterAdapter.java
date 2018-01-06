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

import com.spkt.nguyenducnguu.jobstore.Admin.activities.AdminViewRecruiterAccountActivity;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Const.Status;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnFilterListener;
import com.spkt.nguyenducnguu.jobstore.Models.Block;
import com.spkt.nguyenducnguu.jobstore.Models.AdminFilterAccountSetting;
import com.spkt.nguyenducnguu.jobstore.Models.Recruiter;
import com.spkt.nguyenducnguu.jobstore.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManageAccountRecruiterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private ItemFilter mFilter = new ItemFilter();
    private List<Recruiter> lstData;
    private List<Recruiter> filteredData = new ArrayList<>();
    private Context context;
    private OnFilterListener onFilterListener;
    private AdminFilterAccountSetting mSetting;

    public ManageAccountRecruiterAdapter(List<Recruiter> lstData) {
        mSetting = new AdminFilterAccountSetting();
        this.lstData = lstData;
        this.filteredData.addAll(lstData);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v1 = inflater.inflate(R.layout.list_item_account_recruiter_layout, parent, false);
        viewHolder = new ViewHolderRecruiter(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolderRecruiter vh1 = (ViewHolderRecruiter) viewHolder;
        configureViewHolderRecruiter(vh1, position);
    }

    private void configureViewHolderRecruiter(ViewHolderRecruiter vh1, int position) {
        final Recruiter r = lstData.get(position);
        if (r != null) {

            if(r.getStatus() == Status.BLOCKED && r.getBlocked().isActive())
            {
                r.setStatus(Status.ACTIVE);
                r.setBlocked(null);
                Database.updateData(Node.RECRUITERS, r.getKey(), r);
            }

            vh1.txt_Key.setText(r.getKey() == null ? "" : r.getKey());
            vh1.txt_CompanyName.setText(r.getCompanyName() == null ? "-- Chưa cập nhật --" : r.getCompanyName());
            vh1.txt_Email.setText(r.getEmail() == null ? "-- Chưa cập nhật --" : r.getEmail());
            vh1.txt_Website.setText(r.getWebsite() == null ? "-- Chưa cập nhật --" : r.getWebsite());
            if(r.getAddress() != null)
                vh1.txt_Address.setText(r.getAddress().getAddressStr() == null ? "-- Chưa cập nhật --" : r.getAddress().getAddressStr());
            else vh1.txt_Address.setText("-- Chưa cập nhật --");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if(r.getCreateAt() != null) {
                String CreateAt = sdf.format(new Date(r.getCreateAt()));
                vh1.txt_CreateAt.setText(CreateAt);
            }
            else vh1.txt_CreateAt.setText("null");

            if (r.getStatus() == Status.ACTIVE)
                vh1.txt_Status.setText("Đang hoạt động");
            else if (r.getStatus() == Status.BLOCKED)
            {
                vh1.txt_Status.setText("Đã khóa vĩnh viễn");
                if(!r.getBlocked().isPermanent())
                    vh1.txt_Status.setText("Mở khóa sau " + Block.getDistanceTime(r.getBlocked().getFinishDate()));
            }
            else if (r.getStatus() == Status.PENDING)
                vh1.txt_Status.setText("Đang chờ duyệt");
            else vh1.txt_Status.setText("null");

            if (r.getAvatar() != null) {
                Picasso.with(context).load(r.getAvatar()).into(vh1.imgv_Avatar);
            } else Picasso.with(context).load(R.drawable.ic_default_avatar).into(vh1.imgv_Avatar);

            vh1.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    TextView txt = (TextView) view.findViewById(R.id.txt_Key);
                    Intent intent = new Intent(context, AdminViewRecruiterAccountActivity.class);
                    intent.putExtra("Key", r.getKey());
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
                    Recruiter r = lstData.get(i);
                    for (String str : filterArr) {
                        str = str.trim();
                        if (str.isEmpty() || str == "") continue;
                        if (r.getCompanyName().toUpperCase().contains(str)
                                || r.getEmail().toUpperCase().contains(str)
                                || r.getWebsite().toUpperCase().contains(str)) {
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
            if (onFilterListener != null)
                onFilterListener.onFilter(filteredData.size());
            notifyDataSetChanged();
        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    public static class ViewHolderRecruiter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView txt_Key;
        public TextView txt_CompanyName;
        public TextView txt_Email;
        public TextView txt_Website;
        public TextView txt_Address;
        public TextView txt_CreateAt;
        public TextView txt_Status;
        public CircleImageView imgv_Avatar;
        public LinearLayout ln_Recruiter;
        private ItemClickListener clickListener;

        public ViewHolderRecruiter(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            txt_Key = (TextView) itemView.findViewById(R.id.txt_Key);
            txt_CompanyName = (TextView) itemView.findViewById(R.id.txt_CompanyName);
            txt_Email = (TextView) itemView.findViewById(R.id.txt_Email);
            txt_Website = (TextView) itemView.findViewById(R.id.txt_Website);
            txt_Address = (TextView) itemView.findViewById(R.id.txt_Address);
            txt_CreateAt = (TextView) itemView.findViewById(R.id.txt_CreateAt);
            txt_Status = (TextView) itemView.findViewById(R.id.txt_Status);
            imgv_Avatar = (CircleImageView) itemView.findViewById(R.id.imgv_Avatar);
            ln_Recruiter = (LinearLayout) itemView.findViewById(R.id.ln_Recruiter);
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
