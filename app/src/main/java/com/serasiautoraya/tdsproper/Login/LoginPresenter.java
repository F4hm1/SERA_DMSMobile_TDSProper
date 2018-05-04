package com.serasiautoraya.tdsproper.Login;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.tdsproper.BaseModel.Model;
import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;
import com.serasiautoraya.tdsproper.BuildConfig;
import com.serasiautoraya.tdsproper.Dashboard.DashboardActivity;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.Helper.PermissionsHelper;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Randi Dwi Nandra on 20/03/2017.
 */

public class LoginPresenter extends TiPresenter<LoginView> {
    private SharedPrefsModel mSharedPrefsModel;
    private RestConnection mRestConnection;
    private PermissionsHelper mPermissionsHelper;
    private TelephonyManager mTelephonyManager;

    public LoginPresenter(SharedPrefsModel sharedPrefsModel, RestConnection mRestConnection, PermissionsHelper permissionsHelper, TelephonyManager mTelephonyManager) {
        this.mSharedPrefsModel = sharedPrefsModel;
        this.mRestConnection = mRestConnection;
        this.mPermissionsHelper = permissionsHelper;
        this.mTelephonyManager = mTelephonyManager;
    }

    public void initializePermissions() {
        mPermissionsHelper.requestLocationPermission();
    }

    @SuppressLint("MissingPermission")
    public void onLogin(String username, String password) {
        if (mPermissionsHelper.isAllPermissionsGranted()) {
            if (mPermissionsHelper.isAllPermissionsGranted()) {
                getView().startInitializeLocation();
            }
            final String fUsername = username;
            final String fPassword = password;

            String deviceID = "";
            if (Build.VERSION.SDK_INT >= 23) {
                deviceID = mTelephonyManager.getDeviceId(0);
            }else {
                deviceID = mTelephonyManager.getDeviceId();
            }

            String tokenFCM = mSharedPrefsModel.get(HelperKey.KEY_TOKEN_SAVED, "token-not-defined");
            if(tokenFCM.equalsIgnoreCase("token-not-defined")){
                tokenFCM = FirebaseInstanceId.getInstance().getToken();
            }

            LoginSendModel loginSendModel = new LoginSendModel(username, password, tokenFCM, deviceID, HelperKey.APPTYPE_TRAC, BuildConfig.VERSION_CODE + "");
            getView().toggleLoading(true);
            mRestConnection.postData("", HelperUrl.POST_LOGIN, loginSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
                @Override
                public void callBackOnSuccess(JSONObject response) {
                    try {
                        JSONArray jsonArr = response.getJSONArray("data");
                        if (jsonArr.toString().equals("[]")){
                            getView().toggleLoading(false);
                            getView().showToast("Terjadi kesalahan pada saat membaca data");
                        } else {
                            JSONObject jsonObject = response.getJSONArray("data").getJSONObject(0);
                            HelperBridge.sModelLoginResponse = Model.getModelInstanceFromString(jsonObject.toString(), LoginResponseModel.class);
                        }
                        if (!TextUtils.isEmpty(HelperBridge.sModelLoginResponse.getIsVersionValidate()) && HelperBridge.sModelLoginResponse.getIsVersionValidate().equals("1")){
                            int updateLocationInterval = Math.round(Float.valueOf(HelperBridge.sModelLoginResponse.getLocationUpdateInterval()));
                            mSharedPrefsModel.apply(HelperKey.HAS_LOGIN, true);
                            mSharedPrefsModel.apply(HelperKey.KEY_USERNAME, fUsername);
                            mSharedPrefsModel.apply(HelperKey.KEY_PASSWORD, fPassword);
                            mSharedPrefsModel.apply(HelperKey.KEY_LOCATION_UPDATE_INTERVAL, updateLocationInterval);
                            getView().toggleLoading(false);
                            getView().changeActivity(DashboardActivity.class);
                        } else {
                            getView().toggleLoading(false);
                            getView().showToast("Aplikasi Harus Diperbaharui, download dan Install di Playstore");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getView().goToPlayStore();
                                }
                            }, 3000);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        getView().showToast("Terjadi kesalahan pada saat membaca data");
                        getView().toggleLoading(false);
                    }
                }

                @Override
                public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                    getView().showToast(response);
                    getView().toggleLoading(false);
                    /*new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getView().goToPlayStore();
                        }
                    }, 3000);*/
                }

                @Override
                public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                    getView().showToast("ERROR: "+error.toString());
                    getView().toggleLoading(false);
                }
            });
        }else {
            mPermissionsHelper.requestLocationPermission();
        }
    }

    @Override
    protected void onAttachView(@NonNull final LoginView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
        if(mPermissionsHelper.isAllPermissionsGranted()){
            getView().startInitializeLocation();
        }
        /*
        * TODO Change / uncomment this, and init location and get permissions?
        * */
        Log.d("TAG_TOKEN", "firebase instance util :"+ FirebaseInstanceId.getInstance().getToken());
//        FirebaseInstanceIdServiceUtil firebaseInstanceIdServiceUtil = new FirebaseInstanceIdServiceUtil();
//        firebaseInstanceIdServiceUtil.onTokenRefresh();
        if(mSharedPrefsModel.get(HelperKey.HAS_LOGIN, false)){
            String password = mSharedPrefsModel.get(HelperKey.KEY_PASSWORD, "");
            String username = mSharedPrefsModel.get(HelperKey.KEY_USERNAME, "");
            getView().setCachedFormLogin(username, password);
            /*
            * TODO change foreground of login view to full white or other solid color
            * */
            onLogin(username, password);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
