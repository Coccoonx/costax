package com.mobilesoft.bonways.uis.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.backend.DummyServer;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.uis.MainActivity;
import com.mobilesoft.bonways.uis.adapters.CategoryItemAdapter;
import com.mobilesoft.bonways.uis.adapters.MainItemAdapter;
import com.mobilesoft.bonways.uis.adapters.OnLoadMoreListener;

import java.util.ArrayList;

/**
 * Created by Lyonnel Dzotang on 24/01/2017.
 */

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoryItemAdapter mi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = v.findViewById(R.id.recyclerCategories);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        final ArrayList<Category> dataSet = new ArrayList<>();
        dataSet.addAll(DummyServer.getInstance().getCategories());


        mi = new CategoryItemAdapter(getActivity(), dataSet);
        recyclerView.setAdapter(mi);

    }

}
