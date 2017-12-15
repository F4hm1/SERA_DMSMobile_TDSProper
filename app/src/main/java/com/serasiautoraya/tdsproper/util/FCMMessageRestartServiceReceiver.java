package com.serasiautoraya.tdsproper.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Randi Dwi Nandra on 06/02/2017.
 */
public class FCMMessageRestartServiceReceiver extends BroadcastReceiver{

    private static final String TAG = "RestartServiceReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive");
        context.startService(new Intent(context.getApplicationContext(), FCMMessageService.class));
    }
}
