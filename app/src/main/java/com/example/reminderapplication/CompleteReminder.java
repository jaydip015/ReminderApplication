package com.example.reminderapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

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
                Log.d("tag", String.valueOf(intent.getIntExtra("id",0)));
                dao.alaramComplete(intent.getIntExtra("id",0));
            }
        }).start();
        stopSelf();
        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
         return null;
    }
}
