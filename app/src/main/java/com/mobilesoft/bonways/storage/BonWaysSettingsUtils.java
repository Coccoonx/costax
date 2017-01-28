package com.mobilesoft.bonways.storage;


import com.mobilesoft.bonways.BonWaysApplication;

/**
 * An utility to get storage values
 */
public class BonWaysSettingsUtils {
    public static final String APP_ALREADY_CONFIGURED = "APP_ALREADY_CONFIGURED";
    public static final String ACCOUNT_ALREADY_CONFIGURED = "ACCOUNT_ALREADY_CONFIGURED";
    public static final String PLAY_NOTIFICATIONS = "notifications_play";
    public static final String NOTIFICATION_VIBRATE = "notification_vibrate";
    public static final String USER_PASSWORD = "PASSWORD";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    private static final String IS_FOR_PHONE_CODE = "IS_FOR_PHONE_CODE";
    private static final String IS_NOTIFICATION_VIEWED = "IS_NOTIFICATION_VIEWED";
    private static final String IS_CONTACT_LOADED = "IS_CONTACT_LOADED";
    public static final String MAX_AMOUNT_TRANSFERABLE = "MAX_AMOUNT_TRANSFERABLE";
    public static final String MIN_AMOUNT_TRANSFERABLE = "MIN_AMOUNT_TRANSFERABLE";
    public static final String APP_RATE_LAUNCH_TIME = "APP_RATE_LAUNCH_TIME";

    public static void logOut() {
        setIsLoggedIn(false);
    }

    public static String getUserPhone() {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_EMAIL, "");
    }

    public static void setUserPhone(String b) {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_EMAIL, b);
    }

    public static void setAppRateLaunchTime(int appRateLaunchTime){
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        storageInterface.save(APP_RATE_LAUNCH_TIME, appRateLaunchTime);
    }

    public static int getAppRateLaunchTime(){
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        return storageInterface.load(APP_RATE_LAUNCH_TIME, 0);
    }

    public static void setIsLoggedIn(Boolean b) {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        storageInterface.save(IS_LOGGED_IN, b);
    }

    public static void setIsContactLoaded(Boolean b) {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        storageInterface.save(IS_CONTACT_LOADED, b);
    }


    public static void setIsNotificationViewed(boolean b) {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        storageInterface.save(IS_NOTIFICATION_VIEWED, b);
    }

    public static Boolean getIsLoggedIn() {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        return storageInterface.load(IS_LOGGED_IN, false);
    }

    public static void setIsForPhoneCode(boolean b) {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        storageInterface.save(IS_FOR_PHONE_CODE, b);
    }

    public static Boolean getIsForPhoneCode() {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        return storageInterface.load(IS_FOR_PHONE_CODE, false);
    }

    public static String getUserPassword() {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_PASSWORD, "");
    }

    public static void setUserPassword(String value) {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_PASSWORD, value);
    }

    public static boolean getAppAlreadyConfigured() {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        return storageInterface.load(APP_ALREADY_CONFIGURED, false, true);
    }

    public static void setAppAlreadyConfigured(boolean value) {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        storageInterface.save(APP_ALREADY_CONFIGURED, value,  true);
    }

    public static void setAccountAlreadyConfigured(boolean value) {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        storageInterface.save(ACCOUNT_ALREADY_CONFIGURED, value);
    }

    public static boolean canDisplayNotifications() {
        boolean notificationEnabled;
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        notificationEnabled = storageInterface.load(BonWaysSettingsUtils.PLAY_NOTIFICATIONS, true);
        return notificationEnabled;
    }

    public static boolean canVibrate() {
        boolean notificationEnabled;
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        notificationEnabled = storageInterface.load(BonWaysSettingsUtils.NOTIFICATION_VIBRATE, true);
        return notificationEnabled;
    }

    public static void canVibrate(boolean value) {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        storageInterface.save(BonWaysSettingsUtils.NOTIFICATION_VIBRATE, value);
    }

    public static boolean canPlayNotificationSound() {
        // TODO Implement this method, in order to load the sound the customer want to play. Since now, we just return true
        return true;
    }



    public static void setMaxAmountTransferable(Float value) {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        storageInterface.save(MAX_AMOUNT_TRANSFERABLE, value);
    }

    public static Float getMaxAmountTransferable() {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        return storageInterface.load(MAX_AMOUNT_TRANSFERABLE, 1000f);
    }


    public static void setMinAmountTransferable(Float value) {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        storageInterface.save(MIN_AMOUNT_TRANSFERABLE, value);
    }

    public static Float getMinAmountTransferable() {
        PreferencesStorage storageInterface = BonWaysApplication.getPreferencesStorageInterface();
        return storageInterface.load(MIN_AMOUNT_TRANSFERABLE, 5f);
    }

}
