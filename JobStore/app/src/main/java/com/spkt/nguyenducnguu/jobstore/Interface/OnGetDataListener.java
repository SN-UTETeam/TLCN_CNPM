package com.spkt.nguyenducnguu.jobstore.Interface;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface OnGetDataListener {
    public void onSuccess(DataSnapshot dataSnapshot);
    public void onFailed(DatabaseError databaseError);
}
