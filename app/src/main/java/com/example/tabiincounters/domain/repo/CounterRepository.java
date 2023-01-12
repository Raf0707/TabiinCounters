package com.example.tabiincounters.domain.repo;

import android.app.Application;
import android.os.AsyncTask;

import com.example.tabiincounters.database.CounterDatabase;
import com.example.tabiincounters.domain.dao.CounterItemDao;
import com.example.tabiincounters.domain.model.CounterItem;

import java.util.List;

public class CounterRepository {
    private CounterItemDao counterItemDao;
    private List<CounterItem> counterlist;

    public CounterRepository(Application application) {
        CounterDatabase counterDatabase = CounterDatabase.getInstance(application);
        counterItemDao = counterDatabase.counterItemDao();
        counterlist = counterItemDao.getAllCounters();
    }

    public void insertData(CounterItem counterItem) {
        new InsertTask(counterItemDao).execute(counterItem);
    }
    public void updateData(CounterItem counterItem) {
        new UpdateTask(counterItemDao).execute(counterItem);
    }
    public void deleteData(CounterItem counterItem) {
        new DeleteTask(counterItemDao).execute(counterItem);
    }
    public List<CounterItem> getAllData() {
        return counterlist;
    }

    private static class InsertTask extends AsyncTask<CounterItem, Void, Void> {
        private CounterItemDao counterItemDao;

        public InsertTask(CounterItemDao counterItemDao) {
            this.counterItemDao = counterItemDao;
        }

        @Override
        protected Void doInBackground(CounterItem... counterItems) {
            counterItemDao.insertCounter(counterItems[0]);
            return null;
        }
    }

    private static class UpdateTask extends AsyncTask<CounterItem, Void, Void> {
        private CounterItemDao counterItemDao;

        public UpdateTask(CounterItemDao counterItemDao) {
            this.counterItemDao = counterItemDao;
        }

        @Override
        protected Void doInBackground(CounterItem... counterItems) {
            counterItemDao.updateCounter(counterItems[0]);
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<CounterItem, Void, Void> {
        private CounterItemDao counterItemDao;

        public DeleteTask(CounterItemDao counterItemDao) {
            this.counterItemDao = counterItemDao;
        }

        @Override
        protected Void doInBackground(CounterItem... counterItems) {
            counterItemDao.deleteCounter(counterItems[0]);
            return null;
        }
    }

}
