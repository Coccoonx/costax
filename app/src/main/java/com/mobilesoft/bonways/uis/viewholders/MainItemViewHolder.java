package com.mobilesoft.bonways.uis.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mobilesoft.bonways.R;

/**
 * Created by Lyonnel Dzotang on 25/01/2017.
 */

public class MainItemViewHolder extends RecyclerView.ViewHolder{

    public TextView commentCount;

    public MainItemViewHolder(View itemView) {
        super(itemView);
        commentCount = (TextView) itemView.findViewById(R.id.comment_count);
    }
}
