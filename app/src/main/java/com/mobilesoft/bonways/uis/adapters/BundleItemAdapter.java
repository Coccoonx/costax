package com.mobilesoft.bonways.uis.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.SubscriptionType;
import com.mobilesoft.bonways.uis.SubscriptionDetailActivity;
import com.mobilesoft.bonways.uis.viewholders.BundleItemViewHolder;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lyonnel Dzotang on 25/01/2017.
 */

public class BundleItemAdapter extends RecyclerView.Adapter<BundleItemViewHolder> {

    List<SubscriptionType> mDataSet;
    Context mContext;
    public static final String TAG = "BundleItemAdapter";

    public BundleItemAdapter(Context context, List<SubscriptionType> arrayList) {
        super();
        mDataSet = arrayList;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(BundleItemViewHolder holder, int position) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        final SubscriptionType type = mDataSet.get(position);
        holder.name.setText(type.getName());
        holder.description.setText(type.getDescription());
        double promo = type.getCost();
        holder.price.setText(nf.format(promo) + " F");
        holder.validity.setText(type.getValidity() + " " + mContext.getResources().getString(R.string.label_timeleft_value));
        if (type.getImageUrl() != null && (type.getImageUrl().contains("http") || type.getImageUrl().contains("cdn"))) {
            Picasso.with(mContext).load(type.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher).into(holder.logo);
        } else
            Picasso.with(mContext).load(type.getImageInt())
                    .placeholder(R.mipmap.ic_launcher).into(holder.logo);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SubscriptionDetailActivity.class);
                intent.putExtra("subscription", (Parcelable) type);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public BundleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bundle_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        BundleItemViewHolder vh = new BundleItemViewHolder(v);
        return vh;
    }


}
