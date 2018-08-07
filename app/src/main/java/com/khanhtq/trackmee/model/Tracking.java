package com.khanhtq.trackmee.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tracking extends RealmObject {
    @PrimaryKey
    private long mSessionId;

    private RealmList<Float> mLongitudeOfPath;

    private RealmList<Float> mLatitudeOfPath;

    private RealmList<Float> mSpeedValues;

    public long getSessionId() {
        return mSessionId;
    }

    public void setSessionId(long sessionId) {
        mSessionId = sessionId;
    }

    public RealmList<Float> getLongitudeOfPath() {
        return mLongitudeOfPath;
    }

    public void setLongitudeOfPath(RealmList<Float> longitudeOfPath) {
        mLongitudeOfPath = longitudeOfPath;
    }

    public RealmList<Float> getLatitudeOfPath() {
        return mLatitudeOfPath;
    }

    public void setLatitudeOfPath(RealmList<Float> latitudesOfPath) {
        mLatitudeOfPath = latitudesOfPath;
    }

    public RealmList<Float> getSpeedValues() {
        return mSpeedValues;
    }

    public void setSpeedValues(RealmList<Float> speedValues) {
        mSpeedValues = speedValues;
    }
}
