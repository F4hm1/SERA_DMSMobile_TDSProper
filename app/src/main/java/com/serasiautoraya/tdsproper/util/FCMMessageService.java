package com.serasiautoraya.tdsproper.util;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;
import com.serasiautoraya.tdsproper.Login.LoginActivity;
import com.serasiautoraya.tdsproper.SQLIte.DBHelper;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

//import com.serasiautoraya.slimobiledrivertracking.activity.LoginActivityBackup;

/**
 * Created by Randi Dwi Nandra on 16/01/2017.
 */
public class FCMMessageService extends FirebaseMessagingService {

    public final void molo(){

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();

//        String value1  = data.get("title");
//        String value2 = data.get("content");

//        if(!SharedPrefsUtil.getString(this, HelperKey.KEY_USERNAME, "").equalsIgnoreCase(data.get("IdPersonal"))){
//            Log.d("NOTIF_TAG", "Usernya logout");

//        }else {
            showNotification(data);
//        }

//        showPopUpAlert(data.get("title"));
    }


    /*public void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("default",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel description");
        notificationManager.createNotificationChannel(channel);
    }*/

    public void showNotificationInclOreo(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Oreo";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId) //channel id
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }


    public void showNotification(Map<String, String> message)
    {
        SharedPrefsModel sharedPrefsModel = new SharedPrefsModel(getApplicationContext());

        Intent i = new Intent(this, LoginActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP ); //FLAG_ACTIVITY_CLEAR_TOP

        Log.d("TAG_NOTIF", "Message Received here: "+message);
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_order);
//        contentView.setTextViewText(R.id.title, message.get("title"));
//        contentView.setTextViewText(R.id.text, message.get("content"));

        int iconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);
        Bitmap bitmapIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.logoselog);



        String channelId = "channel-01";
        String channelName = "Oreo";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationManager manager =   (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            manager.createNotificationChannel(mChannel);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(bitmapIcon)
                .setSmallIcon(R.drawable.logoselog)
                .setColor(iconColor)
                .setAutoCancel(true)
                .setContentTitle(message.get("Title"))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message.get("Body")))
                .setContentText(message.get("Body"))
//                .setCustomHeadsUpContentView(contentView)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message.get("Body")))
                .setTicker(message.get("Body"))
                .setVibrate(new long[] { 100, 250, 100, 250, 100, 250 })
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);



        manager.notify(sharedPrefsModel.get(HelperKey.KEY_NOTIF_ID, 0),builder.build());


//        Integer index = Integer.parseInt();

        sharedPrefsModel.apply(HelperKey.KEY_NOTIF_ID, sharedPrefsModel.get(HelperKey.KEY_NOTIF_ID, 0) + 1);

        saveToDatabase(message);
    }

    private void saveToDatabase(Map<String, String> message){
        Calendar dateCal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss");
        String dateString = sdf.format(dateCal.getTime());

        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NOTIFICATION_COLUMN_TITLE, message.get("Title"));
        contentValues.put(DBHelper.NOTIFICATION_COLUMN_MESSAGE, message.get("Body"));
        contentValues.put(DBHelper.NOTIFICATION_COLUMN_DATE, dateString);
        dbHelper.insert(DBHelper.NOTIFICATION_TABLE_NAME, contentValues);
    }

    public void showPopUpAlert(String message){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(FCMMessageService.this.getApplicationContext());
                LayoutInflater inflater = LayoutInflater.from(FCMMessageService.this);

                View dialogView = inflater.inflate(R.layout.notification_order, null);
                builder.setView(dialogView);

                final AlertDialog alert = builder.create();
                alert.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alert.show();

                Vibrator vibrator;
                vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = alert.getWindow();
                lp.copyFrom(window.getAttributes());
                //This makes the dialog take up the full width
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        sendBroadcast(new Intent("FCMReceiver"));
    }

}