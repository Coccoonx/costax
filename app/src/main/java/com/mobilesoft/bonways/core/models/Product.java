package com.mobilesoft.bonways.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Lyonnel Dzotang on 27/01/2017.
 */
@Data
public class Product implements Serializable, Parcelable {

    private String code;

    private String designation;

    private long unit;

    private long packing;

    private double price;

    private double discount;

    private String description;

    private Long unitQuantity;

    private String imageUrl;

    private String createdDate;

    private String updatedDate;

    private String dateTimeOff;

    private Category category;

    private Trade trade;

    public Product() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.designation);
        dest.writeLong(this.unit);
        dest.writeLong(this.packing);
        dest.writeDouble(this.price);
        dest.writeDouble(this.discount);
        dest.writeString(this.description);
        dest.writeValue(this.unitQuantity);
        dest.writeString(this.imageUrl);
        dest.writeString(this.createdDate);
        dest.writeString(this.updatedDate);
        dest.writeString(this.dateTimeOff);
        dest.writeSerializable(this.category);
        dest.writeParcelable(this.trade, flags);
    }

    protected Product(Parcel in) {
        this.code = in.readString();
        this.designation = in.readString();
        this.unit = in.readLong();
        this.packing = in.readLong();
        this.price = in.readDouble();
        this.discount = in.readDouble();
        this.description = in.readString();
        this.unitQuantity = (Long) in.readValue(Long.class.getClassLoader());
        this.imageUrl = in.readString();
        this.createdDate = in.readString();
        this.updatedDate = in.readString();
        this.dateTimeOff = in.readString();
        this.category = (Category) in.readSerializable();
        this.trade = in.readParcelable(Trade.class.getClassLoader());
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
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
