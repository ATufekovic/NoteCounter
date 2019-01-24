package hr.ferit.antotufekovic.notecounter;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class EntryRepository {
    private EntryDao mEntryDao;
    private LiveData<List<Entry>> mAllEntries;

    EntryRepository(Application application)
    {
        EntryRoomDatabase db = EntryRoomDatabase.getDatabase(application);
        mEntryDao=db.entryDao();
        mAllEntries=mEntryDao.getAllEntries();
    }

    LiveData<List<Entry>> getAllEntries()
    {
        return mAllEntries;
    }

    public void insert (Entry entry)
    {
        new insertAsyncTask(mEntryDao).execute(entry);
    }
    private static class insertAsyncTask extends AsyncTask<Entry, Void, Void>
    {
        private EntryDao mAsyncTaskDao;
        insertAsyncTask(EntryDao dao)
        {
            mAsyncTaskDao=dao;
        }

        @Override
        protected Void doInBackground(final Entry... params)
        {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete (Entry entry) {new deleteAsyncTask(mEntryDao).execute(entry);}
    private static class deleteAsyncTask extends AsyncTask<Entry, Void, Void>
    {
        private EntryDao mAsyncTaskDao;
        deleteAsyncTask(EntryDao dao) {mAsyncTaskDao=dao;}

        @Override
        protected Void doInBackground(final Entry... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void update (Entry entry) {new updateAsyncTask(mEntryDao).execute(entry);}
    private static class updateAsyncTask extends AsyncTask<Entry, Void, Void>
    {
        private EntryDao mAsyncTaskDao;
        updateAsyncTask(EntryDao dao){mAsyncTaskDao=dao;}

        @Override
        protected Void doInBackground(Entry... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    public void incrementUpdate (Entry entry) {new incrementUpdateAsyncTask(mEntryDao).execute(entry);}
    private static class incrementUpdateAsyncTask extends  AsyncTask<Entry, Void, Void>
    {
        private EntryDao mAsyncTaskDao;
        incrementUpdateAsyncTask(EntryDao dao) {mAsyncTaskDao=dao;}

        @Override
        protected Void doInBackground(Entry... params) {
            mAsyncTaskDao.incrementUpdate(params[0].getName());
            return null;
        }
    }

    public void decrementUpdate (Entry entry) {new decrementUpdateAsyncTask(mEntryDao).execute(entry);}
    private static class decrementUpdateAsyncTask extends AsyncTask<Entry, Void, Void>
    {
        private EntryDao mAsyncTaskDao;
        decrementUpdateAsyncTask(EntryDao dao){mAsyncTaskDao=dao;}

        @Override
        protected Void doInBackground(Entry... params) {
            mAsyncTaskDao.decrementUpdate(params[0].getName());
            return null;
        }
    }
}
