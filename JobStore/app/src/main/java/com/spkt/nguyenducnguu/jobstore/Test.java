package com.spkt.nguyenducnguu.jobstore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Parameter;
import com.spkt.nguyenducnguu.jobstore.Models.WorkInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by TranAnhSon on 10/5/2017.
 */

public class Test extends AppCompatActivity {
    TextView tv_countWorkInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        tv_countWorkInfo = (TextView) findViewById(R.id.tv_countWorkInfo);

        Database.getData("WorkInfos", new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<WorkInfo> lstWorkInfo = new ArrayList<WorkInfo>();
                for (DataSnapshot mdata : dataSnapshot.getChildren()) {
                    WorkInfo w = mdata.getValue(WorkInfo.class);
                    w.setKey(mdata.getKey());
                    lstWorkInfo.add(w);
                    Collections.sort(lstWorkInfo);
                }
                tv_countWorkInfo.setText(lstWorkInfo.size() + " WorkInfo");
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, new Parameter("workDetail/salary", "5-7 triệu"));
    }
}
