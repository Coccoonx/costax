package com.mobilesoft.bonways.uis.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.uis.MainActivity;
import com.mobilesoft.bonways.uis.adapters.MainItemAdapter;
import com.mobilesoft.bonways.uis.adapters.OnLoadMoreListener;
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
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

//        mAdView = (AdView) v.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        instance = this;

        recyclerView = v.findViewById(R.id.recyclerMain);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

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
        final ArrayList<Product> dataSet = new ArrayList<>();
        dataSet.addAll(MainActivity.mProducts);
        dataSet.addAll(ProfileManager.getCurrentUserProfile().getMyProducts());


        mi = new MainItemAdapter(getActivity(), recyclerView, dataSet);
        recyclerView.setAdapter(mi);

        mi.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (dataSet.size() <= 20) {
                    dataSet.add(null);
                    mi.notifyItemInserted(dataSet.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dataSet.remove(dataSet.size() - 1);

                            mi.notifyItemRemoved(dataSet.size());

                            dataSet.addAll(ProfileManager.getCurrentUserProfile().getProducts());
                            mi.notifyDataSetChanged();
                            mi.setLoaded();
                        }
                    }, 5000);
                } else {
//                    Toast.makeText(getActivity(), "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public void onRefresh() {
//        reLoadData();
//    }

    void reLoadData() {
        // Update the adapter and notify data set changed

        MainActivity.mProducts = ProfileManager.getCurrentUserProfile().getProducts();

        ArrayList<Product> dataSet = new ArrayList<>();
        dataSet.addAll(MainActivity.mProducts);
        mi = new MainItemAdapter(getActivity(), recyclerView, dataSet);
        recyclerView.setAdapter(mi);

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
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

        mi.notifyDataSetChanged();

//        mi = new MainItemAdapter(getActivity(), dataSet);
//        recyclerView.setAdapter(mi);
//                }
    }
}
