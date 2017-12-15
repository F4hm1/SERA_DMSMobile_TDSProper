package com.serasiautoraya.tdsproper.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Randi Dwi Nandra on 28/11/2016.
 */
public class SharedPrefsUtil {
    private SharedPreferences sharedPrefs;

    public SharedPrefsUtil(SharedPreferences sharedPrefs) {
        this.sharedPrefs = sharedPrefs;
    }

    private static SharedPrefsUtil getDefaultInstance(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return new SharedPrefsUtil(sp);
    }

    public static void clearAll(Context context) {
        getDefaultInstance(context).clearAll();
    }

    /*SET APPLY DATA*/
    /*String*/
    public static void apply(Context context, String key, String value){
        getDefaultInstance(context).apply(key, value);
    }

    public void apply(String key, String value) {
        putString(key, value).apply();
    }

    /*Integer*/
    public static void apply(Context context, String key, int value){
        getDefaultInstance(context).apply(key, value);
    }

    public void apply(String key, int value) {
        putInt(key, value).apply();
    }

    /*Boolean*/
    public static void apply(Context context, String key, boolean value){
        getDefaultInstance(context).apply(key, value);
    }

    public void apply(String key, boolean value) {
        putBoolean(key, value).apply();
    }

    /*GET DATA*/
    /*String*/
    public static String getString(Context context, String key, String defaultValue) {
        return getDefaultInstance(context).get(key, defaultValue);
    }

    public String get(String key, String defaultValue) {
        return sharedPrefs.getString(key, defaultValue);
    }

    /*Integer*/
    public static int getInt(Context context, String key, int defaultValue) {
        return getDefaultInstance(context).get(key, defaultValue);
    }

    public int get(String key, int defaultValue) {
        return sharedPrefs.getInt(key, defaultValue);
    }

    /*Boolean*/
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getDefaultInstance(context).get(key, defaultValue);
    }

    public boolean get(String key, boolean defaultValue) {
        return sharedPrefs.getBoolean(key, defaultValue);
    }




    /*Clear All (When Logout)*/
    private void clearAll() {
        SharedPreferences.Editor e = sharedPrefs.edit();
        e.clear();
        e.apply();
    }

    /*Put to sharedPreferences and commit*/
    @SuppressLint("CommitPrefEdits")
    private SharedPreferences.Editor putString(String key, String value) {
        SharedPreferences.Editor e = sharedPrefs.edit();
        e.putString(key, value);
        return e;
    }

    private SharedPreferences.Editor putInt(String key, int value) {
        SharedPreferences.Editor e = sharedPrefs.edit();
        e.putInt(key, value);
        return e;
    }

    private SharedPreferences.Editor putBoolean(String key, boolean value) {
        SharedPreferences.Editor e = sharedPrefs.edit();
        e.putBoolean(key, value);
        return e;
    }
}
