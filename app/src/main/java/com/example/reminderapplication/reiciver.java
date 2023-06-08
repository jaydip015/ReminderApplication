package com.example.reminderapplication;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.reminderapplication.Activities.MainActivity;
import com.example.reminderapplication.Servicies.CompleteReminder;

public class reiciver extends BroadcastReceiver {
    int id;
    Context c;


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent openscreen = new Intent(context, MainActivity.class);
        openscreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, openscreen, PendingIntent.FLAG_IMMUTABLE);


        id=intent.getIntExtra("id",0);
        Intent i=new Intent(context, CompleteReminder.class);
        i.putExtra("id",id);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(i);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "testing").
                setSmallIcon(R.drawable.ic_launcher_background).
                setContentTitle(intent.getStringExtra("notificationname")).setContentText("You have Report scheduled ").setAutoCancel(true).setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        int notificationid=1;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if( ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)== PackageManager.PERMISSION_GRANTED){
                notificationManagerCompat.notify(notificationid,builder.build());
            }
        }else {
            notificationManagerCompat.notify(notificationid,builder.build());
        }
    }
}
