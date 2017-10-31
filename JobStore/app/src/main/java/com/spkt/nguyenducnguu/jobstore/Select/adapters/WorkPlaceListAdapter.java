package com.spkt.nguyenducnguu.jobstore.Select.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.Models.WorkPlace;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.List;

public class WorkPlaceListAdapter extends BaseAdapter {
    private List<WorkPlace> listData;
    private List<String> listDataSelected;
    private LayoutInflater layoutInflater;
    private Context context;

    public WorkPlaceListAdapter(Context aContext,  List<WorkPlace> listData,  List<String> listDataSelected) {
        this.context = aContext;
        this.listData = listData;
        this.listDataSelected = listDataSelected;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_workplace_layout, null);
            holder = new ViewHolder();
            holder.img_Check = (ImageView) convertView.findViewById(R.id.img_Check);
            holder.txt_Name = (TextView) convertView.findViewById(R.id.txt_Name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt_Name.setText(this.listData.get(position).getName());
        if(listDataSelected.indexOf(this.listData.get(position).getName()) != -1){
            holder.img_Check.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView img_Check;
        TextView txt_Name;
    }
}
