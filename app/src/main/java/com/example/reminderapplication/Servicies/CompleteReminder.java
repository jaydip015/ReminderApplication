package com.example.reminderapplication.Servicies;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.reminderapplication.AppWidget;
import com.example.reminderapplication.Activities.MainActivity;
import com.example.reminderapplication.RDB.AppDb;
import com.example.reminderapplication.RDB.DAO;

public class CompleteReminder extends Service {
    AppDb db;
    DAO dao;

    @Override
    public void onCreate() {
        super.onCreate();
        db= AppDb.getInstance(this);
        dao=db.dao();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                UpdateWidget();
                dao.alaramComplete(intent.getIntExtra("id",0));
            }
        }).start();
        Intent i=new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        stopSelf();
        return START_NOT_STICKY;
    }

    public void  UpdateWidget(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        Intent updateIntent = new Intent(this, AppWidget.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, AppWidget.class));
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        sendBroadcast(updateIntent);


    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
         return null;
    }
}
