package com.example.reminderapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.reminderapplication.RDB.AppDb;
import com.example.reminderapplication.RDB.DAO;
import com.example.reminderapplication.RDB.entity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.Date;

public class AddReminder extends AppCompatActivity {
    EditText reminderDetails;
    MaterialButton AddReminder,SelcetDate,SelectTime;
    TextView ShowTime;
    Calendar cal,calendar;
    AlarmManager manager;
    Date date;
    AppDb db;
    DAO dao;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        craeteNotification();
        //

        calendar=Calendar.getInstance();
        reminderDetails=findViewById(R.id.reminder_details);
        ShowTime=findViewById(R.id.time_date);
        AddReminder=findViewById(R.id.add_reminder_btn);
        SelcetDate=findViewById(R.id.select_date);
        SelectTime=findViewById(R.id.select_time);
        db= Room.databaseBuilder(getApplicationContext(),AppDb.class,"Reminder").allowMainThreadQueries().build();
        dao=db.dao();
        //
        SelcetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        SelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });
        AddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int id=Math.abs((int)System.currentTimeMillis());
               dao.insertall(new entity(reminderDetails.getText().toString(),id,calendar.getTimeInMillis()));
               setalarm(id);
               Intent intent=new Intent(com.example.reminderapplication.AddReminder.this,MainActivity.class);
               startActivity(intent);
               finish();


            }
        });
    }
    private void setalarm(int id) {
        manager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent=new Intent(AddReminder.this, reiciver.class);
        intent.setAction("com.example.ALARM_TRIGGERED");
//        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("notificationname",reminderDetails.getText().toString());
        intent.putExtra("id",id);
        pendingIntent= PendingIntent.getBroadcast(this,id,intent,PendingIntent.FLAG_MUTABLE);
        Log.d("tag", String.valueOf(id));
        date=new Date(calendar.getTimeInMillis());
        Toast.makeText(this, date.toString(), Toast.LENGTH_SHORT).show();
        manager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

    }
    private void showDatePicker() {
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPositiveButtonClick(Object selection) {
                ShowTime.setText(materialDatePicker.getHeaderText());
            }
        });

    }
    private void showTimePicker(){
        cal=Calendar.getInstance();
        int hours=cal.get(Calendar.HOUR_OF_DAY);
        int min=cal.get(Calendar.MINUTE);
        MaterialTimePicker timePicker=new MaterialTimePicker.Builder().setHour(hours).setMinute(min).setTitleText("Selecttime").setTimeFormat(TimeFormat.CLOCK_12H).build();
        timePicker.show(getSupportFragmentManager(),"MainActivity");
        timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
                calendar.set(Calendar.MINUTE,timePicker.getMinute());
                Date date1=new Date(calendar.getTimeInMillis());
                ShowTime.setText(date1.toString());
            }
        });
    }
    private void craeteNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name="channelname";
            String description="channle for alarm manager";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel("testing",name,importance);
            channel.setDescription(description);

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }
}