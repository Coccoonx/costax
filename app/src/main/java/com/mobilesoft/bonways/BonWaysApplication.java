package com.mobilesoft.bonways;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.mobilesoft.bonways.storage.PreferencesStorage;

import net.danlew.android.joda.JodaTimeAndroid;


public class BonWaysApplication extends Application {

    private static BonWaysApplication instance;
    private static PreferencesStorage preferencesStorageInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        JodaTimeAndroid.init(this);
        instance=this;
        preferencesStorageInterface = new PreferencesStorage(this);
    }

    public static PreferencesStorage getPreferencesStorageInterface() {
        return preferencesStorageInterface;
    }

    public static synchronized BonWaysApplication getInstance() {
        if (instance != null)
            return instance;
        return null;
    }
}
