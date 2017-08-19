package com.mobilesoft.bonways.uis.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.uis.ProductActivity;
import com.mobilesoft.bonways.uis.fragments.MainFragment;
import com.mobilesoft.bonways.uis.viewholders.CategoryItemViewHolder;
import com.mobilesoft.bonways.uis.viewholders.ShopItemViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemViewHolder> {

    ArrayList<Category> mDataSet;
    Context mContext;
    public static final String TAG = "CategoryItemAdapter";

    public CategoryItemAdapter(Context context, ArrayList arrayList) {
        super();
        mDataSet = arrayList;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(CategoryItemViewHolder holder, int position) {
        final Category category = mDataSet.get(position);
        holder.name.setText(category.getName());
        holder.description.setText(category.getName());
        Log.d(TAG, "URL:" + category.getIconIntUrl());
        Picasso.with(mContext).load(category.getIconIntUrl()).into(holder.catLogo);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFragment.instance.process(category);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public CategoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_grid, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CategoryItemViewHolder vh = new CategoryItemViewHolder(v);
        return vh;
    }


}
