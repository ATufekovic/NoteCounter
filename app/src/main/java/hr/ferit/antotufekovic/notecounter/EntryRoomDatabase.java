package hr.ferit.antotufekovic.notecounter;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities={Entry.class},version = 1, exportSchema = false)
public abstract class EntryRoomDatabase extends RoomDatabase {
    public abstract  EntryDao entryDao();

    private static volatile EntryRoomDatabase INSTANCE;

    static EntryRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE==null)
        {
            synchronized (EntryRoomDatabase.class)
            {
                if(INSTANCE==null)
                {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),EntryRoomDatabase.class,"entry_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>
    {
        private final EntryDao mDao;

        PopulateDbAsync(EntryRoomDatabase db)
        {
            mDao=db.entryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //if you need to do something to the data while starting the app, write it here
            return null;
        }
    }
}
