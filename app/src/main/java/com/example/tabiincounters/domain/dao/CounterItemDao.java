package com.example.tabiincounters.domain.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tabiincounters.domain.model.CounterItem;

import java.util.List;

@Dao
public interface CounterItemDao {
    @Query("SELECT * FROM counters")
    LiveData<List<CounterItem>> getAllCounters();

    @Query("SELECT * FROM counters WHERE id IN (:userIds)")
    LiveData<List<CounterItem>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM counters WHERE title LIKE :title LIMIT 1")
    CounterItem findByName(String title);

    @Insert
    void insertAll(CounterItem...counterItems);

    @Insert
    void insert(CounterItem counterItem);

    @Update
    void update(CounterItem counterItem);

    @Delete
    void delete(CounterItem counterItem);
}
