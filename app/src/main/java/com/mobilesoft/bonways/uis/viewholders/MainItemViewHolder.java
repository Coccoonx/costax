package com.mobilesoft.bonways.uis.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mobilesoft.bonways.R;

/**
 * Created by Lyonnel Dzotang on 25/01/2017.
 */

public class MainItemViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout container;
    public ImageView productImage;
    public ImageView iconLiked;
    public CircularImageView shopLogo;
    public TextView normalPrice;
    public TextView promoPrice;
    public TextView shopName;
    public TextView shopDistance;
    public TextView percentageDiscount;
    public TextView title;
//    public TextView timeOff;
//    public TextView timePosted;
    public TextView liked;
    public TextView watched;
    public TextView commented;
    public TextView category;
//    public ProgressBar progressBar;


    public MainItemViewHolder(View itemView) {
        super(itemView);
        normalPrice =  itemView.findViewById(R.id.normal_price);
        promoPrice =  itemView.findViewById(R.id.promo_price);
        percentageDiscount =  itemView.findViewById(R.id.percentage_discount);
        title =  itemView.findViewById(R.id.item_title);
        liked =  itemView.findViewById(R.id.item_liked);
        watched =  itemView.findViewById(R.id.item_watched);
        commented =  itemView.findViewById(R.id.item_comments);
        category =  itemView.findViewById(R.id.category);
//        timeOff =  itemView.findViewById(R.id.timeleft);
//        timePosted =  itemView.findViewById(R.id.timeposted);
        shopName =  itemView.findViewById(R.id.shop_name);
        shopDistance =  itemView.findViewById(R.id.shop_distance);
        productImage =  itemView.findViewById(R.id.product_image);
        iconLiked =  itemView.findViewById(R.id.icon_liked);
        shopLogo =  itemView.findViewById(R.id.shop_logo);
        container =  itemView.findViewById(R.id.item_container);
//        progressBar =  itemView.findViewById(R.id.progressBar1);

    }
}
