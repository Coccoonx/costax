package com.mobilesoft.bonways.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class PreferencesStorage {

    private SharedPreferences prefs;
    private SharedPreferences prefs2;

    public PreferencesStorage(Context ctx) {
        prefs = ctx.getSharedPreferences(ctx.getPackageName() + "_bonways_settings", Context.MODE_PRIVATE);
        prefs2 = ctx.getSharedPreferences(ctx.getPackageName() + "_bonways_setting", Context.MODE_PRIVATE);
    }
    public void save(String key, Boolean value) {
        prefs.edit().putBoolean(key, value).commit();
    }

    public boolean load(String key, Boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public void save(String key, Boolean value, boolean fromOther) {
        if (fromOther)
        prefs2.edit().putBoolean(key, value).commit();
        else
            prefs.edit().putBoolean(key, value).commit();
    }

    public boolean load(String key, Boolean defaultValue, boolean fromOther) {
        if (fromOther)
        return prefs2.getBoolean(key, defaultValue);
        else
            return prefs.getBoolean(key, defaultValue);
    }

    public void save(String key, String value) {
        prefs.edit().putString(key, value).commit();
    }

    public String load(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public void save(String key, Integer value) {
        prefs.edit().putInt(key, value).commit();
    }

    public Integer load(String key, Integer defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public void save(String key, Float value) {
        prefs.edit().putFloat(key, value).commit();
    }

    public float load(String key, float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    public int load(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public void save(String key, Long value) {
        prefs.edit().putLong(key, value).commit();
    }

    public Long load(String key, Long defaultValue) {
        return prefs.getLong(key, defaultValue);
    }

    public Map<String, ?> getAll() {

        return prefs.getAll();
    }
}
