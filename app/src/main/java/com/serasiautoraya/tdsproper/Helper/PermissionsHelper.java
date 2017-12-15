package com.serasiautoraya.tdsproper.Helper;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;
import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;

/**
 * Created by Randi Dwi Nandra on 13/04/2017.
 * This class is different with other Helper Class, because this class is instantiatable
 */

public class PermissionsHelper implements ActivityCompat.OnRequestPermissionsResultCallback{


    private boolean isAllPermissionsGranted = false;
    private boolean isExternalStorageGranted = false;
    private boolean isInternetGranted = false;
    private boolean isSystemAlertGranted = false;
    private boolean isVibrateGranted = false;

    private static Activity mCurrentActivity;
    private static SharedPrefsModel mSharedPrefsModel;
    private static BaseViewInterface mBaseViewInterface;

    private static PermissionsHelper sPermissionsHelper = null;

    private String mDeviceID = "";

    public String getsDeviceID() {
        return mDeviceID;
    }

    public PermissionsHelper(Activity mCurrentActivity, BaseViewInterface mBaseViewInterface) {
        this.mCurrentActivity = mCurrentActivity;
        this.mBaseViewInterface = mBaseViewInterface;
        this.mSharedPrefsModel = new SharedPrefsModel(this.mCurrentActivity);
    }

    public static PermissionsHelper getInstance(Activity activity, BaseViewInterface baseViewInterface){
        if(sPermissionsHelper == null){
            sPermissionsHelper = new PermissionsHelper(activity, baseViewInterface);
        }else {
            mCurrentActivity = activity;
            mBaseViewInterface = baseViewInterface;
            mSharedPrefsModel = new SharedPrefsModel(mCurrentActivity);
        }
        return sPermissionsHelper;
    }

    public void requestLocationPermission(){
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(mCurrentActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mCurrentActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mCurrentActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mCurrentActivity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(mCurrentActivity, new String[] {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE},
                    HelperKey.LOCATION_PERMISSION_GRANTED_CODE);
        }else{
            isAllPermissionsGranted = true;
        }
    }

    public boolean isAllPermissionsGranted() {
        return isAllPermissionsGranted;
    }

    //    public void requestStoragePermission(){
//        if (Build.VERSION.SDK_INT >= 23 &&
//                ContextCompat.checkSelfPermission(mCurrentActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(mCurrentActivity, new String[] {  android.Manifest.permission.WRITE_EXTERNAL_STORAGE  },
//                    HelperKey.STORAGE_PERMISSION_GRANTED_CODE);
//        }else{
//            isExternalStorageGranted = true;
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case HelperKey.LOCATION_PERMISSION_GRANTED_CODE: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    isAllPermissionsGranted = true;

//                    TelephonyManager mTelephonyManager = (TelephonyManager) mCurrentActivity.getSystemService(Context.TELEPHONY_SERVICE);
//                    mDeviceID = mTelephonyManager.getDeviceId();

                } else {
                    if(mSharedPrefsModel.get(HelperKey.HAS_LOGIN, false)){
                        System.exit(0);
                    }else{
                        mBaseViewInterface.showToast("Mohon aktifkan semua ijin sistem untuk aplikasi ini");
                    }
                }
                break;
            }
            case HelperKey.STORAGE_PERMISSION_GRANTED_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isExternalStorageGranted = true;
                } else {
                    if(mSharedPrefsModel.get(HelperKey.HAS_LOGIN, false)){
                        System.exit(0);
                    }else{
                        mBaseViewInterface.showToast("Mohon aktifkan ijin akses storage untuk aplikasi ini");
                    }
                }
                break;
            }
            case HelperKey.SYSWINDOWS_PERMISSION_GRANTED_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isSystemAlertGranted = true;
                } else {
                    if(mSharedPrefsModel.get(HelperKey.HAS_LOGIN, false)){
                        System.exit(0);
                    }else{
                        mBaseViewInterface.showToast("Mohon aktifkan ijin akses notifikasi sistem untuk aplikasi ini");
                    }
                }
                break;
            }
            case HelperKey.VIBRATE_PERMISSION_GRANTED_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isVibrateGranted = true;
                } else {
                    if(mSharedPrefsModel.get(HelperKey.HAS_LOGIN, false)){
                        System.exit(0);
                    }else{
                        mBaseViewInterface.showToast("Mohon aktifkan ijin getar untuk notifikasi aplikasi ini");
                    }
                }
                break;
            }
        }
    }
}
