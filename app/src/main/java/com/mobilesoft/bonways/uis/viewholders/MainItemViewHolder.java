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
    public ImageView shopLogo;
    public TextView commentCount;
    public TextView description;
    public TextView title;
    public TextView timeOff;

    public MainItemViewHolder(View itemView) {
        super(itemView);
        commentCount = (TextView) itemView.findViewById(R.id.comment_count);
        description = (TextView) itemView.findViewById(R.id.item_description);
        title = (TextView) itemView.findViewById(R.id.item_title);
        timeOff = (TextView) itemView.findViewById(R.id.timeleft);
        productImage = (ImageView) itemView.findViewById(R.id.product_image);
        shopLogo = (ImageView) itemView.findViewById(R.id.shop_logo);
        container = (LinearLayout) itemView.findViewById(R.id.item_container);
    }
}
