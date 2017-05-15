package com.mobilesoft.bonways.uis.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobilesoft.bonways.R;

/**
 * Created by Lyonnel Dzotang on 25/01/2017.
 */

public class MainItemViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout container;
    public ImageView productImage;
    public ImageView iconLiked;
    public TextView normalPrice;
    public TextView promoPrice;
    public TextView shopName;
    public TextView shopDistance;
    public TextView percentageDiscount;
    public TextView title;
    public TextView timeOff;
    public TextView timePosted;
    public TextView liked;
    public TextView watched;
    public TextView commented;
    public TextView category;

    public MainItemViewHolder(View itemView) {
        super(itemView);
        normalPrice = (TextView) itemView.findViewById(R.id.normal_price);
        promoPrice = (TextView) itemView.findViewById(R.id.promo_price);
        percentageDiscount = (TextView) itemView.findViewById(R.id.percentage_discount);
        title = (TextView) itemView.findViewById(R.id.item_title);
        liked = (TextView) itemView.findViewById(R.id.item_liked);
        watched = (TextView) itemView.findViewById(R.id.item_watched);
        commented = (TextView) itemView.findViewById(R.id.item_comments);
        category = (TextView) itemView.findViewById(R.id.category);
        timeOff = (TextView) itemView.findViewById(R.id.timeleft);
        timePosted = (TextView) itemView.findViewById(R.id.timeposted);
        shopName = (TextView) itemView.findViewById(R.id.shop_name);
        shopDistance = (TextView) itemView.findViewById(R.id.shop_distance);
        productImage = (ImageView) itemView.findViewById(R.id.product_image);
        iconLiked = (ImageView) itemView.findViewById(R.id.icon_liked);
        container = (LinearLayout) itemView.findViewById(R.id.item_container);
    }
}
