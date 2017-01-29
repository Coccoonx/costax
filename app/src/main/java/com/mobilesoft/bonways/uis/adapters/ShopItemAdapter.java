package com.mobilesoft.bonways.uis.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.uis.viewholders.MainItemViewHolder;
import com.mobilesoft.bonways.uis.viewholders.ShopItemViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lyonnel Dzotang on 25/01/2017.
 */

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemViewHolder> {

    ArrayList<Trade> mDataSet ;
    Context mContext;
    public static final String TAG = "ShopItemAdapter";

    public ShopItemAdapter(Context context, ArrayList arrayList) {
        super();
        mDataSet = arrayList;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(ShopItemViewHolder holder, int position) {
        Trade trade = mDataSet.get(position);
        holder.address.setText(trade.getAddress());
        holder.email.setText(trade.getEmail());
        holder.shopName.setText(trade.getName());
        holder.phone.setText(trade.getPhone());
        holder.representer.setText(trade.getRepresenterName());
        Log.d(TAG, "URL:"+ trade.getLogoUrl());
        Picasso.with(mContext).load("file://"+trade.getLogoUrl()).into(holder.shopLogo);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public ShopItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_grid, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ShopItemViewHolder vh = new ShopItemViewHolder(v);
        return vh;
    }






}
