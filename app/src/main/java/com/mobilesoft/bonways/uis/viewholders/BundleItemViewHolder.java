package com.mobilesoft.bonways.uis.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobilesoft.bonways.R;


public class BundleItemViewHolder extends RecyclerView.ViewHolder{

    public TextView name;
    public TextView description;
    public TextView price;
    public TextView validity;
    public ImageView logo;
    public RelativeLayout container;

    public BundleItemViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.item_bundle_name);
        description = (TextView) itemView.findViewById(R.id.item_bundle_description);
        price = (TextView) itemView.findViewById(R.id.item_bundle_price);
        validity = (TextView) itemView.findViewById(R.id.item_bundle_validity);
        logo = (ImageView) itemView.findViewById(R.id.bundle_image);
        container = (RelativeLayout) itemView.findViewById(R.id.card_bundle);
    }
}
