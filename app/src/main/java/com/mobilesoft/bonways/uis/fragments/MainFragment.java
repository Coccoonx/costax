package com.mobilesoft.bonways.uis.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Profile;
import com.mobilesoft.bonways.uis.MainActivity;
import com.mobilesoft.bonways.uis.adapters.MainItemAdapter;
import com.mobilesoft.bonways.uis.adapters.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lyonnel Dzotang on 24/01/2017.
 */

public class MainFragment extends Fragment implements SimpleAdapter.FilterByCategory {

    private AdView mAdView;
    MainItemAdapter mi;
    RecyclerView recyclerView;
    public static SimpleAdapter.FilterByCategory instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

//        mAdView = (AdView) v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        instance = this;

//        BottomBar bottomBar = (BottomBar) v.findViewById(R.id.bottomBar);
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
////                if (tabId == R.id.tab_favorites) {
////                    // The tab with id R.id.tab_favorites was selected,
////                    // change your content accordingly.
////                }
//            }
//        });

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerMain);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        Profile profile = ProfileManager.getCurrentUserProfile();
//        if (profile != null) {
//            if (profile.getProducts().size() == 0) {
//                profile.getProducts().addAll(DummyServer.getAvailableProduct());
//            }
//            dataSet.addAll(profile.getMyProducts());
//            dataSet.addAll(profile.getProducts());
//        } else {
//
//            dataSet.addAll(DummyServer.getAvailableProduct());
//        }
        //Dummy Data


        ArrayList<Product> dataSet = new ArrayList<>();
        dataSet.addAll(MainActivity.mProducts);


        mi = new MainItemAdapter(getActivity(), dataSet);
        recyclerView.setAdapter(mi);
//        mi = new MainItemAdapter(getActivity(), dataSet);
//        recyclerView.setAdapter(mi);
//        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        return v;
    }


    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        ArrayList<Product> dataSet = new ArrayList<>();
        dataSet.addAll(MainActivity.mProducts);
        dataSet.addAll(ProfileManager.getCurrentUserProfile().getMyProducts());


        mi = new MainItemAdapter(getActivity(), dataSet);
        recyclerView.setAdapter(mi);
    }


    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void process(Category category) {
        Set<Product> filteredProduct = new HashSet<>();

        if (category.getName().equals("Tout")) {
            filteredProduct.addAll(MainActivity.mProducts);
        } else {
            for (Product product : MainActivity.mProducts) {
                if (product.getCategory().equals(category)) {
                    filteredProduct.add(product);
                }

            }
            for (Product product : ProfileManager.getCurrentUserProfile().getMyProducts()) {
                if (product.getCategory().equals(category)) {
                    filteredProduct.add(product);
                }

            }
        }
//                if (!filteredProduct.isEmpty()) {
        ArrayList<Product> dataSet = new ArrayList<>();
        dataSet.addAll(filteredProduct);

        mi = new MainItemAdapter(getActivity(), dataSet);
        recyclerView.setAdapter(mi);
//                }
    }
}
