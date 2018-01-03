package com.spkt.nguyenducnguu.jobstore.Database;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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

    public static void addData(String node, Object object) {
        mdatabase.getReference(node).push().setValue(object);
    }

    public static void addDataWithKey(String node, String Key, Object object) {
        mdatabase.getReference(node).child(Key).setValue(object);
    }

    public static void updateData(String node, String key, Object object) {
        mdatabase.getReference(node).child(key).setValue(object);
    }

    public static void deleteData(String node, String key) {
        mdatabase.getReference(node).child(key).removeValue();
    }

    public static void deleteFileFromUrl(String Url) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(Url);
        storageReference.delete();
    }
}
