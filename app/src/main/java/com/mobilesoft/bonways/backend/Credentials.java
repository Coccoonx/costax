package com.mobilesoft.bonways.backend;


import android.util.Base64;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class Credentials {
    private static final String TAG = "Credentials";
    public static String userPhone;
    public static String userPassword;
    public static String authorization;

    public static void setCredentials(String phone, String password) {
        userPhone = phone;
        userPassword = password;
        authorization = "Basic " +
                Base64.encodeToString((userPhone + ":" + userPassword).getBytes(), Base64.NO_WRAP);
    }

    public static OkHttpClient getClient() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

//    public static void setCredentials() {
//        userPhone = LibreExchangeSettingsUtils.getUserPhone();
//        userPassword = LibreExchangeSettingsUtils.getUserPassword();
//        authorization = "Basic " +
//                Base64.encodeToString((userPhone + ":" + userPassword).getBytes(), Base64.NO_WRAP);
//        Log.d(TAG, "set credential userPhone = " + userPhone + ", userPassword = " + userPassword);
//    }

}
