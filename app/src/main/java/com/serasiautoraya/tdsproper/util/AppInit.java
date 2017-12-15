package com.serasiautoraya.tdsproper.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.serasiautoraya.tdsproper.model.VolleyUtil;

/**
 * Created by Randi Dwi Nandra on 28/11/2016.
 */
public class AppInit extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        AppInit.context=getApplicationContext();
        initVolley();
        initConfig();

        super.onCreate();
    }

    private void initVolley() {
        VolleyUtil.init(this);
    }

    private void initConfig() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static Context getAppContext() {
        return AppInit.context;
    }
}
