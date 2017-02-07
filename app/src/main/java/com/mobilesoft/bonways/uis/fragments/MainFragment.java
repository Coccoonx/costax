package com.mobilesoft.bonways.uis.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.backend.DummyServer;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Profile;
import com.mobilesoft.bonways.uis.adapters.MainItemAdapter;

import java.util.ArrayList;

/**
 * Created by Lyonnel Dzotang on 24/01/2017.
 */

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerMain);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        Profile profile = ProfileManager.getCurrentUserProfile();
        ArrayList<Product> dataSet = new ArrayList<>();
        if (profile != null) {
            dataSet.addAll(profile.getMyProducts());
            dataSet.addAll(profile.getProducts());
        }
        //Dummy Data
        dataSet.addAll(DummyServer.getAvailableProduct());



        MainItemAdapter mi = new MainItemAdapter(getActivity(), dataSet);
        recyclerView.setAdapter(mi);
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

}
