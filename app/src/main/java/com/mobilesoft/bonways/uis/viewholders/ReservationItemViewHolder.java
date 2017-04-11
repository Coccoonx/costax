package com.mobilesoft.bonways.uis.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilesoft.bonways.R;


public class ReservationItemViewHolder extends RecyclerView.ViewHolder{

    public TextView productName;
    public TextView shopName;
    public TextView code;
    public TextView price;
    public TextView date;
    public ImageView productPreview;

    public ReservationItemViewHolder(View itemView) {
        super(itemView);
        shopName = (TextView) itemView.findViewById(R.id.item_product_trade);
        productName = (TextView) itemView.findViewById(R.id.item_product_name);
        code = (TextView) itemView.findViewById(R.id.item_label_code_value);
        price = (TextView) itemView.findViewById(R.id.item_label_price_value);
        date = (TextView) itemView.findViewById(R.id.item_label_date_value);
        productPreview = (ImageView) itemView.findViewById(R.id.item_image);
    }
}
