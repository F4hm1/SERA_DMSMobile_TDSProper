package com.serasiautoraya.tdsproper.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Randi Dwi Nandra on 21/09/2017.
 */

public class NetworkUtil {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static boolean LAST_CONNECTION_NETWORK_STATUS = true;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static boolean getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        boolean status = false ;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = true;
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = true;
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = false;
        }
        LAST_CONNECTION_NETWORK_STATUS = status;
        return status;
    }
}
