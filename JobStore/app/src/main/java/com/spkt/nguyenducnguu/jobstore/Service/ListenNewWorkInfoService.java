package com.spkt.nguyenducnguu.jobstore.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Models.Notification;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.UV.activities.UVViewNotificationActivity;
import com.spkt.nguyenducnguu.jobstore.UV.activities.UVViewWorkInfoActivity;

import java.util.Random;

public class ListenNewWorkInfoService extends Service {

    FirebaseAuth auth = null;
    FirebaseDatabase db = null;
    DatabaseReference refNotification = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            auth = FirebaseAuth.getInstance();
            db = FirebaseDatabase.getInstance();
            refNotification = db.getReference(Node.CANDIDATES + "/" + auth.getCurrentUser().getUid() + "/notifications");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(refNotification != null)
        {
            refNotification.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Notification notification = dataSnapshot.getValue(Notification.class);
                    if(notification.getStatus() == Notification.NOTIFY)
                    {
                        showNotification(dataSnapshot.getKey(), notification);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else Toast.makeText(getBaseContext(), "refNotification is null", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification(String key, Notification notification) {
        Intent intent;
        if (notification.getWorkInfoKey() != null && !notification.getWorkInfoKey().isEmpty()
                && !notification.getWorkInfoKey().equals("")) {
            intent = new Intent(getBaseContext(), UVViewWorkInfoActivity.class);
            intent.putExtra("Key", notification.getWorkInfoKey());
        }
        else
        {
            intent = new Intent(getBaseContext(), UVViewNotificationActivity.class);
            intent.putExtra("Key", key);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(android.app.Notification.DEFAULT_ALL)
                .setTicker("JobStore")
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getContent())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        int randomInt = new Random().nextInt(9999 - 1) + 1;
        manager.notify(randomInt, builder.build());
    }

    public ListenNewWorkInfoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
