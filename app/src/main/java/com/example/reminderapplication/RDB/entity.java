package com.example.reminderapplication.RDB;


import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

@androidx.room.Entity
public class entity {
    @ColumnInfo(name = "reminderdetails")
    public String reminderdetails;

    @PrimaryKey
    @ColumnInfo(name = "alarmid" )
    public int aid;

    @ColumnInfo(name="alarmtime")
    public long alaramtime;

    public entity(String reminderdetails, int aid, long alaramtime) {
        this.reminderdetails = reminderdetails;
        this.aid = aid;
        this.alaramtime = alaramtime;
    }

    public String getReminderdetails() {
        return reminderdetails;
    }

    public void setReminderdetails(String reminderdetails) {
        this.reminderdetails = reminderdetails;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public long getAlaramtime() {
        return alaramtime;
    }

    public void setAlaramtime(long alaramtime) {
        this.alaramtime = alaramtime;
    }
}
