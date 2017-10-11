package com.spkt.nguyenducnguu.jobstore.Adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WorkInfoListAdapter extends BaseAdapter {
    private List<WorkInfo> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public WorkInfoListAdapter(Context aContext,  List<WorkInfo> listData) {
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
            view = layoutInflater.inflate(R.layout.list_item_post_layout, null);
            holder = new WorkInfoListAdapter.ViewHolder();
            holder.txt_Key = (TextView) view.findViewById(R.id.txt_Key);
            holder.txt_Title = (TextView) view.findViewById(R.id.txt_Title);
            holder.txt_WorkPlace = (TextView) view.findViewById(R.id.txt_WorkPlace);
            holder.txt_Views = (TextView) view.findViewById(R.id.txt_Views);
            holder.txt_NumberApply = (TextView) view.findViewById(R.id.txt_NumberApply);
            holder.txt_ExpirationTime = (TextView) view.findViewById(R.id.txt_ExpirationTime);

            holder.txt_icon1 = (TextView) view.findViewById(R.id.txt_icon1);
            holder.txt_icon2 = (TextView) view.findViewById(R.id.txt_icon2);
            holder.txt_icon3 = (TextView) view.findViewById(R.id.txt_icon3);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.txt_Key.setText(this.listData.get(i).getKey());
        holder.txt_Title.setText(this.listData.get(i).getTitlePost());
        holder.txt_WorkPlace.setText("Tại: " + this.listData.get(i).getWorkPlace());
        holder.txt_Views.setText(this.listData.get(i).getViews() + "");
        holder.txt_NumberApply.setText(this.listData.get(i).getApplies().size() + "");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        holder.txt_ExpirationTime.setText(sdf.format(new Date(this.listData.get(i).getExpirationTime())));

        holder.txt_icon1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
        holder.txt_icon2.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
        holder.txt_icon3.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));

        return view;
    }
    static class ViewHolder {
        ImageView imgv_Avatar;
        TextView txt_Title;
        TextView txt_WorkPlace;
        TextView txt_Views;
        TextView txt_NumberApply;
        TextView txt_ExpirationTime;
        TextView txt_Key;
        TextView txt_icon1,txt_icon2,txt_icon3;
    }
}
