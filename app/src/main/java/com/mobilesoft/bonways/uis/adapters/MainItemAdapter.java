package com.mobilesoft.bonways.uis.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.location.Location;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

//import com.bumptech.glide.Glide;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.backend.DummyServer;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.uis.DetailsActivity;
import com.mobilesoft.bonways.uis.MainActivity;
import com.mobilesoft.bonways.uis.viewholders.MainItemViewHolder;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Lyonnel Dzotang on 25/01/2017.
 */

public class MainItemAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    ArrayList<Product> mDataSet;
    ArrayList<Trade> trades;
    Context mContext;
    RecyclerView mRecycler;

    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int totalItemCount;
    private int lastVisibleItem;
    private int visibleThreshold = 5;

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public MainItemAdapter(Context context, RecyclerView recyclerView, ArrayList arrayList) {
        super();
        mDataSet = arrayList;
        mContext = context;
        trades = new ArrayList<>();
        trades.addAll(MainActivity.mTrade);
        mRecycler = recyclerView;


        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {

        if (h instanceof MainItemViewHolder) {

            MainItemViewHolder holder = (MainItemViewHolder) h;
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(1);
            final Product product = mDataSet.get(position);
            Trade tradeTmp = new Trade();
            for (Trade t : trades) {
                if (product.getTradeId().equals(t.getId())) {
                    tradeTmp = t;
                    break;
                }
            }

            holder.title.setText(product.getName());
            if (product.getTradeId() != null) {
                holder.shopName.setText(tradeTmp.getName());
                Picasso.with(mContext).load(tradeTmp.getLogoUrl()).placeholder(R.drawable.nopreview).into(holder.shopLogo);
            }

            if (MainActivity.mUserLocation != null) {
                Location tradeLoc = new Location(tradeTmp.getName());
                tradeLoc.setLatitude(tradeTmp.getLatitude());
                tradeLoc.setLongitude(tradeTmp.getLongitude());
                int distance = (int) MainActivity.mUserLocation.distanceTo(tradeLoc);
                if (distance < 1000) {
                    holder.shopDistance.setText(nf.format(distance) + " m");
                } else {
                    double d = distance / 1000.0;
                    holder.shopDistance.setText(nf.format(d) + " km");
                }
            }

            holder.title.setText(product.getName());
            holder.percentageDiscount.setText("-" + nf.format(product.getDiscountPercentage()) + "%");
            holder.liked.setText(String.valueOf(product.getLikers().size()));
            holder.watched.setText(String.valueOf(product.getWatchers().size()));
            holder.commented.setText(String.valueOf(product.getComments().size()));
            holder.normalPrice.setText(nf.format(product.getPrice()));
            holder.normalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            double promo = product.getPrice() - (product.getPrice() * product.getDiscountPercentage() / 100);
            holder.promoPrice.setText(nf.format(promo) + " F");
//        DateFormat dateFormat = DateFormat.getDateInstance();
//        if (product.getTimeStart() != null)
//            holder.timeOff.setText(dateFormat.format(product.getTimeStart()));
//        if (product.getCreatedDate() != null)
//            holder.timePosted.setText(dateFormat.format(product.getCreatedDate()));
            if (product.getCategory() != null)
                holder.category.setText(product.getCategory().getName());


            if (product.isLiked()) {
                holder.iconLiked.setImageResource(R.drawable.ic_like_filled);
            } else {
                holder.iconLiked.setImageResource(R.drawable.ic_like);
            }


            if (product.getImageUrl() != null && (product.getImageUrl().contains("http") || product.getImageUrl().contains("cdn"))) {

            Picasso.with(mContext).load(product.getImageUrl()).placeholder(R.drawable.nopreview).into(holder.productImage);
//                Glide
//                        .with(mContext)
//                        .load(product.getImageUrl())
////                    .centerCrop()
//                        .placeholder(R.drawable.nopreview)
//                        .crossFade()
//                        .into(holder.productImage);
            } else {
            Picasso.with(mContext).load("file://" + product.getImageUrl()).placeholder(R.drawable.nopreview).into(holder.productImage);
//                Glide
//                        .with(mContext)
//                        .load("file://" + product.getImageUrl())
////                    .centerCrop()
//                        .placeholder(R.drawable.nopreview)
//                        .crossFade()
//                        .into(holder.productImage);
            }

            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtra("product", (Parcelable) product);
                    MainActivity.tmpCurrentProduct = product;
                    mContext.startActivity(intent);
                }
            });
        } else if (h instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) h;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemCount() {
        return  mDataSet == null ? 0 : mDataSet.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == VIEW_TYPE_ITEM) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_grid, parent, false);
            // set the view's size, margins, paddings and layout parameters
            MainItemViewHolder vh = new MainItemViewHolder(v);
            return vh;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }

        return null;


    }


    // "Loading item" ViewHolder
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar1);
        }
    }


}
