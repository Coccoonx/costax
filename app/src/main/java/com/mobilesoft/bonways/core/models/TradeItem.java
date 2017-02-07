package com.mobilesoft.bonways.core.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

/**
 * Created by Lyonnel Dzotang on 27/01/2017.
 */

@Data
public class TradeItem implements Comparable<TradeItem> {

    private String name;
    private String address;

    public TradeItem(String name, String address) {
        this.name = name;
        this.address = address;
    }


    @Override
    public int compareTo(TradeItem tradeItem) {
        return this.getName().compareTo(tradeItem.getName());
    }
}
