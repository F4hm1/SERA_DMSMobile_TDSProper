package com.serasiautoraya.tdsproper.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Randi Dwi Nandra on 21/09/2017.
 */

public abstract class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        boolean IsConnected = NetworkUtil.getConnectivityStatusString(context);
        Log.d("INTERNET ISSUE", "Connected: " + IsConnected);
//        View rootView = ((Activity) context).getWindow().getDecorView();
//        snackbar = Snackbar.make(rootView, "Koneksi internet anda bermasalah", Snackbar.LENGTH_INDEFINITE);
        if (!IsConnected) {
//            Toast.makeText(context, "Internet Connection is lost", Toast.LENGTH_LONG).show();
            onDisconnect();
        }else {
            onConnect();
        }
    }

    protected abstract void onDisconnect();

    protected abstract void onConnect();

}
