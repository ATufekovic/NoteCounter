package hr.ferit.antotufekovic.notecounter;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName="entry_table")
public class Entry {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="count")
    private int count;

    public Entry(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
