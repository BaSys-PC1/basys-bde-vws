package de.eyeled.fue.basyx.android.aasapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDexApplication;

public class BaSyxApplication extends MultiDexApplication {

    public static final String PREF_NAME = "basyx_prefs";
    public static final String PREF_APP_ID = "basyx_app_id";
    private static BaSyxApplication sInstance = null;
    private SharedPreferences mPrefs;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        sInstance = this;
        mPrefs = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }

    public static synchronized BaSyxApplication i() {
        return sInstance;
    }
}