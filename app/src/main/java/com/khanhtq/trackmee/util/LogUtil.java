package com.khanhtq.trackmee.util;

import android.util.Log;

import com.khanhtq.trackmee.BuildConfig;

public class LogUtil {
    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }
}
