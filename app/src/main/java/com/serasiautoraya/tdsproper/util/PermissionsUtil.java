package com.serasiautoraya.tdsproper.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.serasiautoraya.tdsproper.util.HelperKey;
import com.serasiautoraya.tdsproper.util.HelperUtil;

/**
 * Created by Randi Dwi Nandra on 29/11/2016.
 */
public class PermissionsUtil implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static boolean sLocationGranted = false;
    private static boolean sExternalStorageGranted = false;
    private static boolean sInternetGranted = false;
    private static boolean sSystemAlertGranted = false;
    private static boolean sVibrateGranted = false;

    private static Activity sCurrentActivity;
    public static void requestLocationPermission(Activity activity){
        sCurrentActivity = activity;
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( activity, new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    HelperKey.LOCATION_PERMISSION_GRANTED_CODE);
        }else{
            PermissionsUtil.sLocationGranted = true;
        }
    }

    public static void requestStoragePermission(Activity activity){
        sCurrentActivity = activity;
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( activity, new String[] {  Manifest.permission.WRITE_EXTERNAL_STORAGE  },
                    HelperKey.STORAGE_PERMISSION_GRANTED_CODE);
        }else{
            PermissionsUtil.sExternalStorageGranted = true;
        }
    }

    public static void requestWindowsAlert(Activity activity){
        sCurrentActivity = activity;
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( activity, new String[] {  Manifest.permission.SYSTEM_ALERT_WINDOW  },
                    HelperKey.SYSWINDOWS_PERMISSION_GRANTED_CODE);
        }else{
            PermissionsUtil.sSystemAlertGranted = true;
        }
    }

    public static void requestVibrate(Activity activity){
        sCurrentActivity = activity;
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( activity, new String[] {  Manifest.permission.VIBRATE  },
                    HelperKey.VIBRATE_PERMISSION_GRANTED_CODE);
        }else{
            PermissionsUtil.sVibrateGranted = true;
        }
    }

    public static boolean issSystemAlertGranted() {
        return sSystemAlertGranted;
    }

    public static boolean issVibrateGranted() {
        return sVibrateGranted;
    }

    public static boolean issLocationGranted() {
        return sLocationGranted;
    }

    public static boolean issExternalStorageGranted() {
        return sExternalStorageGranted;
    }

    public static boolean issInternetGranted() {
        return sInternetGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case HelperKey.LOCATION_PERMISSION_GRANTED_CODE: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    PermissionsUtil.sLocationGranted = true;
                } else {
//                    HelperUtil.showConfirmationAlertDialogNoCancel("Mohon aktifkan ijin lokasi untuk aplikasi ini", sCurrentActivity.getBaseContext(), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            requestLocationPermission(sCurrentActivity);
//                        }
//                    });
                    if(SharedPrefsUtil.getBoolean(sCurrentActivity, HelperKey.HAS_LOGIN, false)){
                        System.exit(0);
                    }else{
                        HelperUtil.showSimpleToast("Mohon aktifkan semua ijin sistem untuk aplikasi ini", sCurrentActivity.getBaseContext());
                    }
                }
                break;
            }
            case HelperKey.STORAGE_PERMISSION_GRANTED_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionsUtil.sExternalStorageGranted = true;
                } else {
//                    HelperUtil.showConfirmationAlertDialogNoCancel("Mohon aktifkan ijin akses storage untuk aplikasi ini", sCurrentActivity.getBaseContext(), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            requestStoragePermission(sCurrentActivity);
//                        }
//                    });
                    if(SharedPrefsUtil.getBoolean(sCurrentActivity, HelperKey.HAS_LOGIN, false)){
                        System.exit(0);
                    }else{
                        HelperUtil.showSimpleToast("Mohon aktifkan ijin akses storage untuk aplikasi ini", sCurrentActivity.getBaseContext());
                    }
                }
                break;
            }
            case HelperKey.SYSWINDOWS_PERMISSION_GRANTED_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionsUtil.sSystemAlertGranted = true;
                } else {
//                    HelperUtil.showConfirmationAlertDialogNoCancel("Mohon aktifkan ijin akses storage untuk aplikasi ini", sCurrentActivity.getBaseContext(), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            requestStoragePermission(sCurrentActivity);
//                        }
//                    });
                    if(SharedPrefsUtil.getBoolean(sCurrentActivity, HelperKey.HAS_LOGIN, false)){
                        System.exit(0);
                    }else{
                        HelperUtil.showSimpleToast("Mohon aktifkan ijin akses notifikasi sistem untuk aplikasi ini", sCurrentActivity.getBaseContext());
                    }
                }
                break;
            }
            case HelperKey.VIBRATE_PERMISSION_GRANTED_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionsUtil.sVibrateGranted = true;
                } else {
//                    HelperUtil.showConfirmationAlertDialogNoCancel("Mohon aktifkan ijin akses storage untuk aplikasi ini", sCurrentActivity.getBaseContext(), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            requestStoragePermission(sCurrentActivity);
//                        }
//                    });
                    if(SharedPrefsUtil.getBoolean(sCurrentActivity, HelperKey.HAS_LOGIN, false)){
                        System.exit(0);
                    }else{
                        HelperUtil.showSimpleToast("Mohon aktifkan ijin getar untuk notifikasi aplikasi ini", sCurrentActivity.getBaseContext());
                    }
                }
                break;
            }
        }
    }
}
