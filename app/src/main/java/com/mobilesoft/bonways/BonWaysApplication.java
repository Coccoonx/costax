package com.mobilesoft.bonways;

import android.app.Application;
import android.content.Intent;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.mobilesoft.bonways.service.PullService;
import com.mobilesoft.bonways.storage.PreferencesStorage;



public class BonWaysApplication extends Application {

    private static BonWaysApplication instance;
    private static PreferencesStorage preferencesStorageInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        instance=this;
        preferencesStorageInterface = new PreferencesStorage(this);

        //pullData();
    }

    private void pullData() {
        Intent intent = new Intent(this, PullService.class);
        startService(intent);
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
