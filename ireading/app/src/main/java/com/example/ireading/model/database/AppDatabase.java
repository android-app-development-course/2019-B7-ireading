package com.example.ireading.model.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.ireading.model.dao.BookDao;
import com.example.ireading.model.entity.Book;


@Database(entities = {Book.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        // bug: 在fragment使用该单例，将fragment的getActivity()作为参数传进来，然后报了以下异常
        // Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'android.content.Context android.content.Context.getApplicationContext()' on a null object reference
        // at com.example.ireading.model.database.AppDatabase.getDatabase(AppDatabase.java:27)
        // at com.example.ireading.ui.fragment.ListBookFragment.<init>(ListBookFragment.java:42)
        // 	... 34 more
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            AppDatabase.class, "app_database").build();
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    public abstract BookDao bookDao();
}
