package com.example.tabiincounters.domain.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "counters")
public class CounterItem {
    private String title;
    private int target;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public CounterItem(String title, int target) {
        this.title = title;
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
