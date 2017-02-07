package com.mobilesoft.bonways.uis.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilesoft.bonways.R;


public class ProductItemViewHolder extends RecyclerView.ViewHolder {

    public TextView commentCount;
    public TextView description;
    public TextView title;
    public TextView timeOff;
    public ImageView shopLogo;
    public ImageView productImage;

    public ProductItemViewHolder(View itemView) {
        super(itemView);
        commentCount = (TextView) itemView.findViewById(R.id.comment_count);
        description = (TextView) itemView.findViewById(R.id.item_description);
        title = (TextView) itemView.findViewById(R.id.item_title);
        timeOff = (TextView) itemView.findViewById(R.id.timeleft);
        productImage = (ImageView) itemView.findViewById(R.id.product_image);
        shopLogo = (ImageView) itemView.findViewById(R.id.shop_logo);
    }
}
