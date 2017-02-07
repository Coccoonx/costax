package com.mobilesoft.bonways.uis.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.uis.viewholders.MainItemViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lyonnel Dzotang on 25/01/2017.
 */

public class MainItemAdapter extends RecyclerView.Adapter<MainItemViewHolder> {

    ArrayList<Product> mDataSet;
    Context mContext;

    public MainItemAdapter(Context context, ArrayList arrayList) {
        super();
        mDataSet = arrayList;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(MainItemViewHolder holder, int position) {

        Product product = mDataSet.get(position);
        holder.title.setText(product.getDesignation());
        holder.description.setText(product.getDescription());
        holder.commentCount.setText("" + position);
        holder.timeOff.setText(product.getDateTimeOff());
        if (product.getTrade().getLogoUrl().contains("http") || product.getTrade().getLogoUrl().contains("cdn"))
            Picasso.with(mContext).load(product.getTrade().getLogoUrl()).into(holder.shopLogo);
        else
            Picasso.with(mContext).load("file://" + product.getTrade().getLogoUrl()).into(holder.shopLogo);

        if (product.getImageUrl().contains("http") || product.getImageUrl().contains("cdn"))
            Picasso.with(mContext).load(product.getImageUrl()).into(holder.productImage);
        else
            Picasso.with(mContext).load("file://" + product.getImageUrl()).into(holder.productImage);

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
