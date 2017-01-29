package com.mobilesoft.bonways.uis.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilesoft.bonways.R;


public class ShopItemViewHolder extends RecyclerView.ViewHolder{

    public TextView shopName;
    public TextView address;
    public TextView email;
    public TextView phone;
    public TextView representer;
    public ImageView shopLogo;

    public ShopItemViewHolder(View itemView) {
        super(itemView);
        shopName = (TextView) itemView.findViewById(R.id.shop_name);
        address = (TextView) itemView.findViewById(R.id.shop_address);
        email = (TextView) itemView.findViewById(R.id.shop_email);
        phone = (TextView) itemView.findViewById(R.id.shop_phone);
        representer = (TextView) itemView.findViewById(R.id.shop_representer);
        shopLogo = (ImageView) itemView.findViewById(R.id.shop_image);
    }
}
