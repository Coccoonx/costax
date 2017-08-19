package com.mobilesoft.bonways.uis.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilesoft.bonways.R;


public class CategoryItemViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView description;
    public ImageView catLogo;
    public CardView container;

    public CategoryItemViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.cat_name);
        description = itemView.findViewById(R.id.cat_description);
        catLogo =  itemView.findViewById(R.id.cat_image);
        container = itemView.findViewById(R.id.card_view);
    }
}
