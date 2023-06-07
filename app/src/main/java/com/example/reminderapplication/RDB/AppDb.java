package com.example.reminderapplication.RDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {entity.class},version = 1)
public abstract  class AppDb extends RoomDatabase {
    private static AppDb instance;

    public static synchronized AppDb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDb.class, "Reminder").allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract DAO dao();
}
