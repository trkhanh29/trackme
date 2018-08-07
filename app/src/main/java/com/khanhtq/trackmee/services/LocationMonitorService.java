package com.khanhtq.trackmee.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.khanhtq.trackmee.Constant;
import com.khanhtq.trackmee.manager.DatabaseManager;
import com.khanhtq.trackmee.util.LogUtil;
import com.khanhtq.trackmee.TrackMeApplication;

public class LocationMonitorService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = LocationMonitorService.class.getSimpleName();

    private GoogleApiClient mLocationClient;
    private LocationRequest mLocationRequest;
    private DatabaseManager mDatabaseManager;

    public static final String ACTION_LOCATION_BROADCAST = LocationMonitorService.class.getName() + "_LocationBroadcast";
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TrackMeApplication application = (TrackMeApplication) getApplication();
        mDatabaseManager = application.getDatabaseManager();

        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(Constant.LOCATION_INTERVAL);
        mLocationRequest.setFastestInterval(Constant.FASTEST_LOCATION_INTERVAL);


        int priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
        mLocationRequest.setPriority(priority);
        mLocationClient.connect();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(Bundle dataBundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);
        LogUtil.d(TAG, "Connected to Google API");
    }

    @Override
    public void onConnectionSuspended(int i) {
        LogUtil.d(TAG, "Connection suspended");
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            LogUtil.d(TAG, "Location != null");
            sendMessageToUI(location.getLatitude(), location.getLongitude());
        }
    }

    private void sendMessageToUI(double lat, double lng) {
        LogUtil.d(TAG, "Sending info..." + lat + "/" + lng);
        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_LATITUDE, lat);
        intent.putExtra(EXTRA_LONGITUDE, lng);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        LogUtil.d(TAG, "Failed to connect to Google API -- " + connectionResult.getErrorMessage());
    }
}
