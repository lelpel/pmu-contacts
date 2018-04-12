package ua.lelpel.phonebook;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import ua.lelpel.phonebook.data.AppDb;

/**
 * Created by bruce on 31.03.2018.
 */

public class App extends Application {
    private AppDb db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(this, AppDb.class, "pmu.db").allowMainThreadQueries().build();
    }

    public AppDb getDb() {
        return db;
    }
}
