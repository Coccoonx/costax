package com.mobilesoft.bonways.core.managers;


import android.os.AsyncTask;
import android.util.Log;

import com.mobilesoft.bonways.core.models.Profile;
import com.mobilesoft.bonways.storage.BonWaysSettingsUtils;


public class ProfileManager {

    private static final String TAG = "ProfileManager";
    private static FileManager fileManager = FileManager.getInstance();
    private static Profile mProfile = null;

    private static synchronized Profile retrieveProfile() {
        if (mProfile == null)
            mProfile = fileManager.restore();
        return mProfile;
    }

    private static Profile saveProfile(final Profile profile) {
        boolean isOk = fileManager.save(profile);
        if (isOk) {
            mProfile = profile;
            BonWaysSettingsUtils.setAccountAlreadyConfigured(true);
            Log.d(TAG, "saveProfile completed:" + mProfile);
            return profile;
        } else {
            return null;
        }
    }

    public static Profile getCurrentUserProfile() {
        return retrieveProfile();
    }
    public static boolean delete() {
        return deleteProfile();
    }

    private static boolean deleteProfile() {
        return fileManager.deleteData();
    }


    public static class SaveProfile extends AsyncTask<Profile, Void, Profile> {

        @Override
        protected Profile doInBackground(Profile... params) {
            return saveProfile(params[0]);
        }
    }
}


