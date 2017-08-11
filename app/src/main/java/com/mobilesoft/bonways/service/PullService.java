package com.mobilesoft.bonways.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.ArraySet;
import android.util.Log;
import android.widget.Toast;

import com.mobilesoft.bonways.backend.DummyServer;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Profile;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.storage.BonWaysSettingsUtils;
import com.mobilesoft.bonways.uis.MainActivity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PullService extends IntentService {

    private static final String TAG = "PullService";
    Set<Trade> allTrades;
    Set<Product> products;
    Set<Category> categories;

    public PullService() {
        super("PullService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {


        long min30 = 30 * 60 * 1000;
        long now = new Date().getTime();
        long diff = now - BonWaysSettingsUtils.getLastPull();
        if (diff >= min30) {
            Toast.makeText(this, "Fetching data", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onHandleIntent: retrieving data");
            getData();
        } else {
            Log.d(TAG, "onHandleIntent: Up-to-date :)");
            Toast.makeText(this, "Up-to-date :)", Toast.LENGTH_SHORT).show();
        }

    }

    /*
    * Retrieves Data from backend
    * */
    public void getData() {

        allTrades = new HashSet<>();
        allTrades.addAll(DummyServer.getInstance().getTrades());

        //get Products using user Location, and radius.
        products = new HashSet<>();
        products.addAll(DummyServer.getInstance().getProducts());

        categories = new HashSet<>();
        categories.addAll(DummyServer.getInstance().getCategories());

        saveData();
    }

    /**
     * Store data in localStorage.
     */
    private void saveData() {
        Profile profile = ProfileManager.getCurrentUserProfile();
        if (profile != null) {
            if (products != null)
                profile.setProducts(products);
            if (allTrades != null)
                profile.setTrades(allTrades);
            if (categories != null)
                profile.setCategories(categories);

            BonWaysSettingsUtils.setLastPull(new Date().getTime());
            new ProfileManager.SaveProfile().execute(profile);
        }


    }
}
