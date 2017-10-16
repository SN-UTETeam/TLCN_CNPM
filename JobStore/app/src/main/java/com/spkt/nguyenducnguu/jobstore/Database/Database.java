package com.spkt.nguyenducnguu.jobstore.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Parameter;

public class Database {

    public static FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
    public Database() {

    }

    public static void getData(String node, final OnGetDataListener listener, Parameter... parameters) {
        Query query = mdatabase.getReference(node);

        if(parameters != null && parameters.length > 0)
        {
            for (Parameter param : parameters) {
                query = query.orderByChild(param.getName()).equalTo(param.getValue());
            }
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }
    public static void addData(String node, Object object)
    {
        mdatabase.getReference(node).push().setValue(object);
    }
    public static void updateData(String node, String key, Object object)
    {
        mdatabase.getReference(node).child(key).setValue(object);
    }
}