package com.example.tabiincounters.ui.counters.counter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tabiincounters.database.CounterDatabase;
import com.example.tabiincounters.domain.dao.CounterItemDao;
import com.example.tabiincounters.domain.model.CounterItem;
import com.example.tabiincounters.domain.repo.CounterRepository;

import java.util.List;

public class CounterViewModel extends AndroidViewModel {
    private MutableLiveData<List<CounterItem>> counterlist;
    private CounterDatabase counterDatabase;

    public CounterViewModel(@NonNull Application application) {
        super(application);
        counterlist = new MutableLiveData<>();
        counterDatabase = CounterDatabase.getInstance(getApplication()
                .getApplicationContext());

    }

    public MutableLiveData<List<CounterItem>> getCounterlistObserver() {
        return counterlist;
    }

    public void getAllCounterList() {
        List<CounterItem> categoryList = counterDatabase.counterItemDao().getAllCounters();
        if(categoryList.size() > 0)
        {
            counterlist.postValue(categoryList);
        }else {
            counterlist.postValue(null);
        }
    }

    public void insert(String title, int target) {
        CounterItem counterItem = new CounterItem();
        counterItem.title = title;
        counterItem.target = target;
        counterDatabase.counterItemDao().insertCounter(counterItem);
        getAllCounterList();
        //getCounterlistObserver();
    }

    public void update(CounterItem counterItem) {
        counterDatabase.counterItemDao().updateCounter(counterItem);
        getAllCounterList();
        //getCounterlistObserver();
    }

    public void delete(CounterItem counterItem) {
        counterDatabase.counterItemDao().deleteCounter(counterItem);
        getAllCounterList();
        //getCounterlistObserver();

    }

}
