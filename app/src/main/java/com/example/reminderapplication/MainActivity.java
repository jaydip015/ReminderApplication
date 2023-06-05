package com.example.reminderapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.reminderapplication.Adapter.RVAdapter;
import com.example.reminderapplication.RDB.AppDb;
import com.example.reminderapplication.RDB.DAO;
import com.example.reminderapplication.RDB.entity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView ReminderList;
    FloatingActionButton addReminder;
    List<entity> Data=new ArrayList<>();
    RVAdapter adapter;
    AppDb db;
    DAO dao;
    AlarmManager manager;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestpermission();
//        db=AppDb.getInstance(getApplicationContext());
        db= Room.databaseBuilder(getApplicationContext(),
                        AppDb.class, "Reminder").allowMainThreadQueries()
                .build();
        dao=db.dao();
        Data=dao.getalldata();
        Log.d("tag", String.valueOf(Data));

        ReminderList=findViewById(R.id.ReminderList);
        addReminder=findViewById(R.id.addReminder);
        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, AddReminder.class);
                intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                startActivity(intent);
                finish();

            }

        });
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext());
        adapter=new RVAdapter(Data);
        ReminderList.setLayoutManager(manager);
        ReminderList.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(ReminderList);

    }
    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position=viewHolder.getAdapterPosition();
            entity model=Data.get(position);
            Log.d("tag", String.valueOf(model.getAid()));
            RemoveFromManager(model.getAid(),model.getReminderdetails());
            dao.deleteAlarm(model.getAid());
            adapter.notifyItemRemoved(position);
            Data.remove(position);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void RemoveFromManager(int id,String remindername) {
        manager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(this, reiciver.class);
        intent.setAction("com.example.ALARM_TRIGGERED");
        intent.putExtra("notificationname",remindername);
        intent.putExtra("id",id);
        pendingIntent= PendingIntent.getBroadcast(this,id,intent,PendingIntent.FLAG_MUTABLE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
        Toast.makeText(this, "Alarm Removed Successfully", Toast.LENGTH_SHORT).show();
    }
    private void requestpermission() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            int notificaitonPermission= ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);
            if(shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)){

            }
            if(notificaitonPermission != PackageManager.PERMISSION_GRANTED){
                String [] notifperm={Manifest.permission.POST_NOTIFICATIONS};
                ActivityCompat.requestPermissions(this,notifperm,100);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}