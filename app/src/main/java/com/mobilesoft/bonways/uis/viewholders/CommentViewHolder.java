package com.mobilesoft.bonways.uis.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mobilesoft.bonways.R;

/**
 * Created by Lyonnel Dzotang on 25/01/2017.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout container;
    public CircularImageView authorPicture;
    public TextView authorName;
    public TextView date;
    public TextView content;

    public CommentViewHolder(View itemView) {
        super(itemView);
        authorName = (TextView) itemView.findViewById(R.id.author);
        date = (TextView) itemView.findViewById(R.id.date);
        content = (TextView) itemView.findViewById(R.id.content);
        container = (LinearLayout) itemView.findViewById(R.id.item_container);
        authorPicture = (CircularImageView) itemView.findViewById(R.id.authorPicture);
    }
}
