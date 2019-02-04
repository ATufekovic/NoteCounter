package hr.ferit.antotufekovic.notecounter;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
    //TODO: @Delete and @Update
@Dao
public interface EntryDao {
    @Insert
    void insert (Entry entry);

    @Query("DELETE FROM entry_table")
    void deleteAll();

    @Query("SELECT * FROM entry_table ORDER BY name ASC")
    LiveData<List<Entry>> getAllEntries();

    @Delete
    void delete(Entry entry);

    @Update
    void update(Entry entry);

    @Query("UPDATE entry_table SET count=count+1 WHERE name=:entryName" )
    void incrementUpdate(String entryName);

    @Query("UPDATE entry_table SET count=count-1 WHERE name=:entryName" )
    void decrementUpdate(String entryName);
}
