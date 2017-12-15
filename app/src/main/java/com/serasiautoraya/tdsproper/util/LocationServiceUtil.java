package com.serasiautoraya.tdsproper.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.serasiautoraya.tdsproper.Login.LoginActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Randi Dwi Nandra on 29/11/2016.
 */
public class LocationServiceUtil implements LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    /**
     * Variable declaration
     */
    private static LocationServiceUtil instance = null;
    private static final int UPDATE_INTERVAL = 1000 * 5; //in milliseconds
    private static final int UPDATE_INTERVAL_FASTEST = 1000 * 1; //in milliseconds
    private Geocoder mGeocoder;
    private GoogleApiClient mGoogleApiClient = null;
    private LocationRequest mLocationRequest;
    private Location sLocation;
    private Context mTemporaryContext;

    public LocationServiceUtil(Context context) {
        this.mTemporaryContext = context;
        initGoogleApiClient(context);

    }

    private void initGoogleApiClient(Context context) {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(UPDATE_INTERVAL_FASTEST);
        this.mGeocoder = new Geocoder(context, Locale.getDefault());

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    public void connectGoogleAPIClient(){
        if (mGoogleApiClient != null) {
            if(!mGoogleApiClient.isConnected()){
                mGoogleApiClient.connect();
            }
        }
    }

    /**
     * Singleton implementation
     * @return
     */
    public static LocationServiceUtil getLocationManager(Context context) {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(context);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog((LoginActivity) context, result, 9001).show();
            }
        }
        if (instance == null) {
            instance = new LocationServiceUtil(context);
        }
        return instance;
    }

    public boolean isGPSEnabled(){
        LocationManager lm = (LocationManager) mTemporaryContext.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled( LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(mTemporaryContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mTemporaryContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        sLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null){
            this.sLocation = location;
        }
        Log.d("LOCATION_UPDATE", "Location updated");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(connectionResult.getErrorCode() != ConnectionResult.SUCCESS) {
            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
            if(googleAPI.isUserResolvableError(connectionResult.getErrorCode())) {
                googleAPI.getErrorDialog((LoginActivity) mTemporaryContext, connectionResult.getErrorCode(), 9001).show();
            }
        }
    }

    private void updateLocation(){
        if (ContextCompat.checkSelfPermission(mTemporaryContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mTemporaryContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        sLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    public Location getLastLocation(){
        updateLocation();
        if(sLocation != null){
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            return sLocation;
        }else {
            return null;
        }
    }

    public String getLastLocationName(){
        String address = "Nama lokasi tidak terdeteksi";
        List<Address> addresses;
        try {
            addresses = mGeocoder.getFromLocation(sLocation.getLatitude(), sLocation.getLongitude(), 1);
            if (addresses.size() > 0) {
                address = "";

                for (Address adrs : addresses) {
                    address = adrs.getAddressLine(0);
                }

            }
        } catch (IOException e) {
            address = "Err. Nama lokasi tidak terdeteksi";
            e.printStackTrace();
        }
        return address;
    }
}