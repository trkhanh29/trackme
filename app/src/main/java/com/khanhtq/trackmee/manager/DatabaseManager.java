package com.khanhtq.trackmee.manager;

import android.content.Context;

import io.realm.Realm;

public class DatabaseManager {
    private static DatabaseManager sInstance;

    private Realm mRealmDatabase;

    private DatabaseManager(Context context) {
        Realm.init(context);
        mRealmDatabase = Realm.getDefaultInstance();
    }

    public static DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context);
        }
        return sInstance;
    }

    public void startNewTrackingSession(long newSessionId, float startLongitude, float startLatitude) {

    }
}
