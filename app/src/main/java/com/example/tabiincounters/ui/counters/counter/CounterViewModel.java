package com.example.tabiincounters.ui.counters.counter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tabiincounters.domain.dao.CounterItemDao;
import com.example.tabiincounters.domain.model.CounterItem;
import com.example.tabiincounters.domain.repo.CounterRepository;

import java.util.List;

public class CounterViewModel extends AndroidViewModel {
    private CounterRepository counterRepository;
    private LiveData<List<CounterItem>> counterlist;

    public CounterViewModel(@NonNull Application application) {
        super(application);
        counterRepository = new CounterRepository(application);
        counterlist = counterRepository.getAllData();
    }

    public void insert(CounterItem counterItem) {
        counterRepository.insertData(counterItem);
    }

    public void update(CounterItem counterItem) {
        counterRepository.updateData(counterItem);
    }

    public void delete(CounterItem counterItem) {
        counterRepository.deleteData(counterItem);
    }

    public LiveData<List<CounterItem>> getCounterlist() {
        return counterlist;
    }

}
