package com.mobilesoft.bonways.uis.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.location.Location;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.uis.DetailsActivity;
import com.mobilesoft.bonways.uis.MainActivity;
import com.mobilesoft.bonways.uis.viewholders.MainItemViewHolder;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

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
        NumberFormat nf = NumberFormat.getNumberInstance();

        final Product product = mDataSet.get(position);
        holder.title.setText(product.getDesignation());
        holder.shopName.setText(product.getTrade().getName());

        if (MainActivity.mUserLocation != null) {
            Location tradeLoc = new Location(product.getTrade().getName());
            tradeLoc.setLatitude(product.getTrade().getLatitude());
            tradeLoc.setLongitude(product.getTrade().getLongitude());
            float distance = MainActivity.mUserLocation.distanceTo(tradeLoc);
            if (distance > 1000f)
                holder.shopDistance.setText(nf.format(distance / 1000) + " km");
            else
                holder.shopDistance.setText(nf.format(distance) + " m");
        }

        holder.title.setText(product.getDesignation());
        holder.percentageDiscount.setText("-" + nf.format(product.getDiscountPercentage()) + "%");
        holder.liked.setText(""+product.getLiked());
        holder.watched.setText(""+product.getWatched());
        holder.normalPrice.setText(nf.format(product.getPrice()) + " F CFA");
        holder.normalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        double promo = product.getPrice() - (product.getPrice() * product.getDiscountPercentage() / 100);
        holder.promoPrice.setText(nf.format(promo) + " F CFA");
        holder.timeOff.setText(product.getDateTimeOff());


        if (product.getImageUrl().contains("http") || product.getImageUrl().contains("cdn"))
            Picasso.with(mContext).load(product.getImageUrl()).into(holder.productImage);
        else
            Picasso.with(mContext).load("file://" + product.getImageUrl()).into(holder.productImage);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("product", (Parcelable) product);
                mContext.startActivity(intent);
            }
        });

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
