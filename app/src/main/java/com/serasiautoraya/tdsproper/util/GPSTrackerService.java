package com.serasiautoraya.tdsproper.util;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.LocationUpdate.LocationUpdateSendModel;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.activity.LoginActivity;
import com.serasiautoraya.tdsproper.util.HelperKey;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Randi Dwi Nandra on 09/02/2017.
 */

public class GPSTrackerService extends Service implements LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static GPSTrackerService instance = null;
    private static final int UPDATE_INTERVAL = 1000 * 10; //in milliseconds
    private static final int UPDATE_INTERVAL_FASTEST = 1000 * 20; //in milliseconds
    private Geocoder mGeocoder;
    private GoogleApiClient mGoogleApiClient = null;
    private LocationRequest mLocationRequest;
    private Location sLocation;
    private Context mTemporaryContext;
    private static final int FOREGROUND_ID = 9898;

    private Context mApplicationContext;

    /*
    * TODO Delete this
    * */
    private static String TAG = "GPSTrackerService";
    private RestConnection mRestConnection;

    public GPSTrackerService(Context context) {
        Log.d(TAG, "1: GPSTrackerService");
        this.mTemporaryContext = context;
        Log.d(TAG, "2: GPSTrackerService");
        initGoogleApiClient(context);
        Log.d(TAG, "3: GPSTrackerService");
    }

    public GPSTrackerService() {

    }

    @Override
    public void onCreate() {
//        getLocationManager(AppInit.getAppContext());
        mApplicationContext = getApplicationContext();
        Log.d(TAG, "CREATED SERVICES");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getLocationManager(AppInit.getAppContext());

        Intent i = new Intent(this, com.serasiautoraya.tdsproper.Login.LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap bitmapIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.logoselog);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(bitmapIcon)
                .setSmallIcon(R.drawable.logoselog)
                .setAutoCancel(true)
                .setContentTitle("TDS Proper")
                .setContentText("Terdapat order yang sedang berjalan")
                .setTicker("Terdapat order yang sedang berjalan")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);

        startForeground(FOREGROUND_ID, builder.build());
        Log.d(TAG, "STARTED SERVICES");
        return START_STICKY;
    }

    private void initGoogleApiClient(Context context) {
        Log.d(TAG, "1: initGoogleApiClient");
        SharedPrefsModel sharedPrefsModel = new SharedPrefsModel(context);
        int locationUpdateInterval = sharedPrefsModel.get(com.serasiautoraya.tdsproper.Helper.HelperKey.KEY_LOCATION_UPDATE_INTERVAL, 60);
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(locationUpdateInterval);
        this.mGeocoder = new Geocoder(context, Locale.getDefault());

        if (mGoogleApiClient == null) {
            Log.d(TAG, "2: initGoogleApiClient");
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        Log.d(TAG, "3: initGoogleApiClient");
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            Log.d(TAG, "4: initGoogleApiClient");
        }

        Log.d(TAG, "5: initGoogleApiClient");
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "6: initGoogleApiClient");
            return;
        }
    }

    public void connectGoogleAPIClient() {
        if (mGoogleApiClient != null) {
            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }
        }
    }

    /**
     * Singleton implementation
     *
     * @return
     */
    public static GPSTrackerService getLocationManager(Context context) {
        Log.d(TAG, "1: getLocationManager");
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(context);
        if (result != ConnectionResult.SUCCESS) {
            Log.d(TAG, "2: getLocationManager");
            if (googleAPI.isUserResolvableError(result)) {
                Log.d(TAG, "3: getLocationManager");
                googleAPI.getErrorDialog((LoginActivity) context, result, 9001).show();
            }
        }
        Log.d(TAG, "4: getLocationManager");
        if (instance == null) {
            Log.d(TAG, "5: getLocationManager");
            instance = new GPSTrackerService(context);
            Log.d(TAG, "6: getLocationManager");
        }
        return instance;
    }

    public boolean isGPSEnabled() {
        LocationManager lm = (LocationManager) mTemporaryContext.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "1: onConnected");
        if (ContextCompat.checkSelfPermission(mTemporaryContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mTemporaryContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "2: onConnected");
            return;
        }
        Log.d(TAG, "3: onConnected");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "4: onConnected");
        sLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.d(TAG, "5: onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "1: onConnectionSuspended");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Loc updated: apsopaso");
        if (location != null) {
            this.sLocation = location;
            Context mContext = AppInit.getAppContext();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(Calendar.getInstance().getTime());
            SharedPrefsUtil.apply(mContext, HelperKey.KEY_LOC_LAT, sLocation.getLatitude() + "");
            SharedPrefsUtil.apply(mContext, HelperKey.KEY_LOC_LONG, sLocation.getLongitude() + "");
            SharedPrefsUtil.apply(mContext, HelperKey.KEY_LOC_ADDRESS, getLastLocationNameVer2() + "");
            SharedPrefsUtil.apply(mContext, "CUR_TIME", time);
            String latitude = SharedPrefsUtil.getString(mContext, HelperKey.KEY_LOC_LAT, "");
            String longitude = SharedPrefsUtil.getString(mContext, HelperKey.KEY_LOC_LONG, "");
            String waktu = SharedPrefsUtil.getString(mContext, "CUR_TIME", "");
            Log.d(TAG, "Loc updated: " + waktu + " oo " + latitude + " - " + sLocation.getLongitude());
        }
        updateToServer();
    }

    private void updateToServer() {
        LocationUpdateSendModel locationUpdateSendModel = new LocationUpdateSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                sLocation.getLatitude() + "",
                sLocation.getLongitude() + "",
                getLastLocationNameVer2()
        );

        if(mRestConnection == null){
            mRestConnection = new RestConnection(AppInit.getAppContext());
        }
        mRestConnection.putData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.PUT_LOCATION_UPDATE, locationUpdateSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                Log.d("LOCATION_UPDATE", "SERV_SUCCESS");
            }

            @Override
            public void callBackOnFail(String response) {
                Log.d("LOCATION_UPDATE", "SERV_FAIL:" + response);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                Log.d("LOCATION_UPDATE", "SERV_ERROR:" + error);
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("LOHIN_TAG", "connect failed: " + connectionResult.getErrorCode());
        if (connectionResult.getErrorCode() != ConnectionResult.SUCCESS) {
            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
            if (googleAPI.isUserResolvableError(connectionResult.getErrorCode())) {
                googleAPI.getErrorDialog((LoginActivity) mTemporaryContext, connectionResult.getErrorCode(), 9001).show();
            }
        }
    }

    private void updateLocation() {
        if (ContextCompat.checkSelfPermission(mTemporaryContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mTemporaryContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        sLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    public Location getLastLocation() {
        updateLocation();
        if (sLocation != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Context mContext = AppInit.getAppContext();
            SharedPrefsUtil.apply(mContext, HelperKey.KEY_LOC_LAT, sLocation.getLatitude() + "");
            SharedPrefsUtil.apply(mContext, HelperKey.KEY_LOC_LONG, sLocation.getLongitude() + "");
            SharedPrefsUtil.apply(mContext, HelperKey.KEY_LOC_ADDRESS, getLastLocationNameVer2() + "");
            return sLocation;
        } else {
            return null;
        }
    }

    public String getLastLocationName() {
        String address = "";
        List<Address> addresses;
        try {
            addresses = mGeocoder.getFromLocation(sLocation.getLatitude(),
                    sLocation.getLongitude(), 1);
            Log.d("LOCATION_UPDATE", "Addresses size: " + addresses.size());
            if (addresses.size() > 0) {
                Log.d("LOCATION_UPDATE", "Locality: " + addresses.get(0).getLocality());
                System.out.println(addresses.get(0).getLocality());
                for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
                    address += "\n" + addresses.get(0).getAddressLine(i) + " ";
                }
                Log.d("LOCATION_UPDATE", "Address: " + address);
                if(address.equalsIgnoreCase("")){
                    address = addresses.get(0).getLocality();
                }
            }
        } catch (IOException e) {
            address = "err";
            e.printStackTrace();
        }
        if (address.equalsIgnoreCase("")) {
            address = "Tidak terdeteksi";
        }
        return address;
    }

    public String getLastLocationNameVer2(){
        String address = "Nama lokasi tidak terdeteksi";
        List<Address> addresses;
        try {
            addresses = mGeocoder.getFromLocation(sLocation.getLatitude(), sLocation.getLongitude(), 1);
            if (addresses.size() > 0) {
                address = "";

                for (Address adrs : addresses) {
                    address = adrs.getAddressLine(0);
                    Log.d("LOCATION_UPDATE", "Address: " + address);
                }

            }
            Log.d("LOCATION_UPDATE", "Final Address: " + address);
        } catch (IOException e) {
            address = "Nama lokasi tidak terdeteksi";
            e.printStackTrace();
        }
        return address;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /*TODO
    * TODO: Update location hanya dilakukan ketika ada journey (start order), nyalakan service (apabila belum nyala)
    * * TODO: Matikan service apabila end order (apabila belum mati & tidak ada order lain yg sedang berjalan)
    *
    * */
}
