package com.mobilesoft.bonways.core.managers;

import android.content.Context;
import android.util.Log;

import com.mobilesoft.bonways.BonWaysApplication;
import com.mobilesoft.bonways.BuildConfig;
import com.mobilesoft.bonways.core.models.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class FileManager {
    private static final String TAG = "FileManager";
    private static File OUTPUT_DIR = BonWaysApplication.getInstance().getDir(BuildConfig.APPLICATION_ID + BuildConfig.VERSION_CODE, Context.MODE_PRIVATE);
    private static final String OUTPUT_FILE = OUTPUT_DIR +"/BonsWays.store";
    private static final FileManager fileManager = new FileManager();

    public static FileManager getInstance() {
        return fileManager;
    }

    public boolean save(Profile profile) {
        return writeData(profile);
    }

    public Profile restore() {
        return restoreData();
    }


    private synchronized boolean writeData(Profile profile) {
        if (!OUTPUT_DIR.isDirectory()) {
            OUTPUT_DIR.mkdirs();
        }
        Log.d(TAG, "Before writing to outDir = (" + OUTPUT_DIR + ")");
        Log.d(TAG, "Datafile = (" + OUTPUT_FILE + ")");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE));
            oos.writeObject(profile);
            oos.flush();
            oos.close();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Problem while writing : " + Log.getStackTraceString(e));
            return false;
        }

    }

    private Profile restoreData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(OUTPUT_FILE));
            Profile profile = (Profile) ois.readObject();
            ois.close();
            Log.d(TAG, "Profile retrieved = "+profile);
            return profile;
        } catch (Exception e) {
            Log.d(TAG, "Problem reading file : " + e.getMessage());
            Log.d(TAG, Log.getStackTraceString(e));
            return null;
        }
    }
}
