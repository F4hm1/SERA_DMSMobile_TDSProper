package com.serasiautoraya.tdsproper.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperKey;

public class GPSTrackerServiceReceiver extends BroadcastReceiver {

    private static final String TAG = "GPSTrackerServiceReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
//        context.startService(new Intent(context.getApplicationContext(), FCMMessageService.class));

        String latitude = SharedPrefsUtil.getString(AppInit.getAppContext(), HelperKey.KEY_LOC_LAT, "");
        String longitude = SharedPrefsUtil.getString(AppInit.getAppContext(), HelperKey.KEY_LOC_LAT, "");
        String address = SharedPrefsUtil.getString(AppInit.getAppContext(), HelperKey.KEY_LOC_ADDRESS, "");
        String time = SharedPrefsUtil.getString(AppInit.getAppContext(), "CUR_TIME", "");

        updateLocationToServer(latitude, longitude, address, time);
    }

    private void updateLocationToServer(String latitude, String longitude, String address, String time) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(AppInit.getAppContext())
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.logoselog)
                .setAutoCancel(true)
                .setContentTitle("RECEIVER")
                .setContentText(time +" > "+latitude+" > "+longitude)
                .setTicker(time +" > "+latitude+" > "+longitude)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setColor(ContextCompat.getColor(AppInit.getAppContext(), R.color.colorPrimary))
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);

        NotificationManager manager =   (NotificationManager)AppInit.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }


}