package com.spkt.nguyenducnguu.jobstore.UV.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;
import com.spkt.nguyenducnguu.jobstore.R;

import java.util.List;

/**
 * Created by TranAnhSon on 10/23/2017.
 */

public class UVRecyclerViewNTDAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<WorkInfo> listData;
    private List<String> lstKey;
    private Context context;

    public UVRecyclerViewNTDAdapter(List<WorkInfo> listData, List<String> lstKey) {
        this.listData = listData;
        this.lstKey = lstKey;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v1 = inflater.inflate(R.layout.list_item_uvmain_layout, parent, false);
        viewHolder = new RCVListNTDViewHolder(v1);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RCVListNTDViewHolder vh = (RCVListNTDViewHolder) holder;
        WorkInfo workInfo = listData.get(position);
        vh.txt_Key.setText(lstKey.get(position));
        vh.txt_CompanyName.setText(workInfo.getCompanyName());
        vh.txt_Title.setText(workInfo.getTitlePost());
        vh.txt_WorkPlace.setText(workInfo.getWorkPlace());
        vh.txt_Salary.setText(workInfo.getWorkDetail().getSalary());

        vh.icon_favorite.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
    }
    public class RCVListNTDViewHolder extends RecyclerView.ViewHolder{
        ImageView img_Avatar;
        TextView txt_Title, txt_CompanyName, txt_WorkPlace, txt_Key, txt_Salary, icon_favorite;
        public RCVListNTDViewHolder(View itemView){
            super(itemView);
            txt_Key = (TextView) itemView.findViewById(R.id.txt_Key);

            img_Avatar = (ImageView) itemView.findViewById(R.id.img_Avatar);
            txt_CompanyName = (TextView) itemView.findViewById(R.id.txt_CompanyName);
            txt_Title = (TextView) itemView.findViewById(R.id.txt_Title);
            txt_WorkPlace = (TextView) itemView.findViewById(R.id.txt_WorkPlace);
            txt_Salary = (TextView) itemView.findViewById(R.id.txt_Salary);

            //Để icon mặc định là: android:text="@string/fa_heart_non_selected"
            //Khi click vào: ndroid:text="@string/fa_heart_selected" => Dùng để lưu thông tin tuyển dụng vào Việc đã lưu
            icon_favorite = (TextView) itemView.findViewById(R.id.icon_favorite);

        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

}