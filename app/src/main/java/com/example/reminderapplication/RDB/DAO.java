package com.example.reminderapplication.RDB;

import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insertall(entity entity);



    @Query("SELECT * FROM entity")
    List<entity> getalldata();

    @Query("SELECT reminderdetails FROM entity ")
    String [] ReminderNames();

    @Query("delete from entity where alarmid=:id")
    void alaramComplete(int id);

    @Query("delete from entity where alarmid=:id")
    void deleteAlarm(int id);

}
