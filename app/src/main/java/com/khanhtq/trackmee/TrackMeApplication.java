package com.khanhtq.trackmee;

import android.app.Application;

import com.khanhtq.trackmee.manager.DatabaseManager;

public class TrackMeApplication extends Application {
    private DatabaseManager mDatabaseManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabaseManager = DatabaseManager.getInstance(this);
    }

    public DatabaseManager getDatabaseManager() {
        return mDatabaseManager;
    }
}
