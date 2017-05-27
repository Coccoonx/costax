package com.mobilesoft.bonways.core.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import lombok.Data;

/**
 * Created by Lyonnel Dzotang on 27/01/2017.
 */
@Data
public class Product implements Serializable, Parcelable, Comparable<Product>, Cloneable {

    private long id;

    private String code;

    private String name;

    private long unit;

    private long packing;

    private double price;

    private double discountPercentage;

    private String description;

    private Set<String> likers = new HashSet<>();

    private Set<String> watchers = new HashSet<>();

    private Set<Comment> comments = new TreeSet<>();

    private Long unitQuantity;

    private String imageUrl;

    private Date createdDate;

    private Date updatedDate;

    private Date dateTimeOff;

    private Category category;

    private Trade trade;

    private boolean isLiked;

    private boolean isWatched;

    private ProductStatus status;

    public Product() {
    }

    @Override
    public int compareTo(Product product) {
        return this.code.compareTo(product.code);
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

        return !(id != -1 ? id!=product.id : product.id != -1);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeLong(this.unit);
        dest.writeLong(this.packing);
        dest.writeDouble(this.price);
        dest.writeDouble(this.discountPercentage);
        dest.writeString(this.description);
        Bundle b = new Bundle();
        b.putSerializable("likers", (Serializable) likers);
        b.putSerializable("watchers", (Serializable) watchers);
        b.putSerializable("comments", (Serializable) comments);
        dest.writeBundle(b);
        dest.writeValue(this.unitQuantity);
        dest.writeString(this.imageUrl);
        dest.writeLong(this.createdDate != null ? this.createdDate.getTime() : -1);
        dest.writeLong(this.updatedDate != null ? this.updatedDate.getTime() : -1);
        dest.writeLong(this.dateTimeOff != null ? this.dateTimeOff.getTime() : -1);
        dest.writeSerializable(this.category);
        dest.writeParcelable(this.trade, flags);
        dest.writeByte(this.isLiked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isWatched ? (byte) 1 : (byte) 0);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
    }

    protected Product(Parcel in) {
        this.id = in.readLong();
        this.code = in.readString();
        this.name = in.readString();
        this.unit = in.readLong();
        this.packing = in.readLong();
        this.price = in.readDouble();
        this.discountPercentage = in.readDouble();
        this.description = in.readString();
        Bundle b = in.readBundle();
        likers = (HashSet<String>) b.getSerializable("likers");
        watchers = (HashSet<String>) b.getSerializable("watchers");
        HashSet<Comment> com = (HashSet<Comment>) b.getSerializable("comments");
        comments = new TreeSet<>(com);
        this.unitQuantity = (Long) in.readValue(Long.class.getClassLoader());
        this.imageUrl = in.readString();
        long tmpCreatedDate = in.readLong();
        this.createdDate = tmpCreatedDate == -1 ? null : new Date(tmpCreatedDate);
        long tmpUpdatedDate = in.readLong();
        this.updatedDate = tmpUpdatedDate == -1 ? null : new Date(tmpUpdatedDate);
        long tmpDateTimeOff = in.readLong();
        this.dateTimeOff = tmpDateTimeOff == -1 ? null : new Date(tmpDateTimeOff);
        this.category = (Category) in.readSerializable();
        this.trade = in.readParcelable(Trade.class.getClassLoader());
        this.isLiked = in.readByte() != 0;
        this.isWatched = in.readByte() != 0;
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : ProductStatus.values()[tmpStatus];
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
