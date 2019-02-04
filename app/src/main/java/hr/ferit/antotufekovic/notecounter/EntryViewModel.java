package hr.ferit.antotufekovic.notecounter;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {
    private EntryRepository mRepository;
    private LiveData<List<Entry>> mAllEntries;

    public EntryViewModel(Application application) {
        super(application);
        mRepository = new EntryRepository(application);
        mAllEntries = mRepository.getAllEntries();
    }

    public boolean listContainsEntryByName(Entry entry) {
        if (mAllEntries.getValue().size() == 0) {
            return false;
        }

        for (int i = 0; i < mAllEntries.getValue().size(); i++) {
            if(mAllEntries.getValue().get(i).getName().equals(entry.getName())){
                return true;
            }
        }

        return false;
    }

    LiveData<List<Entry>> getAllEntries() {
        return mAllEntries;
    }

    public void insert(Entry entry) {
        mRepository.insert(entry);
    }

    public void delete(Entry entry) {
        mRepository.delete(entry);
    }

    public void update(Entry entry) {
        mRepository.update(entry);
    }

    public void incrementUpdate(Entry entry) {
        mRepository.incrementUpdate(entry);
    }

    public void decrementUpdate(Entry entry) {
        mRepository.decrementUpdate(entry);
    }
}
