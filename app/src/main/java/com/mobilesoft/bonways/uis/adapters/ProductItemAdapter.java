package com.mobilesoft.bonways.uis.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.uis.viewholders.ProductItemViewHolder;
import com.mobilesoft.bonways.uis.viewholders.ShopItemViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lyonnel Dzotang on 25/01/2017.
 */

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemViewHolder> {

    ArrayList<Product> mDataSet ;
    Context mContext;
    public static final String TAG = "ProductItemAdapter";

    public ProductItemAdapter(Context context, ArrayList arrayList) {
        super();
        mDataSet = arrayList;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(ProductItemViewHolder holder, int position) {
        Product product = mDataSet.get(position);
        holder.title.setText(product.getDesignation());
        holder.description.setText(product.getDescription());
        holder.commentCount.setText(""+position);
        holder.timeOff.setText(product.getDateTimeOff());
        Picasso.with(mContext).load("file://"+product.getTrade().getLogoUrl()).into(holder.shopLogo);
        Picasso.with(mContext).load("file://"+product.getImageUrl()).into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public ProductItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_grid, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ProductItemViewHolder vh = new ProductItemViewHolder(v);
        return vh;
    }






}
