package com.mobilesoft.bonways.uis.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilesoft.bonways.R;


public class ShopItemViewHolder extends RecyclerView.ViewHolder {

    public TextView shopName;
    public TextView address;
    public TextView website;
    public TextView productsNumber;
    public TextView phone;
    public TextView representer;
    public ImageView shopLogo;
    public CardView container;

    public ShopItemViewHolder(View itemView) {
        super(itemView);
        shopName = (TextView) itemView.findViewById(R.id.shop_name);
        address = (TextView) itemView.findViewById(R.id.shop_address);
        productsNumber = (TextView) itemView.findViewById(R.id.item_value_number_of_product);
        website = (TextView) itemView.findViewById(R.id.item_value_website);
        phone = (TextView) itemView.findViewById(R.id.item_value_phone_number);
        representer = (TextView) itemView.findViewById(R.id.item_value_representer_name);
        shopLogo = (ImageView) itemView.findViewById(R.id.shop_image);
        container = (CardView) itemView.findViewById(R.id.card_view);
    }
}
