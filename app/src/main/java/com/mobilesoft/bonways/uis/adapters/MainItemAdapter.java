package com.mobilesoft.bonways.uis.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.uis.viewholders.MainItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lyonnel Dzotang on 25/01/2017.
 */

public class MainItemAdapter extends RecyclerView.Adapter<MainItemViewHolder> {

    ArrayList<Integer> mDataSet ;

    public MainItemAdapter(ArrayList arrayList) {
        super();
        mDataSet = arrayList;
    }

    @Override
    public void onBindViewHolder(MainItemViewHolder holder, int position) {

        holder.commentCount.setText(mDataSet.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public MainItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_grid, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MainItemViewHolder vh = new MainItemViewHolder(v);
        return vh;
    }






}
