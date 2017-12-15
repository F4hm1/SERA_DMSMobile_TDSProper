package com.serasiautoraya.tdsproper.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Randi Dwi Nandra on 05/01/2017.
 */
public class LocationManagerUtil implements LocationListener {

    private static LocationManager locationManager;
    private Activity activity;
    private static LocationManagerUtil locationManagerUtil = null;
    private Location location = null;

    public LocationManagerUtil(Activity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        requestUpdateLocation();
    }

    private void requestUpdateLocation(){
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }
    private void removeUpdates(){
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager.removeUpdates(this);
    }

    public static LocationManagerUtil getInstance(Activity activity){
        if(locationManagerUtil == null){
            locationManagerUtil = new LocationManagerUtil(activity);
        }
        return locationManagerUtil;
    }

    public Location getLocation(){
        if (location == null){
            requestUpdateLocation();
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        removeUpdates();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
