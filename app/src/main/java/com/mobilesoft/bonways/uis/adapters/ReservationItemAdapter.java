package com.mobilesoft.bonways.uis.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.backend.DummyServer;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Reservation;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.uis.viewholders.ReservationItemViewHolder;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Lyonnel Dzotang on 25/01/2017.
 */

public class ReservationItemAdapter extends RecyclerView.Adapter<ReservationItemViewHolder> {

    ArrayList<Reservation> mDataSet;
    Context mContext;
    ArrayList<Trade> trades;
    public static final String TAG = "ReservationItemAdapter";

    public ReservationItemAdapter(Context context, ArrayList<Reservation> arrayList) {
        super();
        mDataSet = arrayList;
        mContext = context;
        trades = new ArrayList<>();

//        trades.addAll(DummyServer.getTrade());
        trades.addAll(ProfileManager.getCurrentUserProfile().getTrades());
    }

    @Override
    public void onBindViewHolder(ReservationItemViewHolder holder, int position) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        Reservation reservation = mDataSet.get(position);
        holder.productName.setText(reservation.getObject().getName());

        Trade tradeTmp = new Trade();
        for (Trade t : trades) {
            if (reservation.getObject().getTradeId().equals(t.getId())) {
                tradeTmp = t;
                break;
            }
        }
        holder.shopName.setText(tradeTmp.getName());



        double promo = reservation.getObject().getPrice() - (reservation.getObject().getPrice() * reservation.getObject().getDiscountPercentage() / 100);
        holder.promoPrice.setText(nf.format(promo)+" F");
        holder.quantity.setText(""+reservation.getQuantity());
        double totalP = promo * reservation.getQuantity();
        holder.totalPrice.setText(nf.format(totalP)+" F");
        holder.code.setText(reservation.getCode());
        holder.date.setText(reservation.getDateOfReservation());
        if (reservation.getObject().getImageUrl().contains("http") || reservation.getObject()
                .getImageUrl().contains("cdn")) {
            Picasso.with(mContext).load(reservation.getObject().getImageUrl())
                    .placeholder(R.drawable.nopreview).into(holder.productPreview);
        } else
            Picasso.with(mContext).load("file://" + reservation.getObject().getImageUrl())
                    .placeholder(R.drawable.nopreview).into(holder.productPreview);


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public ReservationItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ReservationItemViewHolder vh = new ReservationItemViewHolder(v);
        return vh;
    }


}
