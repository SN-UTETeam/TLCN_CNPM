package com.spkt.nguyenducnguu.jobstore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by TranAnhSon on 10/5/2017.
 */

public class Test extends AppCompatActivity {
    TextView tv_countWorkInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        // sample code snippet to set the text content on the ExpandableTextView
       /* ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view)
                .findViewById(R.id.expand_text_view);*/

        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        //expTv1.setText(getString(R.string.dummy_text1));
        /*tv_countWorkInfo = (TextView) findViewById(R.id.tv_countWorkInfo);

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
        }, new Parameter("workDetail/salary", "5-7 triệu"));*/
    }
}
