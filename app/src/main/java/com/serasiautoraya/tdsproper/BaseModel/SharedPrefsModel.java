package com.serasiautoraya.tdsproper.BaseModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Randi Dwi Nandra on 21/03/2017.
 */

public class SharedPrefsModel {
    private SharedPreferences sharedPrefs;
    private Context context;

    public SharedPrefsModel(Context context) {
        this.context = context;
        this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /*SET APPLY DATA*/
    /*String*/
    public void apply(String key, String value) {
        putString(key, value).apply();
    }

    /*Integer*/
    public void apply(String key, int value) {
        putInt(key, value).apply();
    }

    /*Boolean*/
    public void apply(String key, boolean value) {
        putBoolean(key, value).apply();
    }

    /*GET DATA*/
    /*String*/
    public String get(String key, String defaultValue) {
        return sharedPrefs.getString(key, defaultValue);
    }

    /*Integer*/
    public int get(String key, int defaultValue) {
        return sharedPrefs.getInt(key, defaultValue);
    }

    /*Boolean*/
    public boolean get(String key, boolean defaultValue) {
        return sharedPrefs.getBoolean(key, defaultValue);
    }


    /*Clear All (When Logout)*/
    public void clearAll() {
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
