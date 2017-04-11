package com.mobilesoft.bonways.core.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.Data;

/**
 * Created by Lyonnel Dzotang on 27/01/2017.
 */
@Data
public class Product implements Serializable, Parcelable, Comparable<Product>, Cloneable {

    private String code;

    private String designation;

    private long unit;

    private long packing;

    private double price;

    private double discountPercentage;

    private String description;

    private Set<String> likers = new HashSet<>();

    private Set<String> watchers = new HashSet<>();

    private Long unitQuantity;

    private String imageUrl;

    private String createdDate;

    private String updatedDate;

    private String dateTimeOff;

    private Category category;

    private Trade trade;

    private boolean isLiked;

    private boolean isWatched;

    public Product() {
        code = UUID.randomUUID().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        createdDate = sdf.format(new Date());
    }

    @Override
    public int compareTo(Product product) {
        return this.code.compareTo(product.code);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public Product clone() {
        Product product;
        try {
            product = (Product) super.clone();
            return product;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return !(code != null ? !code.equals(product.code) : product.code != null);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.designation);
        dest.writeLong(this.unit);
        dest.writeLong(this.packing);
        dest.writeDouble(this.price);
        dest.writeDouble(this.discountPercentage);
        dest.writeString(this.description);
        Bundle b = new Bundle();
        b.putSerializable("likers", (Serializable) likers);
        dest.writeBundle(b);
        Bundle b2 = new Bundle();
        b2.putSerializable("watchers", (Serializable) watchers);
        dest.writeBundle(b2);
        dest.writeValue(this.unitQuantity);
        dest.writeString(this.imageUrl);
        dest.writeString(this.createdDate);
        dest.writeString(this.updatedDate);
        dest.writeString(this.dateTimeOff);
        dest.writeSerializable(this.category);
        dest.writeParcelable(this.trade, flags);
        dest.writeByte(this.isLiked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isWatched ? (byte) 1 : (byte) 0);
    }

    protected Product(Parcel in) {
        this.code = in.readString();
        this.designation = in.readString();
        this.unit = in.readLong();
        this.packing = in.readLong();
        this.price = in.readDouble();
        this.discountPercentage = in.readDouble();
        this.description = in.readString();
        Bundle b = in.readBundle();
        likers = (HashSet<String>) b.getSerializable("likers");
        Bundle b2 = in.readBundle();
        watchers = (HashSet<String>) b2.getSerializable("watchers");
        this.unitQuantity = (Long) in.readValue(Long.class.getClassLoader());
        this.imageUrl = in.readString();
        this.createdDate = in.readString();
        this.updatedDate = in.readString();
        this.dateTimeOff = in.readString();
        this.category = (Category) in.readSerializable();
        this.trade = in.readParcelable(Trade.class.getClassLoader());
        this.isLiked = in.readByte() != 0;
        this.isWatched = in.readByte() != 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
