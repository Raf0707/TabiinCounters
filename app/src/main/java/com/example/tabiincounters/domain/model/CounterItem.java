package com.example.tabiincounters.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "counters")
public class CounterItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "target")
    public int target;

    @ColumnInfo(name = "progress")
    public int progress;

    @ColumnInfo(name = "completed")
    public boolean completed;


    public CounterItem() {
        title = getTitle();
        target = getTarget();
        progress = getProgress();
    }
    public CounterItem(String title, int target, int progress) {
        this.title = title;
        this.target = target;
        this.progress = progress;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }


}
