package com.mobilesoft.bonways.core.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

/**
 * Created by Lyonnel Dzotang on 27/01/2017.
 */

@Data
public class Trade implements Serializable, Parcelable {

    private String name;

    private String nearestShopName;

    private String address;

    private double latitude;

    private double longitude;

    private String  phone;

    private String mainCategory;

    private String poBox;

    private String email;

    private String website;

    private String representerName;

    private String createdDate;

    private String updatedDate;

    private User user;

    private String logoUrl;

    private Set<Product> productslist;

    public Trade() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.phone);
        dest.writeString(this.mainCategory);
        dest.writeString(this.poBox);
        dest.writeString(this.email);
        dest.writeString(this.representerName);
        dest.writeString(this.createdDate);
        dest.writeString(this.updatedDate);
        dest.writeSerializable(this.user);
        dest.writeString(this.logoUrl);
        Bundle b = new Bundle();
        b.putSerializable("myProducts", (Serializable) productslist);
        dest.writeBundle(b);
    }

    protected Trade(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.phone = in.readString();
        this.mainCategory = in.readString();
        this.poBox = in.readString();
        this.email = in.readString();
        this.representerName = in.readString();
        this.createdDate = in.readString();
        this.updatedDate = in.readString();
        this.user = (User) in.readSerializable();
        this.logoUrl = in.readString();
        Bundle b = in.readBundle();
        productslist = (Set<Product>) b.getSerializable("myProducts");
    }

    public static final Parcelable.Creator<Trade> CREATOR = new Parcelable.Creator<Trade>() {
        @Override
        public Trade createFromParcel(Parcel source) {
            return new Trade(source);
        }

        @Override
        public Trade[] newArray(int size) {
            return new Trade[size];
        }
    };
}
