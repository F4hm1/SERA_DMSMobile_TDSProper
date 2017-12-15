package com.serasiautoraya.tdsproper.util;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;
import com.serasiautoraya.tdsproper.Helper.HelperKey;

/**
 * Created by Randi Dwi Nandra on 13/01/2017.
 */
public class FirebaseInstanceIdServiceUtil extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("TAG_TOKEN", "Refreshed token: " + refreshedToken);
        SharedPrefsModel sharedPrefsModel = new SharedPrefsModel(getApplicationContext());
        Log.d("TAG_TOKEN", "Saved token: " + sharedPrefsModel.get(HelperKey.KEY_TOKEN_SAVED, ""));
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        SharedPrefsModel sharedPrefsModel = new SharedPrefsModel(getApplicationContext());
        sharedPrefsModel.apply(HelperKey.KEY_TOKEN_SAVED, token);
        Log.d("TAG_TOKEN", "Saved token after sendtoserver: " + sharedPrefsModel.get(HelperKey.KEY_TOKEN_SAVED, ""));
    }
}
