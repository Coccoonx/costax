package com.mobilesoft.bonways.service;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.content.Context;
import android.util.ArraySet;
import android.util.Log;
import android.widget.Toast;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.backend.DummyServer;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Profile;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.storage.BonWaysSettingsUtils;
import com.mobilesoft.bonways.uis.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import br.com.goncalves.pugnotification.notification.PugNotification;


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
            if (!profile.getProducts().containsAll(products)){
                if (products != null){
                    profile.setProducts(products);
                    // List of new products
                    ArrayList<Product> newProducts = new ArrayList<>();
                    for(Product p : products){
                        if (!profile.getProducts().contains(p))
                            newProducts.add(p);
                    }
                    String title="";
                    String message="";

                    if(newProducts.size()==1){
                        title="1 Nouveau BonWays autour de vous !";
                        message=newProducts.get(0).getName()+" "+ newProducts.get(0).getPrice();
                        // start notification
                        PugNotification.with(getApplicationContext())
                                .load()
                                .title(title)
                                .message(message)
                                .smallIcon(R.drawable.pugnotification_ic_launcher)
                                .largeIcon(R.drawable.pugnotification_ic_launcher)
                                .flags(Notification.DEFAULT_ALL)
                                .simple()
                                .build();
                    } else if (newProducts.size()-1<=10){
                        title=(newProducts.size()-1) +" Nouveaux BonWays autour de vous !";
                        for(Product p: newProducts){
                            message+=p.getName()+" "+ p.getPrice()+"\n";
                        }

                    } else {
                        title=(newProducts.size()-1) +"+ Nouveaux BonWays autour de vous !";
                        message="";
                    }

                }
                if (allTrades != null)
                    profile.setTrades(allTrades);
                if (categories != null)
                    profile.setCategories(categories);
            }

            BonWaysSettingsUtils.setLastPull(new Date().getTime());
            new ProfileManager.SaveProfile().execute(profile);
        }


    }
}
