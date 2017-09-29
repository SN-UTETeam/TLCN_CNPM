package com.spkt.nguyenducnguu.jobstore.Adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.Models.Career;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.List;

public class CareerListAdapter extends BaseAdapter {
    private List<Career> listData;
    private List<String> listDataSelected;
    private LayoutInflater layoutInflater;
    private Context context;

    public CareerListAdapter(Context aContext,  List<Career> listData, List<String> listDataSelected) {
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
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listData.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_career_layout, null);
            holder = new CareerListAdapter.ViewHolder();
            holder.img_Check = (ImageView) view.findViewById(R.id.img_Check);
            holder.txt_Name = (TextView) view.findViewById(R.id.txt_Name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.txt_Name.setText(this.listData.get(i).getName());
        if(listDataSelected.indexOf(this.listData.get(i).getName()) != -1){
            holder.img_Check.setVisibility(View.VISIBLE);
        }
        return view;
    }
    static class ViewHolder {
        ImageView img_Check;
        TextView txt_Name;
    }
}
