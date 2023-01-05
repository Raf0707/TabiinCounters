package com.example.tabiincounters.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tabiincounters.domain.dao.CounterItemDao;
import com.example.tabiincounters.domain.model.CounterItem;

@Database(entities = {CounterItem.class}, version = 1)
public abstract class CounterDatabase extends RoomDatabase {
    private static CounterDatabase instance;
    public abstract CounterItemDao counterItemDao();

    public static synchronized CounterDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CounterDatabase.class, "counter_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
