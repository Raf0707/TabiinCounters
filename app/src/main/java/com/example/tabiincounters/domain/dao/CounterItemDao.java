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
    List<CounterItem> getAllCounters();

    @Query("SELECT * FROM counters WHERE id IN (:userIds)")
    List<CounterItem> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM counters WHERE title LIKE :title LIMIT 1")
    CounterItem findByName(String title);

    @Insert
    void insertAll(CounterItem...counterItems);

    @Insert
    void insertCounter(CounterItem counterItem);

    @Update
    void updateCounter(CounterItem counterItem);

    @Delete
    void deleteCounter(CounterItem counterItem);
}
