package com.mobilesoft.bonways.uis.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.location.Location;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.Comment;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.uis.DetailsActivity;
import com.mobilesoft.bonways.uis.MainActivity;
import com.mobilesoft.bonways.uis.viewholders.CommentViewHolder;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Lyonnel Dzotang on 25/01/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    ArrayList<Comment> mDataSet;
    Context mContext;

    public CommentAdapter(Context context, ArrayList<Comment> arrayList) {
        super();
        mDataSet = arrayList;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        DateFormat df = DateFormat.getDateInstance();

        final Comment comment = mDataSet.get(position);
        holder.date.setText(df.format(new Date(comment.getDate())));
        holder.authorName.setText(comment.getAuthorName());
        holder.content.setText(comment.getContent());
        Picasso.with(mContext).load(comment.getPictureUrl()).placeholder(R.drawable.nopreview).into(holder.authorPicture);


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CommentViewHolder vh = new CommentViewHolder(v);
        return vh;
    }


}
